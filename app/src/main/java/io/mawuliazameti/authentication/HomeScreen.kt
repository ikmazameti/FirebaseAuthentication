package  io.mawuliazameti.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
 import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun HomeScreen(navController: NavController) {
    val auth = Firebase.auth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //TODO: access user data

        Text("Welcome, ${auth.currentUser?.email}!")
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            //TODO:  user logout
            auth.signOut()
             navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
        ) {
            Text("Logout")
        }
    }
}

