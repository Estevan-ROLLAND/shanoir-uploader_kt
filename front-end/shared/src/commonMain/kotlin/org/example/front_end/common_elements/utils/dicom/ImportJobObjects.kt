package org.example.front_end.common_elements.utils.dicom

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import kotlinx.serialization.json.put
import java.util.Objects

@Serializable
data class ImportJobRequest(
    val fromPacs: Boolean,
    val patient: PatientRequest,
    val subject: SubjectRequest,
    val study: StudyRequest,
    val selectedSeries: List<SeriesRequest>
)

@Serializable
data class PatientRequest(
    val patientID: String,
    val patientName: String,
    val patientBirthDate: String,
    val patientSex: String
)

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
    val selected: Boolean = true,
    val equipment: EquipmentRequest
)

@Serializable
data class EquipmentRequest(
    val manufacturer: String? = null,
    val manufacturerModelName: String? = null,
    val deviceSerialNumber: String? = null,
    val stationName: String? = null,
    val magneticFieldStrength: String? = null,
    val modality: String? = null
)

fun ImportJobRequest.toJsonString(): String = buildJsonObject {
    put("fromPacs", fromPacs)
    putJsonObject("patient") {
        put("patientID", patient.patientID)
        put("patientName", patient.patientName)
        put("patientBirthDate", patient.patientBirthDate)
        put("patientSex", patient.patientSex)
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
                    put("selected", series.selected)
                    putJsonObject("equipment") {
                        put("manufacturer", series.equipment.manufacturer)
                        put("manufacturerModelName", series.equipment.manufacturerModelName)
                        put("deviceSerialNumber", series.equipment.deviceSerialNumber)
                        put("stationName", series.equipment.stationName)
                        put("magneticFieldStrength", series.equipment.magneticFieldStrength)
                        put("modality", series.equipment.modality)
                    }
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