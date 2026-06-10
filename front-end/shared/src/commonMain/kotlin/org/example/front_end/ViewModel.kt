package org.example.front_end

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelShUp : ViewModel() {
    val testData = listOf<List<String>>(
        listOf("P1","John Doe","IPP_Random","10/08/2019","IRM1","FINISHED"),
        listOf("P2","John Doe","IPP_Random","10/08/2019","IRM1","ERROR"),
        listOf("P3","John Doe","IPP_Random","10/08/2019","IRM1","READY"),
        listOf("P4","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_OK"),
        listOf("P5","John Doe","IPP_Random","10/08/2019","IRM1","CHECK_KO"),
    )

    var errorLines = mutableListOf<List<String>>()
    var selectedLines = mutableListOf<List<String>>()

    fun getNbSelectedLines() : Int = selectedLines.size
}