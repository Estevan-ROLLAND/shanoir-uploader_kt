package org.example.front_end

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.launch
import org.example.front_end.common_elements.bars.CategoryBarElement
import org.example.front_end.common_elements.icons.calendar_month
import org.example.front_end.common_elements.icons.arrow_forward
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.bars.MenuBar
import org.example.front_end.common_elements.icons.arrow_drop_up
import org.example.front_end.common_elements.icons.file_save
import org.example.front_end.common_elements.icons.info
import org.example.front_end.common_elements.utils.dicom.DicomTree
import org.example.front_end.common_elements.utils.dicom.ImportJobRequest
import org.example.front_end.common_elements.utils.dicom.ImportJobStatus
import org.example.front_end.common_elements.utils.dicom.Patient
import org.example.front_end.common_elements.utils.dicom.PatientRequest
import org.example.front_end.common_elements.utils.dicom.SeriesRequest
import org.example.front_end.common_elements.utils.dicom.StudyRequest
import org.example.front_end.common_elements.utils.dicom.SubjectRequest
import org.example.front_end.viewmodel.ViewModelShUp
import java.awt.FileDialog
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.swing.JFileChooser

@Composable
fun LocalDataImportWindow(viewModel: ViewModelShUp, onNavBarSwitch: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var activeImportType by remember { mutableStateOf("PACS") } // Can be set to "PACS" or "Disk"

            /**
             * NAV BAR
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
            )
            {
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = {}
                        )
                        .background(Color(0xEA,0xDD,0xFF))
                        .drawBehind{
                            val bordersize = 4.dp.toPx()
                            drawLine(
                                color = Color(0x67,0x50,0xA4),
                                start = Offset(0f, size.height-2f),
                                end = Offset(size.width, size.height-2f),
                                strokeWidth = bordersize
                            )
                        }
                ){
                    Text(
                        text = "Préparation locale des données",
                        modifier = Modifier
                            .padding(30.dp,15.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onNavBarSwitch()
                            }
                        )
                ) {
                    Text(
                        text = "Exporter vers le serveur (Shanoir)",
                        modifier = Modifier
                            .padding(30.dp,15.dp)
                    )
                }
            }

            /**
             * WINDOW CONTENT
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var patientSelected : Patient? by remember { mutableStateOf(viewModel.getSelectedPatient()) }
                    var queryLaunched by remember { mutableStateOf(false) }

                    /**
                     * Panel PACS Request / Import from Disk
                     */
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .width(653.dp)
                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind{
                                    val bordersize = 1.dp.toPx()
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, size.height-2f),
                                        end = Offset(size.width, size.height-2f),
                                        strokeWidth = bordersize
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CategoryBarElement("Requêter le PACS", (activeImportType=="PACS"),{activeImportType="PACS"},20.sp, 20)
                            CategoryBarElement("Ajouter depuis le Disk", (activeImportType == "Disk"),{activeImportType="Disk"}, 20.sp, 20)
                        }


                        when(activeImportType) {
                            "PACS" -> {
                                // Form
                                Column(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    //.width(550.dp),
                                    verticalArrangement = Arrangement.spacedBy(13.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    var requestLevel by remember { mutableStateOf("Patient") } // Can be set to "Patient", "Study"

                                    var namePatient by remember { mutableStateOf("") }

                                    var idPatient by remember { mutableStateOf("") }

                                    var birthdayPatient by remember { mutableStateOf<Long?>(null) }
                                    var formattedBirthDay by remember { mutableStateOf("") }
                                    var showBirthDatePickerDialog by remember { mutableStateOf(false) }

                                    var descStudy by remember { mutableStateOf("") }

                                    var studyDate by remember { mutableStateOf<Long?>(null) }
                                    var formattedStudyDate by remember { mutableStateOf("") }
                                    var showStudyDatePickerDialog by remember { mutableStateOf(false) }

                                    val modalities = listOf<String>("None","MR","CT", "PT", "NM")
                                    var modalityStudy by rememberSaveable { mutableStateOf(modalities[0]) }
                                    var showMenuModality by remember { mutableStateOf(false) }

                                    var isQueryBtnEnabled by remember { mutableStateOf(false) }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        val requestTypeRadioOptions = listOf("Patient", "Study")
                                        val selectedReqTypeOption = remember { mutableStateOf(requestTypeRadioOptions[0]) }

                                        Row {
                                            Text("Niveau de requête : ")
                                            TooltipBox(
                                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
                                                tooltip = { PlainTooltip { Text("Sélectionner en fonction de la configuration du PACS") } },
                                                state = rememberTooltipState()
                                            ) {
                                                Icon(info, "")
                                            }
                                        }

                                        RadioButtonGroup(requestTypeRadioOptions, selectedReqTypeOption, onSelected = {selectedReqTypeOption.value = it; requestLevel = it })
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row {
                                            Text("Nom, Prénom du patient : ")
                                            TooltipBox(
                                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
                                                tooltip = { PlainTooltip { Text("Nom et prénoms doivent être séparés par une virgule. Seules les premières lettres du nom suffisent") } },
                                                state = rememberTooltipState()
                                            ) {
                                                Icon(info, "")
                                            }
                                        }
                                        TextField(
                                            modifier=Modifier.width(350.dp),
                                            value = namePatient,
                                            onValueChange = {namePatient = it},
                                            placeholder = { Text("NOM, Prénom") },
                                            singleLine = true
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        Text("ID du patient : ")
                                        TextField(
                                            modifier=Modifier.width(350.dp),
                                            value = idPatient,
                                            onValueChange = {idPatient = it},
                                            placeholder = { Text("ID") },
                                            singleLine = true
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Date de naissance du patient : ")
                                        Row(
                                            modifier = Modifier.width(350.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (birthdayPatient != null){
                                                var date = Date(birthdayPatient!!)
                                                formattedBirthDay = SimpleDateFormat("dd/MM/yyyy").format(date)
                                                TextField(
                                                    value = formattedBirthDay,
                                                    onValueChange = {},
                                                    readOnly = true
                                                )
                                            }else{
                                                Text("Veuillez sélectionner une date ")
                                            }

                                            IconButton(
                                                modifier = Modifier
                                                    .border(1.5.dp, Color.Gray, shape = MaterialTheme.shapes.large),
                                                onClick = {showBirthDatePickerDialog = true}
                                            ){
                                                Icon(imageVector = calendar_month,"")
                                            }

                                            if (showBirthDatePickerDialog) {
                                                DatePickerModalInput(onDateSelected = {
                                                    if (it == null){
                                                        birthdayPatient
                                                    }else{
                                                        birthdayPatient = it
                                                    }
                                                },
                                                    onDismiss = {showBirthDatePickerDialog = false}
                                                )
                                            }

                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row {
                                            Text("Description de l'étude : ")
                                            TooltipBox(
                                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
                                                tooltip = { PlainTooltip { Text("Nom de l'examen, un seul mot suffit, en respectant la casse") } },
                                                state = rememberTooltipState()
                                            ) {
                                                Icon(info, "")
                                            }
                                        }
                                        TextField(
                                            modifier=Modifier.width(350.dp),
                                            value = descStudy,
                                            onValueChange = {descStudy = it},
                                            placeholder = { Text("Description") }
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Date de l'étude : ")
                                        Row(
                                            modifier = Modifier.width(350.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (studyDate != null){
                                                var date = Date(studyDate!!)
                                                formattedStudyDate = SimpleDateFormat("dd/MM/yyyy").format(date)
                                                TextField(
                                                    value = formattedStudyDate,
                                                    onValueChange = {},
                                                    readOnly = true
                                                )
                                            }else{
                                                Text("Veuillez sélectionner une date ")
                                            }

                                            IconButton(
                                                modifier = Modifier
                                                    .border(1.5.dp, Color.Gray, shape = MaterialTheme.shapes.large),
                                                onClick = {showStudyDatePickerDialog = true}
                                            ){
                                                Icon(imageVector = calendar_month,"")
                                            }

                                            if (showStudyDatePickerDialog) {
                                                DatePickerModalInput(onDateSelected = {
                                                    if (it == null){
                                                        studyDate
                                                    }else{
                                                        studyDate = it
                                                    }
                                                },
                                                    onDismiss = {showStudyDatePickerDialog = false}
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Modalité : ")
                                        Column {
                                            val iconDrop  = if (showMenuModality) {
                                                arrow_drop_up
                                            } else {
                                                arrow_drop_down
                                            }
                                            TextField(
                                                modifier=Modifier.width(350.dp),
                                                value = modalityStudy,
                                                onValueChange = {modalityStudy = it},
                                                readOnly = true,
                                                trailingIcon = { Icon(
                                                    imageVector = iconDrop,
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .clickable(onClick = {
                                                            showMenuModality = !showMenuModality
                                                        })) }
                                            )
                                            DropdownMenu(
                                                expanded = showMenuModality,
                                                onDismissRequest = {showMenuModality = false},
                                                modifier = Modifier.width(with(
                                                    LocalDensity.current){
                                                    350.dp
                                                })
                                            ) {
                                                modalities.forEach { modality ->
                                                    DropdownMenuItem(
                                                        text = {Text(modality)},
                                                        onClick = {
                                                            modalityStudy = modality
                                                            showMenuModality = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // if all the fields are empty, the button is disabled
                                    isQueryBtnEnabled = if (namePatient.isEmpty() && idPatient.isEmpty() && birthdayPatient == null && descStudy.isEmpty() && studyDate == null && modalityStudy=="None"){
                                        false
                                    } else {
                                        true
                                    }

                                    Button(
                                        modifier = Modifier
                                            .padding(top = 16.dp),
                                        enabled = isQueryBtnEnabled,
                                        onClick = {
                                            queryLaunched = true
                                        },
                                    ){
                                        Text("Requêter le PACS")
                                    }

                                    if (queryLaunched) {
                                        LaunchedEffect(Unit) {
                                            viewModel.setSelectedPatient(null)
                                            patientSelected = null
                                            viewModel.queryDistantPACS(
                                                requestLevel == "Etude",
                                                modalityStudy,
                                                namePatient,
                                                idPatient,
                                                descStudy,
                                                formattedBirthDay,
                                                formattedStudyDate
                                            )
                                            queryLaunched = false
                                        }
                                    }
                                }
                            }

                            "Disk" -> {
                                var isFilePickerOpened by remember { mutableStateOf(false) }
                                var selectedFile by remember { mutableStateOf<File?>(null) }

                                // Add from disk
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(565.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            isFilePickerOpened = true
                                        },
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(end = 10.dp),
                                            imageVector = file_save,
                                            contentDescription = "")
                                        Text("Sélectionner un fichier")
                                    }
                                }

                                if (isFilePickerOpened) {
                                    val fileChooser = JFileChooser()
                                    val result = fileChooser.showOpenDialog(null)
                                    if (result == JFileChooser.APPROVE_OPTION) {
                                        selectedFile = fileChooser.selectedFile
                                        isFilePickerOpened = false
                                    } else if (result == JFileChooser.CANCEL_OPTION) {
                                        isFilePickerOpened = false
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * When the selected profile is "OFSEP", the verification panel is displayed. Otherwise, it is hidden
                     *  and the Tree panel becomes wider. The import button is at the bottom of the tree panel.
                     */
                    val profile = viewModel.loginHandler.getAccountType()
                    var treePanelWidth = .99f
                    if (profile=="OFSEP") {
                        treePanelWidth = .5f
                    }
                    val scope = rememberCoroutineScope()

                    /**
                     * Panel Study Tree
                     */
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(10.dp)
                            .fillMaxWidth(treePanelWidth)
                            .fillMaxHeight(.707f),
                        verticalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        if (!queryLaunched && viewModel.getPatients().isNotEmpty()) {
                            /**
                             * Here is the tree with all the studies
                             */
                            DicomTree(viewModel.getPatients(), viewModel, onSelected = { patient ->
                                viewModel.setSelectedPatient(patient)
                                patientSelected = patient
                            })
                        }


                        if (profile != "OFSEP") {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .drawBehind{
                                        val bordersize = 1.dp.toPx()
                                        drawLine(
                                            color = Color.LightGray,
                                            start = Offset(0f, -2f),
                                            end = Offset(size.width, -2f),
                                            strokeWidth = bordersize
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        val selectedPatient = patientSelected ?: run {
                                            viewModel.logger.writeLog("Téléchargement annulé : aucun patient sélectionné.")
                                            return@Button
                                        }
                                        val selectedStudy = selectedPatient.studies.firstOrNull() ?: run {
                                            viewModel.logger.writeLog("Téléchargement annulé : aucune étude sélectionnée pour le patient ${selectedPatient.patientName}.")
                                            return@Button
                                        }

                                        val importJob = ImportJobRequest(
                                            fromPacs = true,
                                            patient = PatientRequest(patientName = selectedPatient.patientName),
                                            subject = SubjectRequest(identifier = selectedPatient.subject),
                                            study = StudyRequest(
                                                studyInstanceUID = selectedStudy.studyInstanceUID,
                                                studyDate = selectedStudy.studyDate,
                                                studyDescription = selectedStudy.studyDescription ?: ""
                                            ),
                                            selectedSeries = selectedStudy.series.map { series ->
                                                SeriesRequest(
                                                    seriesInstanceUID = series.seriesInstanceUID,
                                                    seriesNumber = series.seriesNumber,
                                                    seriesDescription = series.seriesDescription ?: ""
                                                )
                                            }
                                        )

                                        scope.launch {
                                            viewModel.retrieveData(importJob)
                                        }
                                    }
                                ) {
                                    Text("Importer l'examen")
                                }
                            }
                        }
                    }

                    if(profile == "OFSEP") {
                        /**
                         * Panel Verification
                         */
                        Column(
                            modifier = Modifier
                                .background(color = Color.White)
                                .padding(20.dp)
                                .width(550.dp),
                        ) {
                            Text(
                                text = "3. Vérification du patient",
                                fontSize = 30.sp
                            )
                            // Form
                            Column(
                                modifier = Modifier
                                    .padding(10.dp),
                                //.width(550.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                val genderRadioOptions = listOf("Féminin", "Masculin", "Autre")
                                val selectedGenderOption = remember { mutableStateOf(patientSelected?.patientSex) }
                                if (patientSelected?.patientSex == "null") {
                                    selectedGenderOption.value = "Autre"
                                }
                                var patientBirthName by remember { mutableStateOf("") }
                                if (!queryLaunched && patientSelected != null) {
                                    patientBirthName = patientSelected!!.patientBirthName
                                } else if (patientSelected == null) {
                                    patientBirthName = ""
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Nom : ")
                                    TextField(
                                        modifier=Modifier.width(330.dp),
                                        value = patientSelected?.patientName ?: "",
                                        onValueChange = {}, // is not editable
                                        readOnly = true
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Prénom : ")
                                    TextField(
                                        modifier=Modifier.width(330.dp),
                                        value = patientSelected?.patientFirstName ?: "",
                                        onValueChange = {},
                                        readOnly = true
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Nom de naissance : ")
                                    TooltipBox(
                                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
                                        tooltip = { PlainTooltip { Text("Par défaut, le nom du patient est copié. Vous pouvez le changer si le nom de naissance est différent.") } },
                                        state = rememberTooltipState()
                                    ) {
                                        Icon(info, "")
                                    }
                                    TextField(
                                        modifier=Modifier.width(330.dp),
                                        value = patientBirthName,
                                        onValueChange = {
                                            patientBirthName = it
                                        },
                                        readOnly = false
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Date de naissance : ")
                                    TextField(
                                        modifier=Modifier.width(330.dp),
                                        value = patientSelected?.patientBirthDate ?: "",
                                        onValueChange = {},
                                        readOnly = true
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text("Sexe :")
                                    RadioButtonGroup(genderRadioOptions, selectedGenderOption as MutableState<String>)
                                }
                                Button(
                                    onClick = {
                                        val selectedPatient = patientSelected ?: run {
                                            viewModel.logger.writeLog("Téléchargement annulé : aucun patient sélectionné.")
                                            return@Button
                                        }
                                        val selectedStudy = selectedPatient.studies.firstOrNull() ?: run {
                                            viewModel.logger.writeLog("Téléchargement annulé : aucune étude sélectionnée pour le patient ${selectedPatient.patientName}.")
                                            return@Button
                                        }

                                        selectedPatient.setBirthName(patientBirthName)
                                        viewModel.logger.writeLog("Patient vérifié : $selectedPatient")

                                        val importJob = ImportJobRequest(
                                            fromPacs = true,
                                            patient = PatientRequest(patientName = selectedPatient.patientName),
                                            subject = SubjectRequest(identifier = selectedPatient.subject),
                                            study = StudyRequest(
                                                studyInstanceUID = selectedStudy.studyInstanceUID,
                                                studyDate = selectedStudy.studyDate,
                                                studyDescription = selectedStudy.studyDescription ?: ""
                                            ),
                                            selectedSeries = selectedStudy.series.map { series ->
                                                SeriesRequest(
                                                    seriesInstanceUID = series.seriesInstanceUID,
                                                    seriesNumber = series.seriesNumber,
                                                    seriesDescription = series.seriesDescription ?: ""
                                                )
                                            }
                                        )

                                        patientBirthName = ""
                                        patientSelected = null

                                        scope.launch {
                                            viewModel.retrieveData(importJob)
                                        }
                                    },
                                ) {
                                    Text("Téléchargement (PACS) ou copier (CD/DVD)")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }){
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss){
                Text("Cancel")
            }
        },
    ){
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}

@Composable
fun RadioButtonGroup(radioOptionList: List<String>,selectedRadioBtnState: MutableState<String>, onSelected: (String)-> Unit = {}) {
    Row(
        modifier = Modifier
            .width(350.dp)
            .selectableGroup()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        radioOptionList.forEach { rOption ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (rOption == selectedRadioBtnState.value),
                        onClick = { onSelected(rOption) },
                        role = Role.RadioButton
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RadioButton(
                    selected = (rOption == selectedRadioBtnState.value),
                    onClick = null,
                )
                Text(
                    text = rOption,
                )
            }
        }
    }
}
