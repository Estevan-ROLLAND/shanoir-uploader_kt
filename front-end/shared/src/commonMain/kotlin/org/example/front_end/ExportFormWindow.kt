package org.example.front_end

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.lifecycle.ViewModel
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.icons.arrow_drop_up
import org.example.front_end.common_elements.icons.keyboard_arrow_down
import org.example.front_end.common_elements.icons.keyboard_arrow_right

@Composable
fun ExportFormWindow(onClose: () -> Unit) {
    val state = WindowState(
        width = 800.dp,
        height = 1000.dp
    )

    Window(
        state = state,
        onCloseRequest = onClose,
        alwaysOnTop = true
    ) {
        var isInfoDICOMVisible by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                )
                .background(Color.White),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    var icon by remember { mutableStateOf(keyboard_arrow_down) }

                    if(isInfoDICOMVisible) {
                        icon = keyboard_arrow_down
                    } else {
                        icon = keyboard_arrow_right
                    }

                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = "Informations DICOM",
                        fontSize = 25.sp
                    )

                    Icon(
                        modifier = Modifier
                            .size(42.dp),
                        imageVector = icon,
                        contentDescription = ""
                    )
                }

                AnimatedVisibility(
                    visible = isInfoDICOMVisible,
                ){
                    Column {

                    }
                }
            }
        }
    }
}