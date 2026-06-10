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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import front_end.shared.generated.resources.Res
import front_end.shared.generated.resources.logoShUp
import org.example.front_end.common_elements.icons.info
import org.example.front_end.common_elements.icons.menu
import org.example.front_end.common_elements.icons.settings
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuBar() {
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
        ){
            /**
             * Shanoir Logo
             */
            Image(
                modifier = Modifier
                    .size(42.dp)
                    .clip(
                        shape = RoundedCornerShape(20.dp)
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
        ) {
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
                    }
                )
            }
        }
    }
}

@Composable
fun DropDownMenuShUp(isOpened: Boolean = true, onDismiss: () -> Unit) {
    var isConfigurationMenuOpened by remember { mutableStateOf(false) }

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
                    Icon(
                        imageVector = settings,
                        contentDescription = "",
                        tint = Color(0x67,0x50,0xA4),
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Configuration",
                        fontSize = 15.sp,
                        color = Color(0x67,0x50,0xA4)
                    )
                }

            }
        )

        // About ShUp
        DropdownMenuItem(
            onClick = {},
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = info,
                        contentDescription = "",
                        tint = Color(0x67,0x50,0xA4),
                    )

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

    if (isConfigurationMenuOpened) {
        ConfigurationDialogWindow({isConfigurationMenuOpened = false})
    }
}

@Composable
fun ConfigurationDialogWindow(onDismiss: () -> Unit) {
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
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {

            /**
             * Distant PACS
             */
            Column(
                modifier = Modifier
                    .width(600.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
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
                        value = "",
                        onValueChange = {}
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
                        value = "",
                        onValueChange = {}
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
                        value = "",
                        onValueChange = {}
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
            ) {
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
                        value = "",
                        onValueChange = {}
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
                        value = "",
                        onValueChange = {}
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
                        value = "",
                        onValueChange = {}
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {}
                ){
                    Text("Vérifier la connexion au PACS distant")
                }

                Button(
                    onClick = {}
                ) {
                    Text("Configurer le serveur")
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