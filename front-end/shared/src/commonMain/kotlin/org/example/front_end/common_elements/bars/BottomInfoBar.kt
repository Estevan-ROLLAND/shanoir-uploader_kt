package org.example.front_end.common_elements.bars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.front_end.dialog_windows.ExportFormWindow
import org.example.front_end.viewmodel.ViewModelShUp
import org.example.front_end.Windows
import org.example.front_end.common_elements.icons.arrow_forward
import org.example.front_end.common_elements.icons.close
import org.example.front_end.common_elements.icons.upload
import org.example.front_end.common_elements.utils.LoggerShUP
import java.io.File
import java.io.InputStream
import kotlin.collections.emptyList
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun BottomInfoBar(currentScreen: Windows, viewModel: ViewModelShUp, logger: LoggerShUP, onScreenChange: () -> Unit) {
    when(currentScreen) {
        Windows.LOGIN -> {}

        else -> {
            var activeBottomBarCategory by remember { mutableStateOf("Infos") }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            )
            {
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
                ) {
                    CategoryBarElement("Infos", (activeBottomBarCategory == "Infos"), { activeBottomBarCategory = "Infos" }, 15.sp, 10)
                    CategoryBarElement("Logs",(activeBottomBarCategory == "Logs"), { activeBottomBarCategory = "Logs"  }, 15.sp, 10)
                }

                // Dowload Infos
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .width(1200.dp)
                            .fillMaxHeight()
                            .border(1.dp, Color.LightGray)
                    ) {
                        when(activeBottomBarCategory) {
                            "Infos" -> {
                                var title = ""
                                if (currentScreen == Windows.IMPORT) {
                                    title = "Copies ou téléchargement en cours :"
                                } else if (currentScreen == Windows.EXPORT){
                                    title = "Informations générales"
                                }
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp),
                                )

                                when(currentScreen){
                                    Windows.IMPORT -> {
                                        // TODO() les bar de téléchargements
                                        LinearDeterminateIndicator()
                                    }

                                    Windows.EXPORT -> {
                                        ExportInfoPanel(viewModel)
                                    }

                                    else -> {}
                                }
                            }

                            "Logs" -> {
                                /**
                                 * Show the logs
                                 */
                                Logs(logger)
                            }
                        }
                    }

                    /**
                     * Show the current situation of the connection with the DICOM server
                     */
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .fillMaxHeight()
                            .padding(vertical = 15.dp)
                            .border(1.dp, Color.LightGray)
                    )
                    {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            var statusPACS by remember { mutableStateOf("unstable") }
                            var statusColor by remember { mutableStateOf(Color.Green) }
                            var statusText by remember { mutableStateOf("") }

                            Text("Connexion avec le PACS distant :")
                            Row(
                                modifier = Modifier.padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                when(statusPACS) {
                                    "stable" -> {
                                        statusColor = Color(0x29,0xCF,0x00)
                                        statusText = "Connexion stable"
                                    }

                                    "unstable" -> {
                                        statusColor = Color(0xFF,0x78,0x02)
                                        statusText = "Connexion instable"
                                    }

                                    "down" -> {
                                        statusColor = Color(0xCF,0x00,0x00)
                                        statusText = "Connexion perdue"
                                    }
                                }

                                Canvas(modifier = Modifier.size(15.dp)) {
                                    drawCircle(statusColor)
                                }
                                Text(statusText)
                            }
                        }
                    }


                    /**
                     * Buttons
                     */
                    when(currentScreen) {
                        Windows.IMPORT -> {
                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 40.dp ),
                                onClick = onScreenChange,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Voir les imports", fontSize = 30.sp)
                                    Icon(
                                        imageVector = arrow_forward,
                                        "",
                                        modifier = Modifier.width(40.dp).height(40.dp)
                                    )
                                }
                            }
                        }

                        Windows.EXPORT -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                var isExportFormOpened by remember { mutableStateOf(false) }

                                /**
                                 * Import Selected Lines Button
                                 */
                                Button(
                                    modifier = Modifier
                                        .padding(40.dp,0.dp),
                                    onClick = {
                                        isExportFormOpened = !isExportFormOpened
                                    },
                                    colors = ButtonColors(
                                        containerColor = Color(0x29,0xCF,0x00), contentColor = Color.White,
                                        disabledContainerColor = Color.Gray,
                                        disabledContentColor = Color.LightGray,
                                    ),
                                    enabled = viewModel.enableImportBtn // false when no lines are selected
                                ){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = upload, "", modifier = Modifier.width(40.dp).height(40.dp))
                                        Text("Exporter les lignes sélectionnées", fontSize = 20.sp)
                                    }
                                }

                                /**
                                 * Delete Selected Liges Button
                                 */
                                Button(
                                    modifier = Modifier
                                        .padding(40.dp,0.dp),
                                    onClick = { viewModel.deleteSelectedLines() },
                                    colors = ButtonColors(
                                        Color(0xCF, 0x00, 0x00), Color.White,
                                        disabledContainerColor = Color.Gray,
                                        disabledContentColor = Color.LightGray,
                                    ),
                                    enabled = viewModel.selectedLines.isNotEmpty()
                                ){
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = close, "", modifier = Modifier.width(40.dp).height(40.dp))
                                        Text("Supprimer les lignes sélectionnées", fontSize = 20.sp)
                                    }
                                }

                                if (isExportFormOpened){
                                    ExportFormWindow(
                                        onClose = { isExportFormOpened = false }
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}


@Composable
fun ExportInfoPanel(viewModel: ViewModelShUp){
    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        item {
            Text("Examens importés : ${viewModel.testData.size}")
        }
        item{
            Text("Examens en erreur : ")
        }
    }
}

