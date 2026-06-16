package org.example.front_end

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.front_end.common_elements.icons.check_box
import org.example.front_end.common_elements.icons.check_box_outline_blank
import org.example.front_end.common_elements.icons.close
import org.example.front_end.viewmodel.ViewModelShUp

@Composable
fun ExportToServerWindow(viewModel: ViewModelShUp, onNavBarSwitch: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

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
                            onClick = {
                                onNavBarSwitch()
                            }
                        )
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
                            onClick = {}
                        )
                        .background(Color(0xEA,0xDD,0xFF))
                        .drawBehind{ // Draw a purple line at the bottom of the Row
                            val bordersize = 4.dp.toPx()
                            drawLine(
                                color = Color(0x67,0x50,0xA4),
                                start = Offset(0f, size.height-2f),
                                end = Offset(size.width, size.height-2f),
                                strokeWidth = bordersize
                            )
                        }
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
                    .fillMaxHeight(.726f)
                    .padding(20.dp)
                    //.verticalScroll(columnScrollState)
                    .weight(1f,false),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //var lineSelected by remember {mutableStateOf(mutableListOf())}

                /**
                 * Imported Data Table
                 */
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f,false),
                ){
                    TableScreen(
                        viewModel = viewModel,
                        onSelected = { data: List<String> ->
                            viewModel.addLine(data)
                            println("Added - Nb Lignes selectionnées : ${viewModel.selectedLines.size} ; all selected lines : ${viewModel.selectedLines}")
                        },
                        onUnselected = { data: List<String> ->
                            viewModel.removeLine(data)
                            println("Removed - Nb Lignes selectionnées : ${viewModel.selectedLines.size} ; all selected lines : ${viewModel.selectedLines}")
                        }
                    )
                }
            }
        }
    }
}

// Source - https://stackoverflow.com/a/68143597
// Posted by nglauber
// Retrieved 2026-06-05, License - CC BY-SA 4.0

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableScreen(viewModel: ViewModelShUp, onSelected: (List<String>) -> Unit, onUnselected: (List<String>) -> Unit) {
    val filters = remember { mutableStateListOf("", "", "", "", "", "") }
    val columnWeights = listOf(.12f, .23f, .16f, .16f, .14f, .13f)
    val iconColumnWeight = .06f

    // Filtered data based on the filters
    val visibleData = viewModel.testData.filter { lineData ->
        lineData.indices.all { index ->
            val query = filters.getOrNull(index).orEmpty().trim()
            query.isEmpty() || lineData[index].contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                // Header Row
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(0x89, 0x76, 0xBC))
                        .border(1.dp, Color.Black)
                ) {
                    TableCellIcon(check_box_outline_blank, "Sélectionner", iconColumnWeight, {})
                    TableHeaderCell("ID", columnWeights[0])
                    TableHeaderCell("Nom du patient", columnWeights[1])
                    TableHeaderCell("IPP", columnWeights[2])
                    TableHeaderCell("Date de l'étude", columnWeights[3])
                    TableHeaderCell("IRM", columnWeights[4])
                    TableHeaderCell("État", columnWeights[5])
                    TableCellIcon(close, "Supprimer", iconColumnWeight, {})
                }
            }

            items(visibleData, key = { it.firstOrNull().orEmpty() }) { lineData ->
                val isSelected = viewModel.selectedLines.contains(lineData)
                val iconCheckbox = if (isSelected) check_box else check_box_outline_blank
                val backgroundColor = if (isSelected) Color(0xEA, 0xDD, 0xFF) else Color.White

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                ) {
                    TableCellIcon(
                        iconCheckbox,
                        if (isSelected) "Désélectionner" else "Sélectionner",
                        iconColumnWeight,
                        onIconClick = {
                            if (isSelected) onUnselected(lineData) else onSelected(lineData)
                        }
                    )
                    TableCell(lineData.getOrNull(0).orEmpty(), columnWeights[0])
                    TableCell(lineData.getOrNull(1).orEmpty(), columnWeights[1])
                    TableCell(lineData.getOrNull(2).orEmpty(), columnWeights[2])
                    TableCell(lineData.getOrNull(3).orEmpty(), columnWeights[3])
                    TableCell(lineData.getOrNull(4).orEmpty(), columnWeights[4])
                    TableCellState(lineData.getOrNull(5).orEmpty(), columnWeights[5])
                    TableCellIcon(
                        close,
                        "Supprimer la ligne",
                        iconColumnWeight,
                        onIconClick = {
                            viewModel.deleteLine(lineData)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.TableHeaderCell(
    title: String,
    weight: Float,
) {
    Column(
        modifier = Modifier
            .weight(weight)
            .border(1.dp, Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RowScope.TableFilterCell(
    value: String,
    weight: Float,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Filtrer") },
        singleLine = true,
        modifier = Modifier
            .weight(weight)
            .border(1.dp, Color.Black)
            .padding(4.dp)
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    align: TextAlign = TextAlign.Start,
    clickable: Boolean = false,
    filter: () -> Unit = {}
)
{
    if (clickable){
        Text(
            text = text,
            modifier = Modifier
                .border(1.dp, Color.Black)
                .weight(weight)
                .clickable(
                    true,
                    onClick = filter
                )
                .padding(8.dp),
            textAlign = align
        )
    } else {
        Text(
            text = text,
            modifier = Modifier
                .border(1.dp, Color.Black)
                .weight(weight)
                .padding(8.dp),
            textAlign = align
        )
    }
}

@Composable
fun RowScope.TableCellIcon(
    icon: ImageVector,
    contentDescription: String = "",
    weight: Float,
    onIconClick: () -> Unit
)
{
       Icon(
           imageVector = icon,
           contentDescription = contentDescription,
           modifier = Modifier
               .border(1.dp, Color.Black)
               .weight(weight)
               .clickable(
                   onClick = onIconClick
               )
               .padding(vertical = 8.dp)
       )
}

@Composable
fun RowScope.TableCellState(
    state: String,
    weight: Float,
)
{
    var backgroundColor = Color.White

    when(state) {
        "FINISHED" -> backgroundColor = Color.LightGray
        "ERROR" -> backgroundColor = Color(0xCF,0x00,0x00)
        "READY" -> backgroundColor = Color(0x29,0xCF,0x00)
        "CHECK_OK" -> backgroundColor = Color(0x5B,0xA2,0xFF)
        "CHECK_KO" -> backgroundColor = Color(0xFF,0x78,0x02)
    }

    Text(
        text = state,
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .background(backgroundColor)
            .padding(8.dp),
    )
}
