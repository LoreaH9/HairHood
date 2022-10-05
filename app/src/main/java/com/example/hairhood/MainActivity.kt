package com.example.hairhood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hairhood.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.prueba.text = "Cargando datos..."
        val db = Firebase.firestore

        db.collection("peluqueros")
            .get()
            .addOnSuccessListener { list ->
                if (list != null) {
                    list.forEach {
                        binding.prueba.text = "${it.data}\n"
                    }
                }else{
                    binding.prueba.text = "error al cargar los datos"
                }
            }
            .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}

    }
}