package com.example.passwordmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.passwordmanager.ui.theme.PasswordManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PasswordManager("ejemplo.txt")
                }
            }
        }
    }
}

@Composable
fun PasswordManager(fileName: String){
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val outS = ManageUserPass.addUser(context, "prueba", "prueba", fileName)
                Log.i("DAM2", outS)
            }
        ) {
            Text("Probar Guardado")
        }
    }
}




//@Composable
//fun NextButtonExample(onClick: () -> Unit) {
//    NextButton(
//        onClick = { onClick() }
//    ) {
//        Icon(
//            Icons.Filled.PlayArrow,
//            "next password",
//            modifier = modifier.padding(12.dp),
//            "blue"
//
//        )
//        Text("Next Password")
//
//    }
//}
//
//
//@Composable
//fun PrevButtonExample(onClick: () -> Unit) {
//    PrevButton(
//        onClick = { onClick() }
//    ) {
//        Icon(Icons.AutoMirrored.Filled.ArrowBack)
//        Text("Previous Password")
//    }
//}
//








