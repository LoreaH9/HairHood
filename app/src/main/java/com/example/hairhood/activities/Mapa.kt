package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.R
<<<<<<< Updated upstream
=======
import com.google.android.gms.maps.MapFragment
>>>>>>> Stashed changes

class Mapa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_mapa, com.example.hairhood.fragments.Map())
            .commit()
    }
<<<<<<< Updated upstream
}
=======
}

>>>>>>> Stashed changes
