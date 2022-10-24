package com.example.hairhood.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding
import com.google.firebase.firestore.FirebaseFirestore


class SelectorPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding
  /*  var nom = ""
    var img = ""
    val db = FirebaseFirestore.getInstance()*/

    companion object {
        const val EXTRA_MOVIE = "SelectorPeluquero:extraPeluqueros"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)




       /* db.collection("peluqueros").document(nom).get().addOnSuccessListener {
            binding.nombre.setText(it.get("usuario") as String)

            img = it.get("foto").toString()
            Glide.with(this)
                .load(img)
                .into(binding.fotoPeluquero)
        }*/


        binding.nofav.setOnClickListener(){
            binding.fav.visibility=View.VISIBLE
            binding.nofav.visibility=View.GONE
        }
        binding.fav.setOnClickListener(){
            binding.fav.visibility=View.GONE
            binding.nofav.visibility=View.VISIBLE
        }

        binding.infor.setOnClickListener() {
            val intent = Intent(this@SelectorPeluquero, ReservarPeluquero::class.java)
            startActivity(intent)
        }

    }
}