package org.example.front_end.common_elements.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.front_end.common_elements.icons.arrow_drop_down
import org.example.front_end.common_elements.icons.arrow_drop_up

@Composable
fun DropDownTextField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    width: Int,
) {
    // Implementation of the dropdown text field using Jetpack Compose
    // This is a placeholder for the actual implementation
    var showMenu by remember { mutableStateOf(false) }

    Column {
        TextField(
            modifier=Modifier
                .fillMaxWidth(),
            value = selectedOption,
            onValueChange = onOptionSelected,
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (showMenu) {
                        arrow_drop_up
                    } else {
                        arrow_drop_down
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(onClick = {
                            showMenu = !showMenu
                            }
                        )
                )
            }
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = {showMenu = false},
            modifier = Modifier
                .width( //has to be the same width as the TextField
                    // Get the width of the TextField using LocalDensity
                    with(LocalDensity.current) { width.dp.toPx() }.dp
                )
        ) {
            options.forEach { optionText ->
                DropdownMenuItem(
                    text = {Text(optionText)},
                    onClick = {
                        onOptionSelected(optionText)
                        showMenu = false
                    }
                )
            }
        }
    }
}