package com.example.app10_appclima

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app10_appclima.ui.theme.App10_AppClimaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : ComponentActivity() {
    private lateinit var editTextUbicacion: EditText
    private lateinit var buttonBuscar: Button
    private lateinit var textViewClima: TextView
    private val apiKey = "YOUR_API_KEY" // Reemplaza con tu clave API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUbicacion = findViewById(R.id.editTextUbicacion)
        buttonBuscar = findViewById(R.id.buttonBuscar)
        textViewClima = findViewById(R.id.textViewClima)

        buttonBuscar.setOnClickListener {
            val ubicacion = editTextUbicacion.text.toString()
            obtenerClima(ubicacion)
        }
    }

    private fun obtenerClima(ubicacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$ubicacion&appid=$apiKey&units=metric")
                val respuesta = url.readText()
                val json = JSONObject(respuesta)
                val temperatura = json.getJSONObject("main").getDouble("temp")
                val descripcion =
                    json.getJSONArray("weather").getJSONObject(0).getString("description")

                withContext(Dispatchers.Main) {
                    textViewClima.text = "Temperatura: $temperatura°C\nDescripción: $descripcion"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    textViewClima.text = "Error al obtener el clima"
                }
                e.printStackTrace() // Correcto
            }
        }
    }
}