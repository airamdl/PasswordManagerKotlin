package com.example.passwordmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.password
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager.ManageUserPass.Companion.addUser
import com.example.passwordmanager.ManageUserPass.Companion.readUsers
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                Scaffold(content = { padding -> Modifier.padding(8.dp)
                    PassUserInput()
                })
            }
        }
    }
}


val fileprueba = "users.txt"
@Composable
@Preview
fun PassUserInput() {
    val myContext = LocalContext.current
    val users = readUsers(myContext, fileprueba)
    var title by rememberSaveable { mutableStateOf("") }
    var user by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }



    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Introduce a title") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.padding(10.dp))


        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Introduce your User") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Introce your password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Remember to check the password and edit if you need",
            fontSize = 15.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.padding(5.dp))

        (Button
            (
            onClick = {ManageUserPass.addUser(myContext, title, user, pass, fileprueba )}
        )
        {
            Text(text = "Save Pass")
        })

        Spacer(modifier = Modifier.padding(5.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(8.dp)


        ) {
            items(users) { user ->
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .background(Color.hsv(0.0F, 0.0F, 0.93F, 0.93F))
                        .padding(5.dp)


                ) {

                    Text(text = "Título: ${user.tittle}")
                    Text(text = "Nombre: ${user.user}")
                    Text(text = "Contraseña: ${user.pass}")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(onClick =
                        {ManageUserPass.readUsers(myContext, fileprueba )}
                        ) {
                            Text(text = "Editar")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick =
                        {ManageUserPass.readUsers(myContext, fileprueba )}
                        ) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }



        }





    }
}









