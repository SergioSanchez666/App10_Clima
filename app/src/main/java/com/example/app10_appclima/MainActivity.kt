package com.example.app10_appclima
//Miembros de equipo:
//Coronel Meza Sergio Daniel
//Sanchez Cruz Sergio Antonio

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
//Importa las clases y funciones necesarias de las bibliotecas de Android y Kotlin.
// Estas importaciones permiten utilizar las clases Bundle, Button, EditText, TextView, etc.,
// sin tener que escribir sus nombres completos. Además, se importan las corrutinas y las clases para el
// manejo de JSON y URLs.

class MainActivity : ComponentActivity() {
    private lateinit var editTextUbicacion: EditText
    private lateinit var buttonBuscar: Button
    private lateinit var textViewClima: TextView
    private val apiKey = "YOUR_API_KEY"
    //private lateinit var: Declara variables privadas para los elementos de la interfaz de usuario:
    // EditText para la ubicación, Button para buscar y TextView para mostrar el clima.
    // lateinit indica que estas variables se inicializarán más tarde.

    //private val apiKey = "YOUR_API_KEY": Declara una constante privada para la clave de la API de
    // OpenWeatherMap. Debes reemplazar "YOUR_API_KEY" con tu propia clave API.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //override fun onCreate(savedInstanceState: Bundle?) {: Sobrescribe el método onCreate, que se llama cuando se crea la actividad.
        // super.onCreate(savedInstanceState): Llama al método onCreate de la clase padre.
        //setContentView(R.layout.activity_main): Establece el diseño de la interfaz de usuario para la actividad, definido en el archivo activity_main.xml.

        editTextUbicacion = findViewById(R.id.editTextUbicacion)
        buttonBuscar = findViewById(R.id.buttonBuscar)
        textViewClima = findViewById(R.id.textViewClima)
        //findViewById(R.id.): Encuentra los elementos de la interfaz de usuario por sus IDs y los asigna a las variables declaradas anteriormente.

        buttonBuscar.setOnClickListener {
            val ubicacion = editTextUbicacion.text.toString()
            obtenerClima(ubicacion)
        }
    }
    //buttonBuscar.setOnClickListener { ... }: Establece un listener para el botón de búsqueda.
    // Cuando se hace clic en el botón, se obtiene la ubicación ingresada por el usuario y se llama a la
    // función obtenerClima.

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

    //private fun obtenerClima(ubicacion: String) { ... }: Define la función obtenerClima, que realiza la llamada a la API de OpenWeatherMap.
    //CoroutineScope(Dispatchers.IO).launch { ... }: Inicia una corrutina en el hilo de E/S para realizar la llamada a la API sin bloquear el hilo principal.
    //try { ... } catch (e: Exception) { ... }: Maneja posibles excepciones durante la llamada a la API.
    //val url = URL(...): Construye la URL de la API con la ubicación y la clave API.
    //val respuesta = url.readText(): Realiza la llamada a la API y obtiene la respuesta JSON como una cadena.
    //val json = JSONObject(respuesta): Convierte la cadena JSON en un objeto JSONObject.
    //val temperatura = json.getJSONObject("main").getDouble("temp"): Extrae la temperatura del objeto JSON.
    //val descripcion = json.getJSONArray("weather").getJSONObject(0).getString("description"): Extrae la descripción del clima del objeto JSON.
    //withContext(Dispatchers.Main) { ... }: Cambia al hilo principal para actualizar la interfaz de usuario.
    //textViewClima.text = ...: Establece el texto del TextView con la temperatura y la descripción del clima.
    //e.printStackTrace(): Imprime la traza de la excepción en la consola en caso de error.
}