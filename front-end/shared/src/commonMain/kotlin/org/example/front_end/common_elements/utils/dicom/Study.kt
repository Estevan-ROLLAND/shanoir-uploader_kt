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

    fun addSerie(serie: Serie) {
        series.toMutableList().add(serie)
    }

    fun removeSerie(serie: Serie) {
        series.toMutableList().remove(serie)
    }

    fun resetSeries() {
        series.toMutableList().clear()
    }

    override fun toString(): String {
        return "Study(studyInstanceUID='$studyInstanceUID', studyDate='$studyDate', studyDescription=$studyDescription, series=$series)"
    }
}