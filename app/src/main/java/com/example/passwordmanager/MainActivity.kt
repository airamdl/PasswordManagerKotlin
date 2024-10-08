package com.example.passwordmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues -> Text(
                    text = "Nombre: ",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
                }
            }
        }
    }
}

@Composable
fun PasswordManager(){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
//        Text("Siguiente password")
//        NextButtonExample(onClick =  { Log.d("Button", "Next Button clicked") })

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








