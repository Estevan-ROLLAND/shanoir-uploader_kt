package org.example.front_end.common_elements.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import front_end.shared.generated.resources.Res
import front_end.shared.generated.resources.logoShUp
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.front_end.common_elements.icons.cancel
import org.example.front_end.common_elements.icons.check
import org.example.front_end.common_elements.icons.info
import org.example.front_end.common_elements.icons.menu
import org.example.front_end.common_elements.icons.settings
import org.example.front_end.common_elements.utils.DICOMConfig
import org.example.front_end.viewmodel.ViewModelShUp
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuBar(viewModel: ViewModelShUp) {
    var isDropDownMenuOpened by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            /**
             * Shanoir Logo
             */
            Image(
                modifier = Modifier
                    .size(42.dp)
                    .clip(
                        shape = RoundedCornerShape(10.dp)
                    ),
                painter = painterResource(Res.drawable.logoShUp),
                contentDescription = ""
            )


            Text(
                text = "ShanoirUploader",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0x67,0x50,0xA4)
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        )
        {
            Text(
                text = "Profil : [Type du profil]"
            )

            IconButton(
                onClick = {
                    isDropDownMenuOpened = !isDropDownMenuOpened
                },
                shape = IconButtonDefaults.smallSquareShape
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = menu,
                    contentDescription = "",
                    tint = Color(0x67,0x50,0xA4)
                )

                DropDownMenuShUp(
                    isOpened = isDropDownMenuOpened,
                    onDismiss = {
                        isDropDownMenuOpened = false
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun DropDownMenuShUp(isOpened: Boolean = true, onDismiss: () -> Unit, viewModel: ViewModelShUp) {
    var isConfigurationMenuOpened by remember { mutableStateOf(false) }
    var isServerConfigurationMenuOpened by remember { mutableStateOf(false) }
    var verifyServer by remember { mutableStateOf(false) }

    var isLangMenuOpened by remember { mutableStateOf(false) }
    var lang by remember { mutableStateOf("fr") }

    DropdownMenu(
        expanded = isOpened,
        onDismissRequest = onDismiss
    ){
        // File
        DropdownMenuItem(
            onClick = {},
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Fichier",
                    fontSize = 15.sp,
                    color = Color(0x67,0x50,0xA4),
                )
            }
        )

        // Configuration
        DropdownMenuItem(
            onClick = {
                isConfigurationMenuOpened = true
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
//                    Icon(
//                        imageVector = settings,
//                        contentDescription = "",
//                        tint = Color(0x67,0x50,0xA4),
//                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Configuration",
                        fontSize = 15.sp,
                        color = Color(0x67,0x50,0xA4)
                    )

                    DropdownMenu(
                        expanded = isConfigurationMenuOpened,
                        onDismissRequest = {isConfigurationMenuOpened = false},
                        offset = DpOffset(x = -292.dp, y=-30.dp)
                    )
                    {
                        DropdownMenuItem(
                            onClick = {
                                isServerConfigurationMenuOpened = true
                                isConfigurationMenuOpened = false
                                onDismiss()
                            },
                            leadingIcon = {
                                Icon(settings,"")
                            },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Configuration du serveur DICOM",
                                    fontSize = 15.sp,
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {verifyServer = !verifyServer},
                            leadingIcon = {
                                if (verifyServer){
                                    Icon(check, "")
                                }
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                )
                                {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Vérifier sur le serveur Shanoir",
                                        fontSize = 15.sp,
                                    )
                                }
                            }
                        )
                    }
                }

            }
        )

        // Language
        DropdownMenuItem(
            onClick = {
                isLangMenuOpened = true
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Langue : $lang",
                        fontSize = 15.sp,
                        color = Color(0x67,0x50,0xA4)
                    )

                    DropdownMenu(
                        expanded = isLangMenuOpened,
                        onDismissRequest = {isLangMenuOpened = false},
                        offset = DpOffset(x = -133.dp, y=-30.dp)
                    )
                    {
                        DropdownMenuItem(
                            onClick = {
                                lang = "fr"
                            },
                            leadingIcon = {
                                if (lang == "fr"){
                                    Icon(check, "")
                                }
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                )
                                {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Français",
                                        fontSize = 15.sp,
                                    )
                                }
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                lang = "en"
                            },
                            leadingIcon = {
                                if (lang == "en"){
                                    Icon(check, "")
                                }
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                )
                                {

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "English",
                                        fontSize = 15.sp,
                                    )
                                }
                            }
                        )
                    }
                }

            }
        )

        // Verify Updates
        DropdownMenuItem(
            onClick = {},
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Vérifier les mises à jour",
                        fontSize = 15.sp,
                        color = Color(0x67,0x50,0xA4)
                    )
                }
            }
        )

        // About ShUp
        DropdownMenuItem(
            onClick = {},
            leadingIcon = {
                Icon(
                    imageVector = info,
                    contentDescription = "",
                    tint = Color(0x67,0x50,0xA4),
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "A propos",
                        fontSize = 15.sp,
                        color = Color(0x67,0x50,0xA4)
                    )
                }
            }
        )
    }

    if (isServerConfigurationMenuOpened) {
        ConfigurationDialogWindow({isServerConfigurationMenuOpened = false}, viewModel = viewModel)
    }
}

