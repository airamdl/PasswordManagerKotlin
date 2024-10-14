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
        fun addUser(context: Context, userData: UserData, fileName: String) {
            val directory = context.filesDir
            val file = File(directory, fileName)

            try {
                val outputStream = FileOutputStream(file, true)
                val writer = OutputStreamWriter(outputStream)
                writer.append("$userData\n")
                writer.close()
                Log.i("DAM2", "Usuario guardado en $directory/$fileName")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DAM2", "Error al guardar el usuario: ${e.message}")
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

                    val userEntries = text.lines().filter { it.isNotBlank() }
                    Log.i("DAM2", "Contenido dividido en entradas: ${userEntries.joinToString()}")

                    userEntries.forEach { entry ->
                        val match = Regex("""UserData\(id=(.*?), tittle=(.*?), user=(.*?), pass=(.*?)\)""").find(entry)
                        if (match != null) {
                            val (id, tittle, user, pass) = match.destructured
                            Log.i("DAM2", "Datos extraídos - ID: $id, Título: $tittle, Usuario: $user, Contraseña: $pass")
                            userList.add(UserData(id, tittle, user, pass))
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

        private fun writeUsersToFile(context: Context, users: List<UserData>, fileName: String) {
            val directory = context.filesDir
            val file = File(directory, fileName)

            try {
                val outputStream = FileOutputStream(file, false)
                val writer = OutputStreamWriter(outputStream)
                users.forEach { user -> writer.append("$user\n") }
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DAM2", "Error al escribir el archivo: ${e.message}")
            }
        }

        fun updateUser(context: Context, updatedUser: UserData, fileName: String) {
            val users = readUsers(context, fileName).map { existingUser ->
                if (existingUser.id == updatedUser.id) updatedUser else existingUser
            }
            writeUsersToFile(context, users, fileName)
        }

        fun deleteUser(context: Context, userToDelete: UserData, fileName: String) {
            val users = readUsers(context, fileName).filter { it.id != userToDelete.id }
            writeUsersToFile(context, users, fileName)
        }
    }
}

data class UserData(val id: String, val tittle: String, val user: String, val pass: String)

