package org.example.front_end.common_elements.utils.dicom

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class Patient(patient: JsonObject) {
    val patientId: String
    val patientName: String
    var patientFirstName: String
        private set
    var patientBirthName: String
        private set
    val patientBirthDate: String
    val patientSex: String
    val patientIdentityRemoved: Boolean
    val deIdentificationMethod: String
    val subject: String
    val studies: List<Study>

    init {
        patientName = patient["patientName"]?.jsonPrimitive?.content ?: ""
        patientId = patient["patientId"]?.jsonPrimitive?.content ?: patientName
        patientFirstName = patient["patientFirstName"]?.jsonPrimitive?.content ?: ""
        patientBirthName = patient["patientBirthName"]?.jsonPrimitive?.content ?: ""
        patientBirthDate = patient["patientBirthDate"]?.jsonPrimitive?.content ?: ""
        patientSex = patient["patientSex"]?.jsonPrimitive?.content ?: ""
        patientIdentityRemoved = patient["patientIdentityRemoved"]?.jsonPrimitive?.booleanOrNull ?: false
        deIdentificationMethod = patient["deIdentificationMethod"]?.jsonPrimitive?.content ?: ""
        subject = patient["subject"]?.jsonPrimitive?.content ?: ""

        val studiesJsonArray = patient["studies"]?.jsonArray ?: emptyList()
        studies = studiesJsonArray.map { Study(it.jsonObject) }
    }

    fun setPatientFirstName(firstName: String) {
        patientFirstName = firstName
    }

    fun setBirthName(birthName: String) {
        patientBirthName = birthName
    }

    override fun toString(): String {
        return "Patient(patientId='$patientId', patientName='$patientName', patientFirstName='$patientFirstName', patientBirthName='$patientBirthName', patientBirthDate='$patientBirthDate', patientSex='$patientSex', patientIdentityRemoved=$patientIdentityRemoved, deIdentificationMethod='$deIdentificationMethod', subject='$subject', studies=$studies)"
    }
}