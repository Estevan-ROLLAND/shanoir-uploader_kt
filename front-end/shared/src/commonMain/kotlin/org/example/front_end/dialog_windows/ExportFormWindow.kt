package org.example.front_end.dialog_windows

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import org.example.front_end.DatePickerModalInput
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.icons.arrow_drop_up
import org.example.front_end.common_elements.icons.calendar_month
import org.example.front_end.common_elements.icons.check_box
import org.example.front_end.common_elements.icons.check_box_outline_blank
import org.example.front_end.common_elements.icons.keyboard_arrow_down
import org.example.front_end.common_elements.icons.keyboard_arrow_right
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ExportFormWindow(onClose: () -> Unit) {
    val state = WindowState(
        width = 1000.dp,
        height = 1000.dp
    )

    Window(
        title = "Exporter les données",
        state = state,
        onCloseRequest = onClose,
        alwaysOnTop = true
    ) {
        val scrollState = rememberScrollState()

        var isInfoDICOMVisible by remember { mutableStateOf(true) }

        var isStudyCardsVisible by remember { mutableStateOf(false) }

        var isInfoSujetVisible by remember { mutableStateOf(false) }
        var isComplementaryParamsVisible by remember { mutableStateOf(false) }
        var isPhysicalyInvolvedChecked by remember { mutableStateOf(false) }

        var isInfoExamVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(scrollState)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
                .background(Color.White),
        )
        {
            /**
             * DICOM Informations
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp)
            )
            {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(
                            onClick = {
                                isInfoDICOMVisible = !isInfoDICOMVisible
                            }
                        )
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    var icon by remember { mutableStateOf(keyboard_arrow_down) }

                    if(isInfoDICOMVisible) {
                        icon = keyboard_arrow_down
                    } else {
                        icon = keyboard_arrow_right
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Informations DICOM",
                        fontSize = 25.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(42.dp),
                        imageVector = icon,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier,
                    visible = isInfoDICOMVisible,
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        /**
                         * Center, Address & Station Name
                         */
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            /**
                             * Center
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(150.dp),
                            )
                            {
                                Text(
                                    text = "Centre : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }

                            /**
                             * Center Postal Address
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(65.dp),
                            )
                            {
                                Text(
                                    text = "Adresse du centre : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }

                            /**
                             * Station Name
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(68.dp),
                            )
                            {
                                Text(
                                    text = "Nom de la station : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }
                        }

                        /**
                         * Manufacturer, Model, Magnetic Field & Serial Number
                         */
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            /**
                             * Manufacturer
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(102.dp),
                            )
                            {
                                Text(
                                    text = "Constructeur : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }

                            /**
                             * Model
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(146.dp),
                            )
                            {
                                Text(
                                    text = "Modèle : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }

                            /**
                             * Magnetic Field
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(53.dp),
                            )
                            {
                                Text(
                                    text = "Champ Magnétique : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }

                            /**
                             * Serial Number
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(76.dp),
                            )
                            {
                                Text(
                                    text = "Numéro de série : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .width(200.dp),
                                    value = "",
                                    onValueChange = {},
                                    readOnly = true
                                )
                            }
                        }
                    }
                }
            }

            /**
             * Study Informations / Study Cards
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
            )
            {
                var study by remember { mutableStateOf("") }
                var studyContainsStudyCards = true

                var studyCard by remember { mutableStateOf("") }
                var isStudyCardChecked by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(
                            onClick = {
                                isStudyCardsVisible = !isStudyCardsVisible
                            }
                        )
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    var icon by remember { mutableStateOf(keyboard_arrow_down) }

                    if(isStudyCardsVisible) {
                        icon = keyboard_arrow_down
                    } else {
                        icon = keyboard_arrow_right
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Informations études / Study Cards",
                        fontSize = 25.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(42.dp),
                        imageVector = icon,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier,
                    visible = isStudyCardsVisible,
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            /**
                             * Choose Study
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(70.dp),
                            )
                            {
                                val studies = listOf<String>()
                                var showMenuStudy by remember { mutableStateOf(false) }

                                Text(
                                    text = "Choisir une étude : ",
                                    fontSize = 16.sp
                                )

                                Column {
                                    val iconDrop  = if (showMenuStudy) {
                                        arrow_drop_up
                                    } else {
                                        arrow_drop_down
                                    }
                                    TextField(
                                        modifier=Modifier
                                            .fillMaxWidth(),
                                        value = study,
                                        onValueChange = {study = it},
                                        readOnly = true,
                                        trailingIcon = { Icon(
                                            imageVector = iconDrop,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .clickable(onClick = {
                                                    showMenuStudy = !showMenuStudy
                                                })) }
                                    )
                                    DropdownMenu(
                                        expanded = showMenuStudy,
                                        onDismissRequest = {showMenuStudy = false},
                                        modifier = Modifier.width(with(
                                            LocalDensity.current){
                                            685.dp
                                            }
                                        )
                                    ) {
                                        studies.forEach { studyText ->
                                            DropdownMenuItem(
                                                text = {Text(studyText)},
                                                onClick = {
                                                    study = studyText
                                                    showMenuStudy = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            if (studyContainsStudyCards) {
                                /**
                                 * Checkbox Study Cards
                                 */
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(90.dp),
                                )
                                {

                                    var iconCheckbox by remember { mutableStateOf(check_box_outline_blank) }

                                    iconCheckbox = if (isStudyCardChecked) {
                                        check_box
                                    } else {
                                        check_box_outline_blank
                                    }

                                    Text(
                                        text = "Study Cards ? : ",
                                        fontSize = 16.sp
                                    )

                                    IconButton(
                                        onClick = {
                                            isStudyCardChecked = !isStudyCardChecked
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(32.dp),
                                            imageVector = iconCheckbox,
                                            contentDescription = "",
                                            tint = Color(0x67, 0x50, 0xA4)
                                        )
                                    }
                                }

                                if (isStudyCardChecked) { // TODO() Only when the study has study cards

                                    /**
                                     * Choose Study Card
                                     */
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(35.dp),
                                    )
                                    {
                                        val studies = listOf<String>()
                                        var showMenuStudy by remember { mutableStateOf(false) }

                                        Text(
                                            text = "Choisir une study Card : ",
                                            fontSize = 16.sp
                                        )

                                        Column {
                                            val iconDrop = if (showMenuStudy) {
                                                arrow_drop_up
                                            } else {
                                                arrow_drop_down
                                            }
                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                value = studyCard,
                                                onValueChange = { studyCard = it },
                                                readOnly = true,
                                                trailingIcon = {
                                                    Icon(
                                                        imageVector = iconDrop,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .clickable(onClick = {
                                                                showMenuStudy = !showMenuStudy
                                                            })
                                                    )
                                                }
                                            )
                                            DropdownMenu(
                                                expanded = showMenuStudy,
                                                onDismissRequest = { showMenuStudy = false },
                                                modifier = Modifier.width(
                                                    with(
                                                        LocalDensity.current
                                                    ) {
                                                        686.dp
                                                    }
                                                )
                                            ) {
                                                studies.forEach { studyText ->
                                                    DropdownMenuItem(
                                                        text = { Text(studyText) },
                                                        onClick = {
                                                            studyCard = studyText
                                                            showMenuStudy = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    /**
                                     * Filter Study Cards
                                     */
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(45.dp),
                                    )
                                    {
                                        Text(
                                            text = "Filtrer les study cards : ",
                                            fontSize = 16.sp
                                        )

                                        TextField(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            value = "",
                                            onValueChange = {},
                                            readOnly = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * Subject Informations
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
            )
            {
                var createdSubject by remember { mutableStateOf("") }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(
                            onClick = {
                                isInfoSujetVisible = !isInfoSujetVisible
                            }
                        )
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    var icon by remember { mutableStateOf(keyboard_arrow_down) }

                    if(isInfoSujetVisible) {
                        icon = keyboard_arrow_down
                    } else {
                        icon = keyboard_arrow_right
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Informations sur le sujet",
                        fontSize = 25.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(42.dp),
                        imageVector = icon,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier,
                    visible = isInfoSujetVisible,
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            var subject by remember { mutableStateOf("") }

                            /**
                             * Create Subject
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(50.dp),
                            )
                            {
                                Text(
                                    text = "Créer un sujet (nom) : ",
                                    fontSize = 16.sp
                                )

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = createdSubject,
                                    onValueChange = {createdSubject = it},
                                    readOnly = false
                                )
                            }

                            // TODO() : find a way to only let one choice available

                            /**
                             * Choose Existing Subject
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                            )
                            {
                                val subjects = listOf<String>()
                                var showMenuSubject by remember { mutableStateOf(false) }

                                Text(
                                    text = "OU choisir un sujet existant : ",
                                    fontSize = 16.sp
                                )

                                Column {
                                    val iconDrop  = if (showMenuSubject) {
                                        arrow_drop_up
                                    } else {
                                        arrow_drop_down
                                    }
                                    TextField(
                                        modifier=Modifier
                                            .fillMaxWidth(),
                                        value = subject,
                                        onValueChange = {subject = it},
                                        readOnly = true,
                                        trailingIcon = { Icon(
                                            imageVector = iconDrop,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .clickable(onClick = {
                                                    showMenuSubject = !showMenuSubject
                                                })) }
                                    )
                                    DropdownMenu(
                                        expanded = showMenuSubject,
                                        onDismissRequest = {showMenuSubject = false},
                                        modifier = Modifier.width(with(
                                            LocalDensity.current){
                                            680.dp
                                        }
                                        )
                                    ) {
                                        subjects.forEach { subjectText ->
                                            DropdownMenuItem(
                                                text = {Text(subjectText)},
                                                onClick = {
                                                    subject = subjectText
                                                    showMenuSubject = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            /**
                             * Complementary parameters
                             */
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable(
                                        onClick = {
                                            isComplementaryParamsVisible = !isComplementaryParamsVisible
                                        }
                                    )
                                    .background(Color.LightGray),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            )
                            {
                                var icon by remember { mutableStateOf(keyboard_arrow_down) }

                                icon = if(isComplementaryParamsVisible) {
                                    keyboard_arrow_down
                                } else {
                                    keyboard_arrow_right
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 10.dp),
                                    text = "Paramètres supplémentaires",
                                    fontSize = 15.sp
                                )

                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .size(32.dp),
                                    imageVector = icon,
                                    contentDescription = ""
                                )
                            }

                            AnimatedVisibility(
                                visible = isComplementaryParamsVisible
                            )
                            {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    /**
                                     * Image Category
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    )
                                    {
                                        var imageCategory by remember { mutableStateOf("") }

                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            val categories = listOf<String>()
                                            var showMenuCategeory by remember { mutableStateOf(false) }

                                            Text(
                                                modifier = Modifier
                                                    .width(220.dp),
                                                text = "Catégorie de l'image : ",
                                                fontSize = 16.sp
                                            )

                                            Column {
                                                val iconDrop = if (showMenuCategeory) {
                                                    arrow_drop_up
                                                } else {
                                                    arrow_drop_down
                                                }
                                                TextField(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    value = subject,
                                                    onValueChange = { subject = it },
                                                    readOnly = true,
                                                    trailingIcon = {
                                                        Icon(
                                                            imageVector = iconDrop,
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .clickable(onClick = {
                                                                    showMenuCategeory = !showMenuCategeory
                                                                })
                                                        )
                                                    }
                                                )
                                                DropdownMenu(
                                                    expanded = showMenuCategeory,
                                                    onDismissRequest = { showMenuCategeory = false },
                                                    modifier = Modifier.width(
                                                        with(
                                                            LocalDensity.current
                                                        ) {
                                                            690.dp
                                                        }
                                                    )
                                                ) {
                                                    categories.forEach { cat ->
                                                        DropdownMenuItem(
                                                            text = { Text(cat) },
                                                            onClick = {
                                                                imageCategory = cat
                                                                showMenuCategeory = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /**
                                     * Language Hemispherical Dominance
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    )
                                    {
                                        var hemiLangDominance by remember { mutableStateOf("") }

                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            val langDominance = listOf<String>()
                                            var showMenuLangDominance by remember { mutableStateOf(false) }

                                            Text(
                                                modifier = Modifier
                                                    .width(220.dp),
                                                text = "Dominance hémisphérique du langage : ",
                                                fontSize = 16.sp
                                            )

                                            Column {
                                                val iconDrop = if (showMenuLangDominance) {
                                                    arrow_drop_up
                                                } else {
                                                    arrow_drop_down
                                                }
                                                TextField(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    value = subject,
                                                    onValueChange = { subject = it },
                                                    readOnly = true,
                                                    trailingIcon = {
                                                        Icon(
                                                            imageVector = iconDrop,
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .clickable(onClick = {
                                                                    showMenuLangDominance = !showMenuLangDominance
                                                                })
                                                        )
                                                    }
                                                )
                                                DropdownMenu(
                                                    expanded = showMenuLangDominance,
                                                    onDismissRequest = { showMenuLangDominance = false },
                                                    modifier = Modifier.width(
                                                        with(
                                                            LocalDensity.current
                                                        ) {
                                                            690.dp
                                                        }
                                                    )
                                                ) {
                                                    langDominance.forEach { ldom ->
                                                        DropdownMenuItem(
                                                            text = { Text(ldom) },
                                                            onClick = {
                                                                hemiLangDominance = ldom
                                                                showMenuLangDominance = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /**
                                     * Manual Hemispherical Dominance
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    )
                                    {
                                        var hemiLangDominance by remember { mutableStateOf("") }

                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            val manualDominance = listOf<String>()
                                            var showMenuManualDominance by remember { mutableStateOf(false) }

                                            Text(
                                                modifier = Modifier
                                                    .width(220.dp),
                                                text = "Dominance hémisphérique manuelle : ",
                                                fontSize = 16.sp
                                            )

                                            Column {
                                                val iconDrop = if (showMenuManualDominance) {
                                                    arrow_drop_up
                                                } else {
                                                    arrow_drop_down
                                                }
                                                TextField(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    value = subject,
                                                    onValueChange = { subject = it },
                                                    readOnly = true,
                                                    trailingIcon = {
                                                        Icon(
                                                            imageVector = iconDrop,
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .clickable(onClick = {
                                                                    showMenuManualDominance = !showMenuManualDominance
                                                                })
                                                        )
                                                    }
                                                )
                                                DropdownMenu(
                                                    expanded = showMenuManualDominance,
                                                    onDismissRequest = { showMenuManualDominance = false },
                                                    modifier = Modifier.width(
                                                        with(
                                                            LocalDensity.current
                                                        ) {
                                                            690.dp
                                                        }
                                                    )
                                                ) {
                                                    manualDominance.forEach { mdom ->
                                                        DropdownMenuItem(
                                                            text = { Text(mdom) },
                                                            onClick = {
                                                                hemiLangDominance = mdom
                                                                showMenuManualDominance = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /**
                                     * Personnal Comments
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    )
                                    {
                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            var comment by remember { mutableStateOf("") }

                                            Text(
                                                modifier = Modifier
                                                    .width(220.dp),
                                                text = "Commentaires personnels : ",
                                                fontSize = 16.sp
                                            )

                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                value = comment,
                                                onValueChange = {comment = it},
                                            )
                                        }
                                    }

                                    /**
                                     * ID Subject
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    )
                                    {
                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            var comment by remember { mutableStateOf("") }

                                            Text(
                                                modifier = Modifier
                                                    .width(220.dp),
                                                text = "Identifiant sujet : ",
                                                fontSize = 16.sp
                                            )

                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                value = comment,
                                                onValueChange = {comment = it},
                                            )
                                        }
                                    }

                                    /**
                                     * CheckBox Physicaly Involved
                                     */
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    )
                                    {

                                        var iconCheckbox by remember { mutableStateOf(check_box_outline_blank) }

                                        iconCheckbox = if (isPhysicalyInvolvedChecked) {
                                            check_box
                                        } else {
                                            check_box_outline_blank
                                        }

                                        Text(
                                            modifier = Modifier
                                                .width(210.dp),
                                            text = "Le sujet est-il physiquement impliqué ? : ",
                                            fontSize = 16.sp
                                        )

                                        IconButton(
                                            onClick = {
                                                isPhysicalyInvolvedChecked = !isPhysicalyInvolvedChecked
                                            }
                                        ){
                                            Icon(
                                                modifier = Modifier
                                                    .size(32.dp),
                                                imageVector = iconCheckbox,
                                                contentDescription = "",
                                                tint = Color(0x67,0x50,0xA4)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * Exam Informations
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
            )
            {
                var existingExam by remember { mutableStateOf("") }
                var isCreateExamChecked by remember { mutableStateOf(false) }
                var center by remember { mutableStateOf("") }
                var examDate by remember { mutableStateOf<Long?>(null) }
                var formattedExamDate by remember { mutableStateOf("") }
                var showExamDatePickerDialog by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable(
                            onClick = {
                                isInfoExamVisible = !isInfoExamVisible
                            }
                        )
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    var icon by remember { mutableStateOf(keyboard_arrow_down) }

                    if(isInfoExamVisible) {
                        icon = keyboard_arrow_down
                    } else {
                        icon = keyboard_arrow_right
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        text = "Informations de l'examen",
                        fontSize = 25.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(42.dp),
                        imageVector = icon,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier,
                    visible = isInfoExamVisible,
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            /**
                             * Complete Existing Exam
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(70.dp),
                            )
                            {
                                val exams = listOf<String>()
                                var showMenuExams by remember { mutableStateOf(false) }

                                Text(
                                    text = "Compléter un exam existant : ",
                                    fontSize = 16.sp
                                )

                                Column {
                                    val iconDrop  = if (showMenuExams) {
                                        arrow_drop_up
                                    } else {
                                        arrow_drop_down
                                    }
                                    TextField(
                                        modifier=Modifier
                                            .fillMaxWidth(),
                                        value = existingExam,
                                        onValueChange = {existingExam = it},
                                        readOnly = true,
                                        trailingIcon = { Icon(
                                            imageVector = iconDrop,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .clickable(onClick = {
                                                    showMenuExams = !showMenuExams
                                                })) }
                                    )
                                    DropdownMenu(
                                        expanded = showMenuExams,
                                        onDismissRequest = {showMenuExams = false},
                                        modifier = Modifier.width(with(
                                            LocalDensity.current){
                                            610.dp
                                        })
                                    ) {
                                        exams.forEach { examText ->
                                            DropdownMenuItem(
                                                text = {Text(examText)},
                                                onClick = {
                                                    existingExam = examText
                                                    showMenuExams = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            /**
                             * Checkbox Create Exam
                             */
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(60.dp),
                            )
                            {

                                var iconCheckbox by remember { mutableStateOf(check_box_outline_blank) }

                                iconCheckbox = if (isCreateExamChecked) {
                                    check_box
                                } else {
                                    check_box_outline_blank
                                }

                                Text(
                                    text = "OU créer un nouvel examen : ",
                                    fontSize = 16.sp
                                )

                                IconButton(
                                    onClick = {
                                        isCreateExamChecked = !isCreateExamChecked
                                    }
                                ){
                                    Icon(
                                        modifier = Modifier
                                            .size(32.dp),
                                        imageVector = iconCheckbox,
                                        contentDescription = "",
                                        tint = Color(0x67,0x50,0xA4)
                                    )
                                }
                            }

                            if(isCreateExamChecked){
                                /**
                                 * Exam Center
                                 */
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(70.dp),
                                )
                                {
                                    val examCenters = listOf<String>()
                                    var showMenuCenters by remember { mutableStateOf(false) }

                                    Text(
                                        modifier = Modifier
                                            .width(225.dp),
                                        text = "Centre d'examen : ",
                                        fontSize = 16.sp
                                    )

                                    Column {
                                        val iconDrop  = if (showMenuCenters) {
                                            arrow_drop_up
                                        } else {
                                            arrow_drop_down
                                        }
                                        TextField(
                                            modifier=Modifier
                                                .fillMaxWidth(),
                                            value = center,
                                            onValueChange = {center = it},
                                            readOnly = true,
                                            trailingIcon = { Icon(
                                                imageVector = iconDrop,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .clickable(onClick = {
                                                        showMenuCenters = !showMenuCenters
                                                    })) }
                                        )
                                        DropdownMenu(
                                            expanded = showMenuCenters,
                                            onDismissRequest = {showMenuCenters = false},
                                            modifier = Modifier.width(with(
                                                LocalDensity.current){
                                                610.dp
                                            })
                                        ) {
                                            examCenters.forEach { centerText ->
                                                DropdownMenuItem(
                                                    text = {Text(centerText)},
                                                    onClick = {
                                                        center = centerText
                                                        showMenuCenters = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                /**
                                 * Exam Date
                                 */
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(152.dp),
                                )
                                {
                                    Text(
                                        text = "Date de l'examen : ",
                                        fontSize = 16.sp
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    )
                                    {
                                        if (examDate != null) {
                                            var date = Date(examDate!!)
                                            formattedExamDate = SimpleDateFormat("dd/MM/yyyy").format(date)
                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxWidth(.9f),
                                                value = formattedExamDate,
                                                onValueChange = {},
                                                readOnly = true
                                            )
                                        } else {
                                            Text("Veuillez sélectionner une date ")
                                        }

                                        IconButton(
                                            modifier = Modifier
                                                .border(1.5.dp, Color.Gray, shape = MaterialTheme.shapes.large),
                                            onClick = { showExamDatePickerDialog = true }
                                        ) {
                                            Icon(imageVector = calendar_month, "")
                                        }

                                        if (showExamDatePickerDialog) {
                                            DatePickerModalInput(
                                                onDateSelected = {
                                                    if (it == null) {
                                                        examDate
                                                    } else {
                                                        examDate = it
                                                    }
                                                },
                                                onDismiss = { showExamDatePickerDialog = false }
                                            )
                                        }

                                    }
                                }

                                /**
                                 * Exam Label
                                 */
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(148.dp),
                                )
                                {
                                    Text(
                                        text = "Label de l'examen : ",
                                        fontSize = 16.sp
                                    )

                                    TextField(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        value = "",
                                        onValueChange = {},
                                        readOnly = true
                                    )
                                }
                            }
                        }
                    }
                }
            }

            /**
             * Cancel & Export Buttons
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                Button(
                    onClick = onClose
                ){
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        text = "Annuler"
                    )
                }

                Button(
                    onClick = {
                        onClose()
                    }
                ){
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        text = "Démarer l'export vers le serveur Shanoir")
                }
            }
        }
    }
}