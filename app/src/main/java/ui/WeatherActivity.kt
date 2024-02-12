package ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lived.R
import com.squareup.picasso.Picasso
import data.WeatherResponse
import viewmodel.WeatherViewModel

class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var editTextText: EditText
    private lateinit var descriptionTextView: TextView
    private lateinit var iconImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        editTextText = findViewById(R.id.editTextText)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        iconImageView = findViewById(R.id.imageView)

        weatherViewModel.weatherData.observe(this, Observer { weatherResponse ->
            weatherResponse?.let {
                updateUI(weatherResponse)
            }
        })

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val city = editTextText.text.toString()
            weatherViewModel.getWeather(city)
        }
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val iconUrl = "https://openweathermap.org/img/w/${weatherResponse.weather.firstOrNull()?.icon}.png"
        Picasso.get().load(iconUrl).into(iconImageView)
    }
}
