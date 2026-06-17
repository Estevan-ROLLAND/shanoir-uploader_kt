package org.example.front_end.common_elements.utils

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DICOMConfig(
    val hostAddress: String,
    val port: String,
    val aet: String,
    val localHostAddress: String,
    val localPort: String,
    val localAET: String
) {

    fun getDICOMConfigAsJsonElement(): JsonElement {
        return buildJsonObject {
            put("distantDicomServer", buildJsonObject {
                put("host", hostAddress)
                put("port", port)
                put("aet", aet)
            })
            put("localDicomServer", buildJsonObject {
                put("host", localHostAddress)
                put("port", localPort)
                put("aet", localAET)
            })
        }
    }
}