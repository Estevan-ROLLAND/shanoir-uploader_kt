package org.example.front_end

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.visibility
import com.example.test.visibility_off
import org.example.front_end.common_elements.utils.DropDownTextField
import org.example.front_end.common_elements.utils.LoginHandler
import org.example.front_end.viewmodel.ViewModelShUp


@Composable
fun LoginWindow(modifier: Modifier = Modifier, viewModel: ViewModelShUp, onLoginSuccess: () -> Unit = {}) {
    var accountType by remember { mutableStateOf(viewModel.loginHandler.accountTypes[0]) }
    var id by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(.5f)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            )
            {
                Text(
                    text = "Choissisez le profil : "
                )

                DropDownTextField(
                    options = viewModel.loginHandler.accountTypes,
                    selectedOption = accountType,
                    onOptionSelected = { accountType = it },
                    width = 766
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(58.dp)
            ) {

                Text(
                    text = "Identifiant :"
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    value = id,
                    onValueChange = { id = it },
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(52.dp)
            ) {
                Text(
                    text = "Mot de passe :"
                )

                val state = rememberTextFieldState()
                SecureTextField(
                    state = state,
                    trailingIcon = {
                        Icon(
                            imageVector = if (showPassword) {
                                visibility_off
                            } else {
                                visibility
                            },
                            contentDescription = "",
                            modifier = Modifier
                                .clickable(onClick = {
                                    showPassword = !showPassword
                                    }
                                )
                        )
                    },
                    textObfuscationMode =
                        if (showPassword) {
                            TextObfuscationMode.Visible
                        } else {
                            TextObfuscationMode.RevealLastTyped
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Button(
                modifier = modifier,
                onClick = {
                    viewModel.loginHandler.setLoginInfo(accountType = accountType, id)
                    viewModel.logger.writeLog("Connected as ${viewModel.loginHandler.getAccountType()} with id $id")
                    onLoginSuccess()
                },
            ) {
                Text("Connexion")
            }
        }
    }


}
