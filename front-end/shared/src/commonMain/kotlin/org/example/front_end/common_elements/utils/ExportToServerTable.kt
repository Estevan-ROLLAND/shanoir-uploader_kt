package org.example.front_end.common_elements.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.front_end.viewmodel.ViewModelShUp
import java.io.File
import kotlin.collections.forEach
import kotlin.plus

class ExportToServerTable(private val logger: LoggerShUP) {

    var testData by mutableStateOf(listOf(
        listOf("P1","John Doe","IPP_Random","10/08/2019","IRM1","FINISHED","/path/to/data/folder1"),
        listOf("P2","John Doe","IPP_Random","10/08/2019","IRM1","ERROR","/path/to/data/folder2"),
        listOf("P3","John Doe","IPP_Random","10/08/2019","IRM1","READY","/path/to/data/folder3"),
        listOf("P4","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_OK","/path/to/data/folder4"),
        listOf("P5","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_KO","/path/to/data/folder5"),
    ))

    var selectedLines by mutableStateOf(listOf<List<String>>())

    var enableImportBtn by mutableStateOf(false)

    private val workFolder by lazy {
        runBlocking { ViewModelShUp.getInstance().getWorkFolder() }
    }


    fun getImportsFromWorkFolder() : List<List<String>> {
        // we need a coroutine to call the getWorkFolder
        println("Getting imports from work folder: ${workFolder.absolutePath}")

        val loadedLines = mutableListOf<List<String>>()

        if (workFolder.isDirectory) {
            val importFolders = workFolder.listFiles()
            println("Found ${importFolders?.size ?: 0} folder in work folder.")
            importFolders.forEach { folder ->
                if (folder.isDirectory) {
                    val importFile = File(folder, "import-job.json")
                    if (importFile.exists()) {
                        val jsonString = importFile.readText()
                        // transform the jsonString into a ImportJobJava object
                        val importJob = Json.decodeFromString<JsonElement>(jsonString) // decode the json string into a JsonElement
                        println("Decoded import-job.json into JsonElement: $importJob")

                        // transform the JsonElement into a List<String> to add to the table
                        val ID = importJob.jsonObject["subject"]?.jsonObject?.get("identifier")?.jsonPrimitive?.contentOrNull ?: "null"
                        val patientNameJson = importJob.jsonObject["patient"]?.jsonObject?.get("patientName")?.jsonPrimitive?.contentOrNull ?: "null"
                        // The patientNameJson is in the format "LastName\^FirstName", we want to transform it into "FirstName LastName"
                        val patientName = if (patientNameJson != "null") {
                            val nameParts = patientNameJson.split("\\^")
                            if (nameParts.size == 2) {
                                "${nameParts[1]} ${nameParts[0]}"
                            } else {
                                patientNameJson
                            }
                        } else {
                            "null"
                        }
                        val patientID = importJob.jsonObject["patient"]?.jsonObject?.get("patientID")?.jsonPrimitive?.contentOrNull ?: "null"
                        val studyDateJson = importJob.jsonObject["study"]?.jsonObject?.get("studyDate")?.jsonArray
                        // We want the format to be DD/MM/YYYY, but the studyDateJson is an array with [YYYY,M,D], so we need to transform it
                        val studyDate = if (studyDateJson != null && studyDateJson.size == 3) {
                            val year = studyDateJson[0].jsonPrimitive.int
                            val month = studyDateJson[1].jsonPrimitive.int
                            val day = studyDateJson[2].jsonPrimitive.int
                            String.format("%02d/%02d/%04d", day, month, year)
                        } else {
                            "null"
                        }
                        val IRM = (importJob.jsonObject["selectedSeries"]?.jsonArray?.getOrNull(0)?.jsonObject?.get("equipment")?.jsonObject?.get("manufacturer")?.jsonPrimitive?.contentOrNull ?: "null") + " (" + (importJob.jsonObject["selectedSeries"]?.jsonArray?.getOrNull(0)?.jsonObject?.get("equipment")?.jsonObject?.get("manufacturerModelName")?.jsonPrimitive?.contentOrNull ?: "null") + ")"
                        val status = importJob.jsonObject["uploadState"]?.jsonPrimitive?.contentOrNull ?: "null"
                        val dataFolder = importJob.jsonObject["workFolder"]?.jsonPrimitive?.contentOrNull ?: "null"

                        val line = listOf(ID, patientName, patientID, studyDate, IRM, status, dataFolder)
                        if (line !in testData && line !in loadedLines) {
                            loadedLines += line
                            logger.writeLog("Added line to table: $line")
                        }
                    } else {
                        logger.writeLog("No import.json file found in ${folder.absolutePath}")
                    }
                }
            }

        } else {
            logger.writeLog("Work folder does not exist or is not a directory: $workFolder")
        }

        if (loadedLines.isNotEmpty()) {
            testData = testData + loadedLines
        }

        return loadedLines
    }



    // Imported Lines Table

    fun addLineToSelected(data: List<String>){
        if (!selectedLines.contains(data)) {
            selectedLines = selectedLines + listOf(data)
        }
        checkEnableImportBtn()
    }

    fun removeLineAsSelected(data: List<String>){

        selectedLines = selectedLines.filterNot { it == data }
        // We also need to delete the folder in the workfolder
        val importFolder = File(data[6]) // the data[6] is the pathname to the dataFolder
        if (importFolder.exists()) {
            importFolder.deleteRecursively()
            // Remove from viewModel.imports
            var imports = ViewModelShUp.getInstance().imports
            imports = imports.filter { it.key == data[0] }.toMutableMap() // remove the import with the same ID as data[0]
            ViewModelShUp.getInstance().setImports(imports)
            logger.writeLog("Deleted import folder: ${importFolder.absolutePath}")
        } else {
            logger.writeLog("Import folder does not exist: ${importFolder.absolutePath}")
        }

        checkEnableImportBtn()
    }

    fun deleteSelectedLines() {
        val linesToDelete = selectedLines.toList() // create a copy of the selected lines to delete
        if (linesToDelete.isEmpty()) return

        testData = testData.filterNot { linesToDelete.contains(it) }
        selectedLines = emptyList()
        for (line in linesToDelete) {
            removeLineAsSelected(line)
        }
        logger.writeLog("Deleted ${linesToDelete.size} selected lines.")
    }

    fun deleteLine(data: List<String>) {
        removeLineAsSelected(data)
        testData = testData.filterNot { it == data }
        logger.writeLog("Deleted line: $data")
    }

    fun checkEnableImportBtn(){
        enableImportBtn = selectedLines.size == 1 && (selectedLines.last()[5] == "READY" || selectedLines.last()[5] == "ERROR")
    }
}
