package com.example.passwordmanager

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ManageUserPass {
    companion object {
        fun addUser(context: Context, tittle: String, user: String, pass: String, fileName: String) : String {
            val storageState = Environment.getExternalStorageState()

            if (storageState == Environment.MEDIA_MOUNTED) {
                val directory = context.filesDir
                val file = File(directory, fileName)

                try {
                    val outputStream = FileOutputStream(file, true)
                    val writer = OutputStreamWriter(outputStream)
                    val userData = UserData(tittle, user,pass)
                    writer.append("$userData;")
                    writer.close()

                    return "Usuario guardado en $directory $fileName"
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "Error al guardar el usuario"
                }
            } else {
                return "No se pudo acceder al almacenamiento externo"
            }
        }

        fun readUsers(context: Context, fileName: String): List<UserData> {
            val storageState = Environment.getExternalStorageState()
            val userList = mutableListOf<UserData>()

            if (storageState == Environment.MEDIA_MOUNTED) {
                val directory = context.filesDir
                val file = File(directory, fileName)

                try {
                    val inputStream = FileInputStream(file)
                    val reader = InputStreamReader(inputStream)
                    val text = reader.readText()
                    Log.i("DAM2", "Contenido completo del archivo: $text")
                    reader.close()

                    val userEntries = text.split(";").filter { it.isNotBlank() }
                    Log.i("DAM2", "Contenido dividido en entradas: ${userEntries.joinToString()}")

                    userEntries.forEach { entry ->
                        // Nueva expresión regular para hacer coincidir el formato de UserData actualizado
                        val match = Regex("""UserData\(tittle=(.*?), user=(.*?), pass=(.*?)\)""").find(entry)
                        if (match != null) {
                            val (tittle, user, pass) = match.destructured
                            Log.i("DAM2", "Datos extraídos - Usuario: $user, Contraseña: $pass")
                            userList.add(UserData(tittle, user, pass))
                        } else {
                            Log.i("DAM2", "No coincide el regex con la entrada: $entry")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("DAM2", "Error al leer el archivo: ${e.message}")
                }
            }
            Log.i("DAM2", "Lista final de usuarios: ${userList.joinToString()}")
            return userList
        }
    }
}

data class UserData(val tittle: String, val user: String, val pass: String)