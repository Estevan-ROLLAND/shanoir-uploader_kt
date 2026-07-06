package org.example.front_end.common_elements.utils.dicom

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds

class DicomRetrieveService(private val httpClient: HttpClient) {

    companion object {
        private const val BASE_URL = "http://localhost:9903/dicom"
        private const val POLL_INTERVAL_MS = 500L // Polling interval in milliseconds
    }

    suspend fun startRetrieve(request: ImportJobRequest) : String {
        val response = httpClient.post("$BASE_URL/retrieve") {
            contentType(ContentType.Application.Json)
            setBody(request.toJsonString())
        }

        if (response.status != HttpStatusCode.Accepted) {
            throw IllegalStateException("Failed to start import: ${response.status}")
        }

        val body = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        return body["importJobId"]?.jsonPrimitive?.content
            ?: throw IllegalStateException("No importJobId returned")
    }

    fun pollProgress(jobId: String) : Flow<ImportJobStatus> = flow {
        while (true) {
            val statusResponse = httpClient.get("$BASE_URL/retrieve/$jobId/status")
            val statusJson = Json.parseToJsonElement(statusResponse.bodyAsText()).jsonObject
            val status = ImportJobStatus(
                percentage = statusJson["percentage"]?.jsonPrimitive?.int ?: 0,
                currentStep = statusJson["currentStep"]?.jsonPrimitive?.content ?: "",
                reportSummary = statusJson["reportSummary"]?.jsonPrimitive?.contentOrNull,
                done = statusJson["done"]?.jsonPrimitive?.boolean ?: false,
                success = statusJson["success"]?.jsonPrimitive?.boolean ?: false
            )
            emit(status)
            if (status.done) break
            delay(POLL_INTERVAL_MS.milliseconds)
        }
    }
}
