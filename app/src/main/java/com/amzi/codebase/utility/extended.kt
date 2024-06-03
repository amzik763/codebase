package com.amzi.codebase.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.amzi.codebase.MainActivity
import com.tillagewireless.ss.data.network.Resource
import java.io.File
import java.io.FileWriter
import java.io.IOException

fun Activity.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
//  Log.d(MainActivity.TAG,"Response FAILED $failure")
    when {
        failure.isNetworkError -> {
        }
        failure.errorCode == 401 -> {

        }
        else -> {
            val error = failure.errorBody?.string().toString()
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


object LogConfig{

    var enabledLogLevel: LogLevel = LogLevel.DEBUG

}

enum class LogLevel(val priority: Int, val tag: String) {
    DEBUG(3, "AppDebug"),
    INFO(4, "AppInfo"),
    WARNING(5, "AppWarning"),
    ERROR(6, "AppError"),
    VERBOSE(2, "AppVerbose")
}

fun Any.writeLog(context: Context, level: LogLevel, tag:String, message: String) {
    if (level.priority >= LogConfig.enabledLogLevel.priority) {
        when (level) {
            LogLevel.DEBUG -> Log.d(tag, message)
            LogLevel.ERROR -> Log.e(tag, message)
            LogLevel.INFO -> Log.i(tag, message)
            LogLevel.WARNING -> Log.w(tag, message)
            LogLevel.VERBOSE -> Log.v(tag, message)
        }

    }
}