@Composable
fun ConfigurationDialogWindow(onDismiss: () -> Unit, viewModel: ViewModelShUp) {
    val state = rememberWindowState(
        width = 1000.dp,
        height = 1000.dp,
    )
    Window(
        onCloseRequest = onDismiss,
        title = "Configuration du serveur DICOM",
        state = state,
        alwaysOnTop = true
    ) {
        val dicomConfig = viewModel.DICOMConfig

        var isDistantPACSConnected by remember { mutableStateOf(true) } // TODO() Implement connection check logic

        var isErrorWindowOpened by remember { mutableStateOf(false) }
        var isVerificationDialogOpened by remember { mutableStateOf(false) }
        var isValidationDialogOpened by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel.getDICOMConfig()
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            val distant = dicomConfig.jsonObject["distantDicomServer"]?.jsonObject
            var aet by remember(dicomConfig) { mutableStateOf(distant?.get("aet")?.jsonPrimitive?.contentOrNull.orEmpty()) }
            var hostAddress by remember(dicomConfig) { mutableStateOf(distant?.get("host")?.jsonPrimitive?.contentOrNull.orEmpty()) }
            var port by remember(dicomConfig) { mutableStateOf(distant?.get("port")?.jsonPrimitive?.contentOrNull.orEmpty()) }

            val local = dicomConfig.jsonObject["localDicomServer"]?.jsonObject
            var localAET by remember(dicomConfig) { mutableStateOf(local?.get("aet")?.jsonPrimitive?.contentOrNull.orEmpty()) }
            var localHostAddress by remember(dicomConfig) { mutableStateOf(local?.get("host")?.jsonPrimitive?.contentOrNull.orEmpty()) }
            var localPort by remember(dicomConfig) { mutableStateOf(local?.get("port")?.jsonPrimitive?.contentOrNull.orEmpty()) }

            /**
             * Distant PACS
             */
            Column(
                modifier = Modifier
                    .width(600.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            )
            {
                Text(
                    text = "Paramètres de configuration du PACS distant :",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AET :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = aet,
                        onValueChange = {
                            aet = it

                        },
                        singleLine = true
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Adresse de l'hôte :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = hostAddress,
                        onValueChange = {
                            hostAddress = it
                        },
                        singleLine = true

                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Port :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = port,
                        onValueChange = {
                            port = it
                        },
                        singleLine = true
                    )
                }
            }

            /**
             * Local PACS
             */
            Column(
                modifier = Modifier
                    .width(600.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            )
            {
                Text(
                    text = "Paramètres de configuration du PACS local :",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AET local :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = localAET,
                        onValueChange = {
                            localAET = it
                        },
                        singleLine = true
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Adresse locale :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = localHostAddress,
                        onValueChange = {
                            localHostAddress = it
                        },
                        singleLine = true
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Port local :"
                    )
                    TextField(
                        modifier = Modifier.width(280.dp),
                        value = localPort,
                        onValueChange = {
                            localPort = it
                        },
                        singleLine = true
                    )
                }
            }

            /**
             * Verifications and Configuration Buttons
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        if (!isDistantPACSConnected) {
                            isErrorWindowOpened = true
                        } else {
                            isVerificationDialogOpened = true
                        }
                    }
                ){
                    Text("Vérifier la connexion au PACS distant")
                }

                Button(
                    onClick = {
                        if (!isDistantPACSConnected) {
                            isErrorWindowOpened = true
                        } else {
                            isValidationDialogOpened = true

                        }
                    }
                ) {
                    Text("Configurer le serveur")
                }

                if (isErrorWindowOpened) {
                    PACSConnectionFailed(
                        onClose = {
                            isErrorWindowOpened = false
                        }
                    )

                }

                if (isVerificationDialogOpened) {
                    PACSVerification(
                        onClose = {
                            isVerificationDialogOpened = false
                        }
                    )

                }

                if (isValidationDialogOpened) {
                    PACSConfigValidation(
                        onClose = {
                            isValidationDialogOpened = false
                            onDismiss()
                        }
                    )

                    LaunchedEffect(Unit){
                        val config = DICOMConfig(
                            aet = aet,
                            hostAddress = hostAddress,
                            port = (port.toIntOrNull() ?: 0).toString(),
                            localAET = localAET,
                            localHostAddress = localHostAddress,
                            localPort = (localPort.toIntOrNull() ?: 0).toString()
                        )
                        viewModel.DICOMConfig = config.getDICOMConfigAsJsonElement()
                        viewModel.setDICOMConfig()
                    }
                }
            }

            Text(
                text = "ATTENTION : vérifiez avec l'administrateur du PACS que le DICOM C-MOVE est activé pour la connexion !!!",
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier
                    .border(1.dp, Color.LightGray)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                /**
                 * Here will be the logs from the connection verification
                 */
            }
        }
    }
}

