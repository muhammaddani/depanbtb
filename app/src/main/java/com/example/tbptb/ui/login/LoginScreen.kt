package com.example.tbptb.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tbptb.R
import com.example.tbptb.data.retrofit.ApiConfig
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.response.LoginRequest
import com.example.tbptb.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current)))
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }  // Checkbox for "Ingatkan saya"
    val errorMessage by loginViewModel.errorMessage.collectAsState()  // Collect error message from ViewModel
    val loginSuccess by loginViewModel.loginSuccess.collectAsState() // Collect login success status
    val context = LocalContext.current

    // FocusRequester and KeyboardController
    val emailFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Column layout to align logo and input fields
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Image (Centered)
        Image(
            painter = painterResource(id = R.drawable.logotahub),  // Logo from drawable
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp) // Adjust size of the logo
                .padding(bottom = 32.dp)
        )

        // Username Field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Username", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .focusRequester(emailFocusRequester),  // Apply the focusRequester
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { /* move to next field */ }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFFFA500), // Orange color for focused state
                unfocusedIndicatorColor = Color.Gray
            )
        )

        // Password Field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* perform login */ }),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFFFA500), // Orange color for focused state
                unfocusedIndicatorColor = Color.Gray
            )
        )

        // "Ingatkan saya" Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFFA500))
            )
            Text("Ingatkan saya", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show error message if login fails
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Login Button
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val loginRequest = LoginRequest(email, password)
                    loginViewModel.login(loginRequest)
                } else {
                    Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(50.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500)  // Orange color for the button
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Check if login is successful and navigate
        LaunchedEffect(loginSuccess) {
            if (loginSuccess) {
                navController.navigate("home") // Navigate to home if login is successful
            }
        }

        // Footer Text
        Text(
            text = "© 2024 Tactical Thinkers",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )
    }

    // Focus on email field and show the keyboard when LoginScreen is first loaded
    LaunchedEffect(Unit) {
        emailFocusRequester.requestFocus() // Request focus for the email field
        keyboardController?.show() // Show the keyboard
    }
}
