package com.example.hairhood.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding
import com.google.firebase.firestore.FirebaseFirestore


class SelectorPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding
    lateinit var sharedPreferences: SharedPreferences


      var nomC = ""
      var nomP = ""
      var img = ""
      val db = FirebaseFirestore.getInstance()

    companion object {
        const val EXTRA_MOVIE = "SelectorPeluquero:extraPeluqueros"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(USER_KEY, "").toString()


        if (user != "") {
            if (sharedPreferences.getString(USER_KEY, "").toString() != "" || sharedPreferences.getString(USER_KEY, "").toString().equals(null)) {
                nomC = sharedPreferences.getString(USER_KEY, "").toString()

                db.collection("favoritos").document(nomC).get().addOnSuccessListener {

                }
            }
        }



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
            guardarFav(db)
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
    fun guardarFav(db: FirebaseFirestore) {
        val fav = hashMapOf(
            "usuarioCliente" to nomC,
            "usuarioPelu" to nomP

        )
        db.collection("favoritos")
            .document("$nomC fav $nomP")
            .set(fav)

    }
}