@Composable
fun PACSConfigValidation(onClose: () -> Unit) {
    Dialog(
        title = "Serveur configuré !",
        state = DialogState(
            width = 500.dp,
            height = 180.dp
        ),
        onCloseRequest = onClose
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.End
        )
        {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    imageVector = info,
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = "Félicitations ! La connexion au PACS est configurée",
                    )
                    Text(
                        text = "Redémarrez l'application ShanoirUploader",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                onClose
            ){
                Text("OK")
            }
        }
    }
}

@Composable
fun PACSConnectionFailed(onClose: () -> Unit){
    Dialog(
        title = "Connexion échouée !",
        state = DialogState(
            width = 500.dp,
            height = 155.dp
        ),
        onCloseRequest = onClose
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.End
        )
        {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    imageVector = cancel,
                    tint = Color(0xCF, 0x00, 0x00),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = "Connexion au PACS distant échouée",
                    )
                }
            }
            Button(
                onClose
            ){
                Text("OK")
            }
        }
    }
}


@Composable
fun PACSVerification(onClose: () -> Unit){
    Dialog(
        title = "Connexion échouée !",
        state = DialogState(
            width = 500.dp,
            height = 155.dp
        ),
        onCloseRequest = onClose
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.End
        )
        {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    modifier = Modifier
                        .size(48.dp),
                    imageVector = cancel,
                    tint = Color(0xCF, 0x00, 0x00),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = "Connexion au PACS distant échouée",
                    )
                }
            }
            Button(
                onClose
            ){
                Text("OK")
            }
        }
    }
}