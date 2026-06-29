package org.example.front_end.common_elements.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class LoginHandler () {
    val accountTypes = listOf("OFSEP", "Neurinfo", "Guest")
    private var currentAccountType: String = ""
    private var currentUsername: String = ""


    fun setLoginInfo(accountType: String, username: String) {
        currentAccountType = accountType
        currentUsername = username
    }

    fun getAccountType() : String {
        return currentAccountType
    }

    fun getUsername() : String {
        return currentUsername
    }
}