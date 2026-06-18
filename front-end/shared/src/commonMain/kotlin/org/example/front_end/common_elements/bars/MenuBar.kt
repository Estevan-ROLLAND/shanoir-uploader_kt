package org.example.front_end.common_elements.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
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
import front_end.shared.generated.resources.Res
import front_end.shared.generated.resources.logoShUp
import kotlinx.coroutines.delay
import org.example.front_end.dialog_windows.ConfigurationDialogWindow
import org.example.front_end.common_elements.icons.check
import org.example.front_end.common_elements.icons.info
import org.example.front_end.common_elements.icons.menu
import org.example.front_end.common_elements.icons.settings
import org.example.front_end.dialog_windows.AboutShUpDialogWindow
import org.example.front_end.viewmodel.ViewModelShUp
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.milliseconds

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
    var lastLang = "fr"

    var isAboutMenuOpened by remember { mutableStateOf(false) }


    LaunchedEffect(viewModel.logger){
        while (true) {
            if (lang != lastLang) {
                lastLang = lang
                viewModel.logger.writeLog("Language changed to $lang")
            }
            delay(500.milliseconds)
        }
    }

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
            onClick = {
                isAboutMenuOpened = true
                onDismiss()
            },
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

    if (isAboutMenuOpened) {
        AboutShUpDialogWindow(onClose = { isAboutMenuOpened = false })
    }
}


