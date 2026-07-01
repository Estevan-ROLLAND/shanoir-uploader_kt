package org.example.front_end.dialog_windows

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.front_end.common_elements.icons.cancel
import org.example.front_end.common_elements.icons.info
import org.example.front_end.common_elements.utils.DICOMConfig
import org.example.front_end.viewmodel.ViewModelShUp

/**
 * DICOM server configuration dialog windows
 */

@Composable
fun ConfigurationDialogWindow(onDismiss: () -> Unit, viewModel: ViewModelShUp) {
    val state = rememberWindowState(
        width = 1000.dp,
        height = 1000.dp,
        position = WindowPosition.Aligned(Alignment.Center)
    )
    Window(
        onCloseRequest = onDismiss,
        title = "Configuration du serveur DICOM",
        state = state,
        alwaysOnTop = true
    ) {
        val dicomConfig = viewModel.DICOMConfig

        var isDistantPACSConnected by remember { mutableStateOf(true) }

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
                    LaunchedEffect(key1 = Unit) {
                        isDistantPACSConnected = viewModel.echoDistantPACS()
                    }

                    PACSVerification(
                        isDistantPACSConnected = isDistantPACSConnected,
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
fun PACSVerification(isDistantPACSConnected: Boolean, onClose: () -> Unit){
    Dialog(
        title = if (isDistantPACSConnected) "Connexion réussie !" else "Connexion échouée !",
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
                    imageVector = if (isDistantPACSConnected) info else cancel,
                    tint = if(isDistantPACSConnected) Color(0x29,0xCF,0x00) else Color(0xCF, 0x00, 0x00),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    Text(
                        text = if (isDistantPACSConnected) "Connexion au PACS distant réussie" else "Connexion au PACS distant échouée",
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