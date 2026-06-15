package org.example.front_end.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

class ViewModelShUp : ViewModel() {
    val testData = listOf<List<String>>(
        listOf("P1","John Doe","IPP_Random","10/08/2019","IRM1","FINISHED"),
        listOf("P2","John Doe","IPP_Random","10/08/2019","IRM1","ERROR"),
        listOf("P3","John Doe","IPP_Random","10/08/2019","IRM1","READY"),
        listOf("P4","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_OK"),
        listOf("P5","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_KO"),
    )

    var errorLines = mutableListOf<List<String>>()
    var selectedLines by mutableStateOf(mutableListOf<List<String>>())

    var DICOMConfigplaceholder = Pair(mutableMapOf("AET" to "ORTHANC", "HostAddress" to "localhost", "Port" to "4242"), mutableMapOf("LocalAET" to "SHANOIR", "LocalAddress" to "localhost", "LocalPort" to "44105"))
        private set

    val client = HttpClient()

    fun getNbSelectedLines() : Int = selectedLines.size

    fun addLine(data: List<String>){
        if (!selectedLines.contains(data)) {
            selectedLines.add(data)
        }
    }

    fun removeLine(data: List<String>){
        if (selectedLines.contains(data)){
            selectedLines.remove(data)
        }
    }

    fun getDICOMConfig(){
        TODO()
    }

    fun setDICOMConfig(PACS: String, key: String, value: String) {
        when(PACS){
            "distant" -> {
                DICOMConfigplaceholder.first.replace(key, value)
            }

            "local" -> {
                DICOMConfigplaceholder.second.replace(key, value)
            }
        }
    }
}