package com.example.hairhood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Siguiente pantalla que tiene que abrir
        val intent = Intent(this, a_login::class.java)
        //Abre la siguiente actividad
        startActivity(intent)
        //Cuando abra la siguiente actividad cerrara esta
        finish()
    }
}