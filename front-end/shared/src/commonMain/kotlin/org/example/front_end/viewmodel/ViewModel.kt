package org.example.front_end.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.example.front_end.common_elements.utils.DICOMConfig
import org.example.front_end.common_elements.utils.ExportToServerTable
import org.example.front_end.common_elements.utils.LoggerShUP
import org.example.front_end.common_elements.utils.LoginHandler
import org.example.front_end.common_elements.utils.dicom.DicomRetrieveService
import org.example.front_end.common_elements.utils.dicom.ImportJobRequest
import org.example.front_end.common_elements.utils.dicom.ImportJobStatus
import org.example.front_end.common_elements.utils.dicom.Patient
import org.example.front_end.common_elements.utils.dicom.Serie
import org.example.front_end.common_elements.utils.dicom.Study
import org.example.front_end.common_elements.utils.dicom.toJsonString
import java.io.File
import kotlin.math.log
import kotlin.time.Duration.Companion.milliseconds


class ViewModelShUp private constructor() : ViewModel() {
    val loginHandler = LoginHandler()
    val logger = LoggerShUP(
        File("/home/estevan/Documents/shanoir-uploader_kt/front-end/shared/src/commonMain/composeResources/files/logs.txt") // use the file logs.txt in the resources folder files
    )
    val exportTable = ExportToServerTable(logger)

    var DICOMConfig : JsonElement by mutableStateOf(DICOMConfig("", "", "", "", "", "").getDICOMConfigAsJsonElement())
    val client = HttpClient()
    val dicomRetrieveService = DicomRetrieveService(client)


    private var media : JsonElement? = null
    private var selectedPatient : Patient? = null


    val imports = mutableMapOf<String, ImportJobStatus>()

    fun setImports(imports: Map<String, ImportJobStatus>) {
        this.imports.clear()
        this.imports.putAll(imports)
    }

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

    // DICOM configuration management

    /**
     * ENDPOINT FOR DICOM SERVICE
     * Fetches the DICOM configuration from the server and updates the DICOMConfig property.
     * Uses the /configuration endpoint with a GET request to retrieve the configuration.
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
     * ENDPOINT FOR DICOM SERVICE
     * Updates the DICOM configuration on the server with the current DICOMConfig property.
     * Uses the /configuration endpoint with a PUT request to send the updated configuration.
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
     * ENDPOINT FOR DICOM SERVICE
     * Echo the distant PACS to check connectivity and configuration.
     * Uses the /echo endpoint of the DICOM server.
     */
    suspend fun echoDistantPACS() : Boolean {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/echo") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        val DICOMEcho = json.decodeFromString<JsonObject>(response.bodyAsText())

        return DICOMEcho["success"]?.jsonPrimitive?.booleanOrNull == true
    }

    /**
     * ENDPOINT FOR DICOM SERVICE
     * Retrieves the list of patients from the distant PACS.
     * Uses the /query endpoint of the DICOM service.
     */
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
        //logger.writeLog("Patients inside media json element: ${getPatients()}")
    }

    /**
     * ENDPOINT FOR DICOM SERVICE
     * Uses the /retrieve endpoint of the DICOM service to retrieve a specific study or series from the distant PACS.
     */
    suspend fun retrieveData(importJob: ImportJobRequest)  {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
        }

        val payload = importJob.toJsonString()
        //logger.writeLog("ImportJobRequest: $payload")

        val response = client.post("http://localhost:9903/dicom/retrieve") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }

        val responseText = response.bodyAsText()
        val body = json.parseToJsonElement(responseText).jsonObject

        try {
            val importJobId = body["importJobId"]?.jsonPrimitive?.content
                ?: throw IllegalStateException("No importJobId returned : $body",)

            imports[importJobId] = ImportJobStatus(
                percentage = 0,
                currentStep = "STARTED",
                reportSummary = null,
                done = false,
                success = false
            )
        } catch (e: Exception) {
            // DO NOT throw an exception here, just log the error and return
            logger.writeLog("Error parsing importJobId from response: ${e.message}")
        }


    }

    /**
     * ENDPOINT FOR DICOM SERVICE
     * Uses the /importJobs/{jobId}/progress endpoint of the DICOM service to get the progress of a specific import job.
     */
    suspend fun getImportJobProgress(jobId: String): ImportJobStatus {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/importJobs/$jobId/progress") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        val statusJson = json.parseToJsonElement(response.bodyAsText()).jsonObject
        return ImportJobStatus(
            percentage = statusJson["percentage"]?.jsonPrimitive?.int ?: 0,
            currentStep = statusJson["currentStep"]?.jsonPrimitive?.content ?: "",
            reportSummary = statusJson["reportSummary"]?.jsonPrimitive?.contentOrNull,
            done = statusJson["done"]?.jsonPrimitive?.boolean ?: false,
            success = statusJson["success"]?.jsonPrimitive?.boolean ?: false
        )
    }

    suspend fun getWorkFolder() : File {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/workFolder") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        val workFolderJson = json.parseToJsonElement(response.bodyAsText()).jsonObject
        println(workFolderJson)
        val workFolderPath = workFolderJson["workFolderPath"]?.jsonPrimitive?.content ?: throw IllegalStateException("No workFolder returned : $workFolderJson")
        return File(workFolderPath)
    }


    // DICOM Objects Management

    fun getPatients(): List<Patient> {
        val patients = mutableListOf<Patient>()
        val patientsJsonArray = media?.jsonObject?.get("patients")?.jsonArray ?: return emptyList()
        patientsJsonArray.forEach { patientJsonElement ->
            val patientJsonObject = patientJsonElement.jsonObject["patient"]?.jsonObject ?: return@forEach
            val patient = Patient(patientJsonObject)
            if (patient.patientFirstName == ""){
                patient.setPatientFirstName(patient.patientName)
            }
            patients.add(patient)
        }
        println(patients)
        return patients
    }

    fun setSelectedPatient(patient: Patient?) {
        selectedPatient = patient
        //selectedPatient?.resetSelectedStudy()
        //logger.writeLog("Selected patient: ${patient?.patientName} (${patient?.patientId})")
    }

    fun setSelectedStudy(study: Study) {
        selectedPatient?.setSelectedStudy(study)
        //logger.writeLog("Selected study: ${study.studyDescription} (${study.studyDate})")
    }

    fun addSelectedSerie(serie: Serie) {
        val parentStudy = selectedPatient?.studies?.firstOrNull { it.studyInstanceUID == serie.seriesInstanceUID }
        parentStudy?.addSerie(serie)
        logger.writeLog("Added serie: ${serie.seriesDescription} (${serie.seriesDate}) to study: ${parentStudy?.studyDescription}")
    }

    fun removeSelectedSerie(serie: Serie) {
        val parentStudy = selectedPatient?.studies?.firstOrNull { it.studyInstanceUID == serie.seriesInstanceUID }
        parentStudy?.removeSerie(serie)
        logger.writeLog("Removed serie: ${serie.seriesDescription} (${serie.seriesDate}) from study: ${parentStudy?.studyDescription}")
    }

    fun getSelectedPatient(): Patient? {
        return selectedPatient
    }

    fun updateImports(jobId: String, status: ImportJobStatus) {
        imports[jobId] = status
    }
}
