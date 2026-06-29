package org.example.front_end.common_elements.utils.dicom

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class Serie(jsonObject: JsonObject) {
    val selected: Boolean
    val ignored: Boolean
    val erroneous: Boolean
    val errorMessage: String?
    val seriesInstanceUID: String
    val modality: String
    val protocolName: String
    val seriesDescription: String?
    val sequenceName: String?
    val seriesDate: String
    val seriesNumber: String
    val acquisitionTime: String
    val numberOfSeriesRelatedInstances: Int
    val sopClassUID: String?
    val equipement: Map<String, String?>
    val institution: String?
    val isCompressed: Boolean
    val isSpectroscopy: Boolean
    val isEnhanced: Boolean
    val isMultiFrame: Boolean
    val multiFrameCount: Int?
    val instances: String?
    val images: String?
    val imagesNumber: Int?
    val datasets: String?


    init {
        selected = jsonObject["selected"]?.jsonPrimitive?.booleanOrNull ?: false
        ignored = jsonObject["ignored"]?.jsonPrimitive?.booleanOrNull ?: false
        erroneous = jsonObject["erroneous"]?.jsonPrimitive?.booleanOrNull ?: false
        errorMessage = jsonObject["errorMessage"]?.jsonPrimitive?.contentOrNull
        seriesInstanceUID = jsonObject["seriesInstanceUID"]?.jsonPrimitive?.content ?: ""
        modality = jsonObject["modality"]?.jsonPrimitive?.content ?: ""
        protocolName = jsonObject["protocolName"]?.jsonPrimitive?.content ?: ""
        seriesDescription = jsonObject["seriesDescription"]?.jsonPrimitive?.contentOrNull
        sequenceName = jsonObject["sequenceName"]?.jsonPrimitive?.contentOrNull
        seriesDate = jsonObject["seriesDate"]?.jsonPrimitive?.content ?: ""
        seriesNumber = jsonObject["seriesNumber"]?.jsonPrimitive?.content ?: ""
        acquisitionTime = jsonObject["acquisitionTime"]?.jsonPrimitive?.content ?: ""
        numberOfSeriesRelatedInstances = jsonObject["numberOfSeriesRelatedInstances"]?.jsonPrimitive?.intOrNull ?: 0
        sopClassUID = jsonObject["sopClassUID"]?.jsonPrimitive?.contentOrNull
        equipement = jsonObject["equipement"]?.jsonObject?.mapValues { it.value.jsonPrimitive.contentOrNull } ?: emptyMap()
        institution = jsonObject["institution"]?.jsonPrimitive?.contentOrNull
        isCompressed = jsonObject["isCompressed"]?.jsonPrimitive?.booleanOrNull ?: false
        isSpectroscopy = jsonObject["isSpectroscopy"]?.jsonPrimitive?.booleanOrNull ?: false
        isEnhanced = jsonObject["isEnhanced"]?.jsonPrimitive?.booleanOrNull ?: false
        isMultiFrame = jsonObject["isMultiFrame"]?.jsonPrimitive?.booleanOrNull ?: false
        multiFrameCount = jsonObject["multiFrameCount"]?.jsonPrimitive?.intOrNull
        instances = jsonObject["instances"]?.jsonPrimitive?.contentOrNull
        images = jsonObject["images"]?.jsonPrimitive?.contentOrNull
        imagesNumber = jsonObject["imagesNumber"]?.jsonPrimitive?.intOrNull
        datasets = jsonObject["datasets"]?.jsonPrimitive?.contentOrNull
    }

    override fun toString(): String {
        return "Serie(selected=$selected, ignored=$ignored, erroneous=$erroneous, errorMessage=$errorMessage, seriesInstanceUID='$seriesInstanceUID', modality='$modality', protocolName='$protocolName', seriesDescription=$seriesDescription, sequenceName=$sequenceName, seriesDate='$seriesDate', seriesNumber='$seriesNumber', acquisitionTime='$acquisitionTime', numberOfSeriesRelatedInstances=$numberOfSeriesRelatedInstances, sopClassUID=$sopClassUID, equipement=$equipement, institution=$institution, isCompressed=$isCompressed, isSpectroscopy=$isSpectroscopy, isEnhanced=$isEnhanced, isMultiFrame=$isMultiFrame, multiFrameCount=$multiFrameCount, instances=$instances, images=$images, imagesNumber=$imagesNumber, datasets=$datasets)"
    }
}