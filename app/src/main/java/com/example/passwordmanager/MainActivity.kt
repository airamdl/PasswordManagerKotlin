package com.example.passwordmanager

import android.os.Bundle
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ManageUserPass.Companion.addUser
import com.example.passwordmanager.ManageUserPass.Companion.readUsers
import com.example.passwordmanager.ManageUserPass.Companion.updateUser
import com.example.passwordmanager.ManageUserPass.Companion.deleteUser
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                Scaffold(content = { padding ->
                    UserListScreen(modifier = Modifier.padding(padding))
                })
            }
        }
    }
}

const val fileName = "users.txt"

@Composable
fun UserInputForm(
    editingUser: UserData? = null,
    onSave: (UserData) -> Unit = {},
    onClearEditing: () -> Unit = {}
) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(editingUser) {
        title = editingUser?.tittle ?: ""
        username = editingUser?.user ?: ""
        password = editingUser?.pass ?: ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Introduce el título") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Introduce el usuario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Introduce la contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            if (editingUser != null) {
                val updatedUser = UserData(editingUser.id, title, username, password)
                updateUser(context, updatedUser, fileName)
                onClearEditing()
                onSave(updatedUser)
            } else {
                val newUser = UserData(UUID.randomUUID().toString(), title, username, password)
                addUser(context, newUser, fileName)
                onSave(newUser)
            }
        }) {
            Text(text = if (editingUser != null) "Actualizar Usuario" else "Guardar Usuario")
        }
    }
}

@Composable
fun UserListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var users by remember { mutableStateOf(readUsers(context, fileName)) }
    var editingUser by remember { mutableStateOf<UserData?>(null) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        UserInputForm(
            editingUser = editingUser,
            onSave = {
                users = readUsers(context, fileName)
                editingUser = null
            },
            onClearEditing = { editingUser = null }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            items(users) { user ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .background(Color.hsv(0.0F, 0.0F, 0.93F, 0.93F))
                        .padding(5.dp)
                ) {
                    Text(text = "Título: ${user.tittle}")
                    Text(text = "Usuario: ${user.user}")
                    Text(text = "Contraseña: ${user.pass}")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            editingUser = user
                        }) {
                            Text(text = "Editar")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick = {
                            deleteUser(context, user, fileName)
                            users = readUsers(context, fileName)
                        }) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}







