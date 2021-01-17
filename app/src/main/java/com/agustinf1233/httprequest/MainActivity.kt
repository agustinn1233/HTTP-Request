package com.agustinf1233.httprequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), CompletadoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCheckRed = findViewById<Button>(R.id.btnCheckRed)
        val btnSolicitud = findViewById<Button>(R.id.btnRequest)
        val btnVolley = findViewById<Button>(R.id.btnRequestVolley)
        val btnOkHTTP = findViewById<Button>(R.id.btnOkHTTP)

        val urlSend = "https://www.google.com/"

        btnCheckRed.setOnClickListener {
            if(Network.checkRed(this)) {
                Toast.makeText(applicationContext, "Hay red", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        btnSolicitud.setOnClickListener {
            if(Network.checkRed(this)) {
                //Log.d("LOGDATA",descargarDatos("https://www.google.com/"))
                DescargaURL(this).execute(urlSend)
            } else {
                Toast.makeText(applicationContext, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        btnVolley.setOnClickListener {
            if(Network.checkRed(this)) {
                VolleyTask(urlSend)
            } else {
                Toast.makeText(applicationContext, "No hay red", Toast.LENGTH_LONG).show()
            }
        }

        btnOkHTTP.setOnClickListener {
            if(Network.checkRed(this)) {
                OkHTTP(urlSend)
            } else {
                Toast.makeText(applicationContext, "No hay red", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun descargaCompleta(resultado: String) {
        Log.d("DescargaCompleta", resultado)
    }

    // Metodo usando Volley
    private fun VolleyTask(url:String) {
            val q = Volley.newRequestQueue(this)
            val request = StringRequest(Request.Method.GET, url, Response.Listener {
                response ->
                try {
                    Log.d("solicitudVolley", response)
                } catch (ex: IOException) {

                }
            }, Response.ErrorListener {  })
        q.add(request)
    }

    // Metodo usando OkHTTP
    // Ejecuta en Thread principal de la app
    private fun OkHTTP(url:String) {
        val client = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        client.newCall(solicitud).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                // implement error case
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val result = response.body()?.string()
                // Mapeo en thread principal
                this@MainActivity.runOnUiThread {
                    try {
                        // Se puede obtener json y parsearla.
                        if (result != null) {
                            Log.d("okHTTP", result)
                        }
                    } catch (ex: Exception) {

                    }
                }
            }
        })
    }
}