package org.example.front_end

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.example.front_end.common_elements.icons.calendar_month
import org.example.front_end.common_elements.icons.arrow_forward
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.bars.MenuBar
import org.example.front_end.common_elements.bars.NavBar
import org.example.front_end.common_elements.icons.arrow_drop_up
import org.example.front_end.common_elements.icons.file_save
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun LocalDataImportWindow(onNavBarSwitch: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var activeImportType by remember { mutableStateOf("PACS") } // Can be set to "PACS" or "Disk"

            MenuBar()

            /**
             * NAV BAR
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
            ){
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
                    .fillMaxHeight()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /**
                     * Panel PACS Request / Import from Disk
                     */
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .width(653.dp)
                            .fillMaxHeight(.7f),
                        ) {
                        when(activeImportType) {
                            "PACS" -> {
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
                                    Box(
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
                                            },
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "Requêter le PACS",
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(67.dp, 20.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    activeImportType = "Disk"
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "Ajouter depuis le Disk",
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(67.dp, 20.dp)
                                        )
                                    }
                                }

                                // Form
                                Column(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    //.width(550.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    var RBtnSelected by remember { mutableStateOf(true) }

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


                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        Text("Niveau de requête :")
                                        Row(
                                            modifier = Modifier.width(350.dp),
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                RadioButton(
                                                    selected = RBtnSelected,
                                                    onClick = {RBtnSelected = !RBtnSelected},
                                                )
                                                Text("Patient")
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                RadioButton(
                                                    selected = !RBtnSelected,
                                                    onClick = {RBtnSelected = !RBtnSelected}
                                                )
                                                Text("Etude")
                                            }
                                        }

                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Nom du patient : ")
                                        TextField(
                                            modifier=Modifier.width(350.dp),
                                            value = namePatient,
                                            onValueChange = {namePatient = it}
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
                                            onValueChange = {idPatient = it}
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
                                        Text("Description de l'étude : ")
                                        TextField(
                                            modifier=Modifier.width(350.dp),
                                            value = descStudy,
                                            onValueChange = {descStudy = it}
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
                                    Button(
                                        onClick = {},
                                    ){
                                        Text("Requêter le PACS")
                                    }
                                }
                            }

                            "Disk" -> {
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
                                    Box(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    activeImportType = "PACS"
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "Requêter le PACS",
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(67.dp, 20.dp)
                                        )
                                    }

                                    Box(
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
                                            },
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "Ajouter depuis le Disk",
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(67.dp, 20.dp)
                                        )
                                    }
                                }

                                // Add from disk
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(525.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        modifier = Modifier,
//                                            .border(
//                                                width = 2.dp,
//                                                color = Color(0x67,0x50,0xA4),
//                                                shape = RoundedCornerShape(20.dp)),
                                        onClick = {},
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .padding(end = 10.dp),
                                            imageVector = file_save,
                                            contentDescription = "")
                                        Text("Sélectionner un dossier")
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * Panel Study Tree
                     */
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(20.dp)
                            .width(580.dp)
                            .fillMaxHeight(.7f)
                    ) {
                        /**
                         * Here is the tree with all the studies
                         */
                    }

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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Nom : ")
                                    TextField(
                                        modifier=Modifier.width(350.dp),
                                        value = "",
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
                                    Text("Prénom : ")
                                    TextField(
                                        modifier=Modifier.width(350.dp),
                                        value = "",
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
                                    TextField(
                                        modifier=Modifier.width(350.dp),
                                        value = "",
                                        onValueChange = {},
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
                                        modifier=Modifier.width(350.dp),
                                        value = "",
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
                                    Row(
                                        modifier = Modifier.width(350.dp),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            RadioButton(
                                                selected = true,
                                                onClick = {},
                                            )
                                            Text("Féminin")
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = false,
                                                onClick = {}
                                            )
                                            Text("Masculin")
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = false,
                                                onClick = {}
                                            )
                                            Text("Autre")
                                        }
                                    }
                                }
                                Button(
                                    onClick = {},
                                ){
                                    Text("Téléchargement (PACS) ou copier (CD/DVD)")
                                }
                            }
                    }
                }

                // Download Infos Panel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    // Dowload Infos
                    Column(
                        modifier = Modifier
                            .padding(15.dp, 15.dp)
                            .width(1400.dp)
                            .fillMaxHeight()
                            .border(2.dp, Color.Red)
                    ) {
                        Text("Copies ou téléchargement en cours :")

                        // TODO() les bar de téléchargements
                    }

                    Button(
                        modifier = Modifier
                            .padding(40.dp,0.dp),
                        onClick = {},
                    ){
                        Row(
                            modifier = Modifier
                                .width(300.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Voir les imports", fontSize = 30.sp)
                            Icon(imageVector = arrow_forward, "", modifier = Modifier.width(40.dp).height(40.dp))
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