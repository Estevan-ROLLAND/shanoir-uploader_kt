package org.example.front_end.common_elements.utils.dicom

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class Study(jsonObject: JsonObject) {
    val studyInstanceUID: String
    val studyDate: String
    val studyDescription: String?
    val series: List<Serie>


    init {
        studyInstanceUID = jsonObject["studyInstanceUID"]?.jsonPrimitive?.content ?: ""
        studyDate = jsonObject["studyDate"]?.jsonPrimitive?.content ?: ""
        studyDescription = jsonObject["studyDescription"]?.jsonPrimitive?.contentOrNull
        val seriesJsonArray = jsonObject["series"]?.jsonArray ?: emptyList()
        series = seriesJsonArray.map { Serie(it.jsonObject) }
    }

    override fun toString(): String {
        return "Study(studyInstanceUID='$studyInstanceUID', studyDate='$studyDate', studyDescription=$studyDescription, series=$series)"
    }
}