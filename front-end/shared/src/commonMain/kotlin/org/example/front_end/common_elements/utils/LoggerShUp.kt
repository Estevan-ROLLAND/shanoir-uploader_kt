package org.example.front_end.common_elements.utils

import java.io.File
import kotlin.math.log

class LoggerShUP (
    private val logFile : File
) {
    init {
        // Create the log file if it doesn't exist
        if (!logFile.exists()) {
            logFile.parentFile.mkdirs() // Create parent directories if they don't exist
            logFile.createNewFile()
        }

        println("Logger initialized. Log file path: ${logFile.absolutePath}")
        // Clean the log file at the start of the application
        logFile.writeText("") // Clear the log file
    }

    fun writeLog(message: String) {
        logFile.appendText("$message\n")
    }

    fun getLogContent(): String {
        return logFile.readText()
    }
}