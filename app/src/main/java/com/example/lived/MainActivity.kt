package com.example.lived

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import viewmodel.WeatherViewModel
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val temperatureTextView = findViewById<TextView>(R.id.descriptionTextView)
        val iconImageView = findViewById<ImageView>(R.id.imageView)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        button.setOnClickListener {
            val city = editText.text.toString()
            weatherViewModel.getWeather(city)
        }

        weatherViewModel.weatherData.observe(this, Observer { weatherResponse ->
            weatherResponse?.let {
                val temperatureCelsius = weatherResponse.main.temp - 273.15 // Преобразование в Цельсии
                val description = getLocalizedDescription(weatherResponse.weather.firstOrNull()?.description ?: "") // Получение локализованного описания

                temperatureTextView.text = "Температура: ${temperatureCelsius.roundToInt()} °C\nОписание: $description"
                val iconUrl = "https://openweathermap.org/img/w/${weatherResponse.weather.firstOrNull()?.icon}.png"
                Picasso.get().load(iconUrl).into(iconImageView)
            }
        })
    }

    private fun getLocalizedDescription(description: String): String {
        return description
    }

}
