package com.agustinf1233.httprequest

import android.os.AsyncTask
import android.os.StrictMode
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DescargaURL(var completadoListener: CompletadoListener?): AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String? {
        return try {
            params[0]?.let { descargarDatos(it) }
        } catch (ex: IOException) {
            null
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
            if (result != null) {
                completadoListener?.descargaCompleta(result)
            }
        } catch (ex: IOException) {

        }
    }

    @Throws(IOException::class)
    private fun descargarDatos(url:String):String {
        var inputStream: InputStream? = null // Contener un objecto con flujo de datos.
        try {
            val url = URL(url)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()
            inputStream = conn.inputStream
            return inputStream.bufferedReader().use {
                it.readText() // Regreso flujo de datos como string.
            }
        } finally {
            inputStream?.close()
        }
    }
}