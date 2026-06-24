package org.example.front_end

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.window.core.layout.WindowSizeClass
import front_end.shared.generated.resources.Res
import org.example.front_end.common_elements.bars.BottomInfoBar
import org.example.front_end.common_elements.bars.MenuBar
import org.example.front_end.common_elements.utils.LoggerShUP
import org.example.front_end.common_elements.utils.LoginHandler
import org.example.front_end.viewmodel.ViewModelShUp
import java.io.File
import kotlin.math.log

@Composable
fun WindowsHandler() {
    var currentScreen by remember { mutableStateOf(Windows.LOGIN)}
    val viewModel = ViewModelShUp.getInstance()

    Column {
        if (currentScreen == Windows.LOGIN) {
            LoginWindow(
                viewModel = viewModel,
                onLoginSuccess = {
                    currentScreen = Windows.IMPORT
                }
            )
        } else {
            MenuBar(viewModel)
            when (currentScreen) {
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

                else -> {}
            }

            BottomInfoBar(
                currentScreen = currentScreen,
                viewModel = viewModel,
                logger = viewModel.logger,
                onScreenChange = {
                    currentScreen = Windows.EXPORT
                }
            )
        }
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