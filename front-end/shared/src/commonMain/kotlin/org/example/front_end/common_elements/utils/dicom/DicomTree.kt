package org.example.front_end.common_elements.utils.dicom

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.scrollableArea
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test.arrow_right
import front_end.shared.generated.resources.Res
import front_end.shared.generated.resources.serie_16x16
import front_end.shared.generated.resources.study_dicom_16x16
import front_end.shared.generated.resources.subject_16x16
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.icons.arrow_drop_up
import org.example.front_end.viewmodel.ViewModelShUp
import org.jetbrains.compose.resources.painterResource

@Composable
fun DicomTree(data: List<Patient>, viewModel: ViewModelShUp, onSelected: (Patient) -> Unit) {
    val horitzontalScrollState = rememberScrollState()

    val heightLazyColumn = if (viewModel.loginHandler.getAccountType() == "OFSEP") .96f else .88f

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(heightLazyColumn)
            .horizontalScroll(horitzontalScrollState),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    )
    {
        items(data.size) { index ->
            DicomTreeItem(patient = data[index], viewModel = viewModel, onSelected = onSelected)
        }
    }
    HorizontalScrollbar(
        modifier = Modifier
            .fillMaxWidth()
        ,
        adapter = rememberScrollbarAdapter(horitzontalScrollState),
        style = ScrollbarStyle(
            minimalHeight = 16.dp,
            thickness = 10.dp,
            shape = RoundedCornerShape(5.dp),
            hoverDurationMillis = 300,
            unhoverColor = Color(0x67,0x50,0xA4).copy(alpha = 0.5f),
            hoverColor = Color(0x67,0x50,0xA4)
        )
    )
}

@Composable
fun DicomTreeItem(patient: Patient, viewModel: ViewModelShUp, onSelected: (Patient) -> Unit) {
    var isPatientExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(end = 8.dp)
            .fillMaxWidth()
    )
    {
        Row(
            modifier= Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(
                        onClick = {
                            isPatientExpanded = !isPatientExpanded
                        }
                    ),
                imageVector = if (isPatientExpanded) arrow_drop_down else arrow_right,
                contentDescription = "Arrow Icon"
            )
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            // Handle study click event here
                            onSelected(patient)
                        }
                    )
            )
            {

                Image(
                    painter = painterResource(Res.drawable.subject_16x16),
                    contentDescription = "Subject Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
                    text = patient.patientName + " [patientID = " + patient.patientId + ", patientBirthDate = " + patient.patientBirthDate + "]",
                )
            }
        }

        if (isPatientExpanded) {
            patient.studies.forEach { study ->
                var isStudyExpanded by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    isStudyExpanded=!isStudyExpanded
                                }
                            ),
                        imageVector = if (isStudyExpanded) arrow_drop_down else arrow_right,
                        contentDescription = "Arrow Icon"
                    )
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onSelected(patient)
                                    viewModel.setSelectedStudy(study)
                                }
                            )
                    )
                    {
                        Image(
                            painter = painterResource(Res.drawable.study_dicom_16x16),
                            contentDescription = "study Icon",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
                            text = "[${study.studyDate}] ${study.studyDescription} + [number of series = ${study.series.size}, studyInstanceUID = ${study.studyInstanceUID}]",
                        )
                    }
                }

                if (isStudyExpanded) {
                    study.series.forEach { serie ->
                        var selected by remember { mutableStateOf(false) }
                        val backgroundColor = if (selected) Color(0xEA, 0xDD, 0xFF) else Color.Transparent
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 65.dp)
                                .background(backgroundColor)
                                .clickable(
                                    onClick = {
                                        onSelected(patient)
                                        selected = !selected
                                        if (selected) {
                                            viewModel.addSelectedSerie(serie)
                                        } else {
                                            viewModel.removeSelectedSerie(serie)
                                        }
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.serie_16x16),
                                contentDescription = "Serie Icon",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp, end= 8.dp),
                                text= "${serie.seriesNumber} [${serie.modality}] ${serie.seriesInstanceUID} (${serie.numberOfSeriesRelatedInstances})",
                            )
                        }
                    }
                }
            }
        }
    }
}