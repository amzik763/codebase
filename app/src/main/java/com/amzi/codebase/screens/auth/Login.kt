package com.amzi.codebase.screens.auth

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.amzi.codebase.retrofit.response.loginResponse
import com.amzi.codebase.utility.LogLevel
import com.amzi.codebase.utility.handleApiError
import com.amzi.codebase.utility.writeLog
import com.amzi.codebase.viewmodels.myViewModel
import com.tillagewireless.ss.data.network.Resource
import java.io.File


@Composable
fun LoginScreen(
    viewmodel:myViewModel
) {

    val context = LocalContext.current

    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResponse by viewmodel.loginResponse.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewmodel.login(phoneNumber, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("OPEN")
        }
    }

    when (loginResponse) {
        is Resource.NoAction -> {
            Text("Please enter your credentials and press login.")
            (context as? Activity)?.writeLog(context, LogLevel.INFO, "LoginScreen", "Login attempt with phone number: $phoneNumber")
        }
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = Modifier.width(100.dp).height(100.dp))
            (context as? Activity)?.writeLog(context, LogLevel.INFO, "LoginScreen", "Login attempt Loading with phone number: $phoneNumber")

        }
        is Resource.Success -> {
            Text("Login Successful: ${(loginResponse as Resource.Success<loginResponse>).value}")
            (context as? Activity)?.writeLog(context, LogLevel.INFO, "LoginScreen", "Response: ${(loginResponse as Resource.Success<loginResponse>).value}")

        }
        is Resource.Failure -> {
            (context as? Activity)?.handleApiError(loginResponse as Resource.Failure)
            Text("Login Failed: ${(loginResponse as Resource.Failure).errorBody}")
            (context as? Activity)?.writeLog(context, LogLevel.INFO, "LoginScreen", "Response: FAILED")

        }
    }
}
