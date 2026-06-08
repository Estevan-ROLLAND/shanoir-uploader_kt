package org.example.front_end.common_elements.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryBarElement(text: String, isSelected: Boolean = false, onClick: () -> Unit = {}, fontSize: TextUnit, verticalPadding: Int){
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick
            )
            .selectedCategoryBarElement(isSelected),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier
                .padding(67.dp, verticalPadding.dp)
        )
    }
}

fun Modifier.selectedCategoryBarElement(isSelected: Boolean = false) : Modifier {
    var res : Modifier = background(Color.Transparent)
    if (isSelected) {
        res = background(Color(0xEA, 0xDD, 0xFF))
            .drawBehind {
                val bordersize = 4.dp.toPx()
                drawLine(
                    color = Color(0x67, 0x50, 0xA4),
                    start = Offset(0f, size.height - 2f),
                    end = Offset(size.width, size.height - 2f),
                    strokeWidth = bordersize
                )
            }
    }
    return res
}