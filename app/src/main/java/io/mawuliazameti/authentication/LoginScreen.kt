package io.mawuliazameti.authentication

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
 import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavController,  ) {
    val rainbowColors = listOf(
        Color(0xFFFF0000), Color(0xFFFF7F00),
        Color(0xFFFFFF00),
        Color(0xFF0000FF),
        Color(0xFF4B0082), Color(0xFF8B00FF)
    )
    val context = LocalContext.current
    val brush = remember { Brush.linearGradient(colors = rainbowColors) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isFormValid = email.isNotBlank() && password.isNotBlank()
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val auth = Firebase.auth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.auth),
            contentScale = ContentScale.Crop,
            contentDescription = "Welcome back!",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                brush = brush
            )
        )

        Spacer(Modifier.height(24.dp))

        // Email Field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = null,
                    tint = Color(0xFF6200EE)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                cursorColor = Color(0xFF6200EE),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Password Field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null, tint = Color(0xFF6200EE))
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    Icon(
                        imageVector = if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordHidden) "Show" else "Hide",
                        tint = Color(0xFF6200EE)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                cursorColor = Color(0xFF6200EE),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                //TODO:  user login
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("home")
                        } else {
                            Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            },
            enabled = isFormValid && isEmailValid,
            colors  = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),
                contentColor = Color(0xFFFFFFFFF),

                ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        TextButton(onClick = {
            navController.navigate("signup")
            email = ""
            password = ""
        }) {
            Text(
                text = "Don't have an account? Sign up",
                color = Color(0xFF6200EE)
            )
        }
    }
}

