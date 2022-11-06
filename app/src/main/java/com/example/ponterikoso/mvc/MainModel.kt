package com.example.ponterikoso.mvc

import kotlinx.coroutines.delay
import java.net.HttpURLConnection
import java.net.URL

class MainModel {

    suspend fun getRicknMorty(i: Int): String {
        delay(3000)
        try {
            val url = URL("https://rickandmortyapi.com/api/character/" + i)
            val httpURLConnection = url.openConnection() as HttpURLConnection

            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true

            httpURLConnection.apply {
                requestMethod = "GET"
                doInput = true
            }

            val statusCode = httpURLConnection.responseCode

            if (statusCode == HttpURLConnection.HTTP_OK) {
                val buffer = httpURLConnection.inputStream.bufferedReader()
                val response = buffer.readText()
                return response
            } else {
                return "Error $statusCode"
            }
        } catch (e: Exception) {
            return "Error en la conexion $e"
        }
    }
}