package org.example.front_end

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.front_end.common_elements.bars.BottomInfoBar
import org.example.front_end.common_elements.bars.MenuBar

@Composable
fun WindowsHandler() {
    var currentScreen by remember { mutableStateOf(Windows.LOGIN)}
    val viewModel = ViewModelShUp()

    Column {
        MenuBar()
        when (currentScreen) {
            Windows.LOGIN -> LoginWindow(
                onLoginSuccess = {
                    currentScreen = Windows.IMPORT
                }
            )

            Windows.IMPORT -> LocalDataImportWindow(
                onNavBarSwitch = {
                    currentScreen = Windows.EXPORT
                }
            )

            Windows.EXPORT -> ExportToServerWindow(
                viewModel = viewModel,
                onNavBarSwitch = {
                    currentScreen = Windows.IMPORT
                }
            )
        }

        BottomInfoBar(
            currentScreen = currentScreen,
            viewModel = viewModel,
            onScreenChange = {
                currentScreen = Windows.EXPORT
            })
    }


}

/**
 * Contains all the differents screens of Shanoir Uploader (do not include pop-up)
 */
enum class Windows {
    LOGIN,
    IMPORT,
    EXPORT,
}