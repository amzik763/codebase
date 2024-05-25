package com.amzi.codebase.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.amzi.codebase.retrofit.response.loginResponse
import com.amzi.codebase.viewmodels.myViewModel
import com.tillagewireless.ss.data.network.Resource


@Composable
fun LoginScreen(
    viewmodel:myViewModel
) {
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
    }

    when (loginResponse) {
        is Resource.NoAction -> {
            Text("Please enter your credentials and press login.")
        }
        is Resource.Loading -> {
            CircularProgressIndicator()
        }
        is Resource.Success -> {
            Text("Login Successful: ${(loginResponse as Resource.Success<loginResponse>).value}")
        }
        is Resource.Failure -> {
            Text("Login Failed: ${(loginResponse as Resource.Failure).errorBody}")
        }
    }
}
