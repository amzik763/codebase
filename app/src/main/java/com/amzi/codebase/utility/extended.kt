package com.amzi.codebase.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.amzi.codebase.MainActivity
import com.tillagewireless.ss.data.network.Resource
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader

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

fun Any.writeLog(context: Context, level: LogLevel, tag:String, message: String, saveFile: (String) -> Unit) {
    if (level.priority >= LogConfig.enabledLogLevel.priority) {
        when (level) {
            LogLevel.DEBUG -> Log.d(tag, message)
            LogLevel.ERROR -> Log.e(tag, message)
            LogLevel.INFO -> Log.i(tag, message)
            LogLevel.WARNING -> Log.w(tag, message)
            LogLevel.VERBOSE -> Log.v(tag, message)
        }
        saveFile("TAG: $tag :: Message : $message")

    }
}






//file read and write

class FilePickerHandler(
    private val activity: ComponentActivity,
    private val onFilePicked: (String) -> Unit
) {
    private val openDocumentLauncher: ActivityResultLauncher<Array<String>>

    init {
        openDocumentLauncher = (activity as ComponentActivity).registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
                val content = readTextFromUri(it)
                onFilePicked(content)
            }
        }
    }

    fun launchFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"  // You can specify the MIME type here, if needed
            putExtra(Intent.EXTRA_TITLE, "example.txt") // Specify the file name here
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null)
        openDocumentLauncher.launch(arrayOf("text/plain"), options)
    }
    fun openSpecificFile(fileName: String = "example.txt") {
        val file = File(activity.filesDir, fileName)
        if (file.exists()) {
            val uri = FileProvider.getUriForFile(activity, "${activity.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            activity.startActivity(intent)
        } else {
            // Handle file not found
        }
    }

    public fun saveTextToFile(text: String, fileName: String = "example.txt") {
        try {
            val fileOutputStream: FileOutputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(text.toByteArray())
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readTextFromFile(fileName: String = "example.txt"): String {
        return try {
            val fileInputStream: FileInputStream = activity.openFileInput(fileName)
            val inputStreamReader = fileInputStream.bufferedReader()
            val stringBuilder = StringBuilder()
            var text: String? = ""
            while ({ text = inputStreamReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }


    private fun readTextFromUri(uri: Uri): String {
        return try {
            val inputStream = activity.contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }
            reader.close()
            stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}
