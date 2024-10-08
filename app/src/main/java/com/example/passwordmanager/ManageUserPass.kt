package com.example.passwordmanager

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class ManageUserPass {
    companion object {
        fun addUser(context: Context, user: String, pass: String, fileName: String) : String {
            val storageState = Environment.getExternalStorageState()

            if (storageState == Environment.MEDIA_MOUNTED) {
                val directory = context.filesDir
                val file = File(directory, fileName)

                try {
                    val outputStream = FileOutputStream(file, true)
                    val writer = OutputStreamWriter(outputStream)
                    val userData = UserData(0,user,pass)
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
    }
}

data class UserData(val id: Int, val user: String, val pass: String)