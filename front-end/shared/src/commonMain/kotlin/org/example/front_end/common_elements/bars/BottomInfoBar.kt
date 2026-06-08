package org.example.front_end.common_elements.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.front_end.Windows
import org.example.front_end.common_elements.icons.arrow_forward
import org.example.front_end.common_elements.icons.close
import org.example.front_end.common_elements.icons.upload

@Composable
fun BottomInfoBar(currentScreen: Windows, currentCategory: String, onCategoryChange: () -> Unit) {
    //var activeBottomBarCategory by remember { mutableStateOf("Infos") }
    when(currentScreen) {
        Windows.IMPORT -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ){
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
                    CategoryBarElement("Infos", (currentCategory == "Infos"), onCategoryChange, 15.sp, 10)
                    CategoryBarElement("Logs",(currentCategory == "Logs"),onCategoryChange, 15.sp, 10)
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
                        when(currentCategory) {
                            "Infos" -> {
                                Text(
                                    text = "Copies ou téléchargement en cours :",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp),
                                )

                                // TODO() les bar de téléchargements
                            }

                            "Logs" -> {
                                /**
                                 * Show the logs of the import of data
                                 */
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
                    ) {

                    }

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 40.dp ),
                        onClick = { },
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
            }
        }

        Windows.EXPORT -> {
            /**
             * Export Infos Panel
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ){
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
                    CategoryBarElement("Infos", isSelected = (currentCategory == "Infos"), onCategoryChange, 15.sp, 10)
                    CategoryBarElement("Logs",(currentCategory == "Logs"),onCategoryChange, 15.sp, 10)
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
                        when(currentCategory) {
                            "Infos" -> {
                                Text(
                                    text = "Informations générales",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp),
                                    )

                            // TODO() les bar de téléchargements
                            }
                            "Logs" -> {
                                /**
                                 * Show the logs of the import of data
                                 */
                            }
                        }
                    }

                    /**
                     * Show the current station of the connection with the DICOM server
                     */
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .fillMaxHeight()
                            .padding(vertical = 15.dp)
                            .border(1.dp, Color.LightGray)
                    ) {

                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        /**
                         * Import Selected Lines Button
                         */
                        Button(
                            modifier = Modifier
                                .padding(40.dp,0.dp),
                            onClick = {},
                            colors = ButtonColors(
                                Color(0x29,0xCF,0x00), Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.LightGray,
                                ),
                            enabled = true // false when no lines are selected
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
                            onClick = {},
                            colors = ButtonColors(
                                Color(0xCF, 0x00, 0x00), Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.LightGray,
                                ),
                            enabled = true // false when no lines are selected
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
                    }
                }
            }
        }

        else -> {}
    }
}