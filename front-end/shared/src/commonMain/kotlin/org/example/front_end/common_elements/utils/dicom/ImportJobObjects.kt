package org.example.front_end.common_elements.utils.dicom

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import kotlinx.serialization.json.put

@Serializable
data class ImportJobRequest(
    val fromPacs: Boolean,
    val patient: PatientRequest,
    val subject: SubjectRequest,
    val study: StudyRequest,
    val selectedSeries: List<SeriesRequest>
)

@Serializable
data class PatientRequest(val patientName: String)

@Serializable
data class SubjectRequest(val identifier: String)

@Serializable
data class StudyRequest(
    val studyInstanceUID: String,
    val studyDate: String,
    val studyDescription: String
)

@Serializable
data class SeriesRequest(
    val seriesInstanceUID: String,
    val seriesNumber: String,
    val seriesDescription: String,
)

fun ImportJobRequest.toJsonString(): String = buildJsonObject {
    put("fromPacs", fromPacs)
    putJsonObject("patient") {
        put("patientName", patient.patientName)
    }
    putJsonObject("subject") {
        put("identifier", subject.identifier)
    }
    putJsonObject("study") {
        put("studyInstanceUID", study.studyInstanceUID)
        put("studyDate", study.studyDate)
        put("studyDescription", study.studyDescription)
    }
    putJsonArray("selectedSeries") {
        selectedSeries.forEach { series ->
            add(buildJsonObject {
            put("seriesInstanceUID", series.seriesInstanceUID)
            put("seriesNumber", series.seriesNumber)
            put("seriesDescription", series.seriesDescription)
            })
        }
    }
}.toString()

@Serializable
data class ImportJobStatus(
    val percentage: Int,
    val currentStep: String,
    val reportSummary: String?,
    val done: Boolean,
    val success: Boolean
)