@Composable
fun Logs(logger: LoggerShUP) {
    val lazyColumnState = rememberLazyListState()

    // Keep a mutable copy of the logs and update it when the underlying file changes.
    var logs by remember { mutableStateOf(logger.getLogContent()) }

    // Watch the log file periodically and update `logs` when its content changes.
    LaunchedEffect(logger) {
        // initial scroll to the bottom if there is already content
        val initialLines = logs.lines().filter { it.isNotEmpty() }
        if (initialLines.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(initialLines.lastIndex)
        }

        while (true) {
            val newContent = try {
                logger.getLogContent()
            } catch (e: Exception) {
                // if reading fails, just skip this iteration
                null
            }

            if (newContent != null && newContent != logs) {
                logs = newContent

                // after updating the state, scroll to the last line
                val lines = newContent.lines().filter { it.isNotEmpty() }
                if (lines.isNotEmpty()) {
                    // animate to the last line
                    lazyColumnState.animateScrollToItem(lines.lastIndex)
                }
            }

            // Poll every 500ms for changes. Adjust interval if needed.
            delay(500.milliseconds)
        }
    }

    // Display each log line as an item so we can scroll to the last line index
    val logLines = logs.lines()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyColumnState
    ){
        items(logLines) { line ->
            Text(text = line)
        }
    }
}


/**
 * Download Bars
 */

@Composable
fun DownloadBar(percentage: Int = 1) {
    val progressFactor by remember(percentage) {mutableStateOf(percentage * 0.01f)}
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .width(400.dp)
                .height(20.dp)
                .border(width = 4.dp,
                    color = Color(0x67,0x50,0xA4),
                    shape = RoundedCornerShape(50.dp)
                )
                .clip(RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                ))
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(progressFactor)
                    .background(color = Color(0x67,0x50,0xA4)),
                enabled = true,
                elevation = null,
                colors = buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            ){ }
        }

        Text(
            text = "${percentage} %",
            fontSize = 20.sp
        )
    }

}

@Composable
fun LinearDeterminateIndicator() {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}