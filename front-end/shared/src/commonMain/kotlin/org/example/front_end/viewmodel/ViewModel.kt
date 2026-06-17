package org.example.front_end.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import org.example.front_end.common_elements.utils.DICOMConfig


class ViewModelShUp : ViewModel() {
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

    fun getNbSelectedLines() : Int = selectedLines.size

    fun addLine(data: List<String>){
        if (!selectedLines.contains(data)) {
            selectedLines = selectedLines + listOf(data)
        }
        checkEnableImportBtn()
    }

    fun removeLine(data: List<String>){
        if (selectedLines.contains(data)){
            selectedLines = selectedLines.filterNot { it == data }
        }
        checkEnableImportBtn()
    }

    fun deleteSelectedLines() {
        val linesToDelete = selectedLines.toList()
        if (linesToDelete.isEmpty()) return

        testData = testData.filterNot { linesToDelete.contains(it) }
        selectedLines = emptyList()
        checkEnableImportBtn()
    }

    fun deleteLine(data: List<String>) {
        testData = testData.filterNot { it == data }
        removeLine(data)
    }

    fun checkEnableImportBtn(){
        enableImportBtn = selectedLines.size == 1
        println("enabled : $enableImportBtn")
    }


    /**
     * Fetches the DICOM configuration from the server and updates the DICOMConfig property.
     */
    suspend fun getDICOMConfig() {
        val response: HttpResponse = client.get("http://localhost:9903/dicom/configuration") {
            contentType(ContentType.Application.Json)
        }

        val json = Json { ignoreUnknownKeys = true }
        DICOMConfig = json.decodeFromString<JsonObject>(response.bodyAsText())
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
        println("Response: ${response.status.value}")
    }
}