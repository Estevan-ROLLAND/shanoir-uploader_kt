package org.example.front_end.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.example.front_end.common_elements.utils.DICOMConfig
import org.example.front_end.common_elements.utils.LoggerShUP
import org.example.front_end.common_elements.utils.LoginHandler
import org.example.front_end.common_elements.utils.dicom.Patient
import java.io.File
import kotlin.math.log
import kotlin.time.Duration.Companion.milliseconds


class ViewModelShUp private constructor() : ViewModel() {
    val loginHandler = LoginHandler()
    val logger = LoggerShUP(
        File("/home/estevan/Documents/shanoir-uploader_kt/front-end/shared/src/commonMain/composeResources/files/logs.txt") // use the file logs.txt in the resources folder files
    )
    var testData by mutableStateOf(listOf(
        listOf("P1","John Doe","IPP_Random","10/08/2019","IRM1","FINISHED"),
        listOf("P2","John Doe","IPP_Random","10/08/2019","IRM1","ERROR"),
        listOf("P3","John Doe","IPP_Random","10/08/2019","IRM1","READY"),
        listOf("P4","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_OK"),
        listOf("P5","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_KO"),
    ))

    var errorLines by mutableStateOf(listOf<List<String>>())
    var selectedLines by mutableStateOf(listOf<List<String>>())
    var enableImportBtn by mutableStateOf(false)

    var DICOMConfig : JsonElement by mutableStateOf(DICOMConfig("", "", "", "", "", "").getDICOMConfigAsJsonElement())
    val client = HttpClient()


    private var media : JsonElement? = null
    private var selectedPatient : Patient? = null

    companion object {
        @Volatile
        private var INSTANCE: ViewModelShUp? = null

        fun getInstance(): ViewModelShUp {
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelShUp()
                INSTANCE = instance
                instance
            }
        }
    }

    init {
        errorLines = testData.filter { it[5] == "ERROR" }
    }

    // Imported Lines Table

    fun addLineToSelected(data: List<String>){
        if (!selectedLines.contains(data)) {
            selectedLines = selectedLines + listOf(data)
        }
        checkEnableImportBtn()
    }

    fun removeLineAsSelected(data: List<String>){
        if (selectedLines.contains(data)){
            selectedLines = selectedLines.filterNot { it == data }
        }
        checkEnableImportBtn()
    }

    fun deleteSelectedLines() {
        println(testData)
        val linesToDelete = selectedLines.toList()
        if (linesToDelete.isEmpty()) return

        testData = testData.filterNot { linesToDelete.contains(it) }
        selectedLines = emptyList()
        logger.writeLog("Deleted ${linesToDelete.size} selected lines.")
        println(testData)
        checkEnableImportBtn()
    }

    fun deleteLine(data: List<String>) {
        testData = testData.filterNot { it == data }
        logger.writeLog("Deleted line: $data")
        removeLineAsSelected(data)
    }

    fun checkEnableImportBtn(){
        enableImportBtn = selectedLines.size == 1 && (selectedLines.last()[5] == "READY" || selectedLines.last()[5] == "ERROR")
    }


    // DICOM configuration management

    /**
     * Fetches the DICOM configuration from the server and updates the DICOMConfig property.
     */
    suspend fun getDICOMConfig() {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/configuration") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        DICOMConfig = json.decodeFromString<JsonObject>(response.bodyAsText())

        logger.writeLog("Fetched DICOM configuration: $DICOMConfig")

        println("Fetched DICOM configuration: $DICOMConfig")
    }

    /**
     * Updates the DICOM configuration on the server with the current DICOMConfig property.
     */
    suspend fun setDICOMConfig() {
        println("Current DICOM configuration BEFORE UPDATE : $DICOMConfig")
        val response: HttpResponse = client.put("http://localhost:9903/dicom/configuration") {
            contentType(ContentType.Application.Json)
            setBody(DICOMConfig.toString())
        }

        logger.writeLog("Updated DICOM configuration: $DICOMConfig")
        println("Response: ${response.status.value}")
    }

    /**
     * Echo the distant PACS to check connectivity and configuration.
     */
    suspend fun echoDistantPACS() : Boolean {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/echo") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        val DICOMEcho = json.decodeFromString<JsonObject>(response.bodyAsText())

        return DICOMEcho["success"]?.jsonPrimitive?.booleanOrNull == true
    }

    suspend fun queryDistantPACS(studyRootQuery: Boolean, modality: String, patientName: String, patientID: String, studyDescription: String, patientBirthDate: String, studyDate: String) {
        val modality : String? = if (modality == "None") null else modality

        val response: HttpResponse = client.post("http://localhost:9903/dicom/query") {
            contentType(ContentType.Application.Json)
            setBody(
                buildJsonObject {
                    put("studyRootQuery", studyRootQuery)
                    put("modality", modality)
                    put("patientName", patientName)
                    put("patientID", patientID)
                    put("studyDescription", studyDescription)
                    put("patientBirthDate", patientBirthDate)
                    put("studyDate", studyDate)
                }.toString()
            )
        }

        //logger.writeLog("Query response: ${response.bodyAsText()}")

        val json = Json { ignoreUnknownKeys = true }
        //println(response.bodyAsText())
        media = json.decodeFromString<JsonElement>(response.bodyAsText())

        // print the patient inside de media json element
//        logger.writeLog("Patients inside media json element: ${getPatients()}")
    }

    fun getPatients(): List<Patient> {
        val patients = mutableListOf<Patient>()
        media?.jsonObject?.get("children")?.jsonArray?.forEach { patientElement ->
            val patientJsonObject = patientElement.jsonObject.get("patient")?.jsonObject
            if (patientJsonObject != null) {
                val patient = Patient(patientJsonObject)
                if (patient.patientFirstName == ""){
                    patient.setPatientFirstName(patient.patientName)
                }
                patients.add(patient)
            }
        }
        println(patients)
        return patients
    }

    fun setSelectedPatient(patient: Patient?) {
        selectedPatient = patient
        logger.writeLog("Selected patient: ${patient?.patientName} (${patient?.patientId})")
    }

    fun getSelectedPatient(): Patient? {
        return selectedPatient
    }

    fun resetMedia() {
        media = null
        selectedPatient = null
    }
}