package com.example.hairhood.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivitySelectorPeluqueroBinding
import com.example.hairhood.fragments.Favorite
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class SelectorPeluquero : AppCompatActivity() {
    private lateinit var binding: ActivitySelectorPeluqueroBinding
    lateinit var sharedPreferences: SharedPreferences


      var nomC = ""
      var nomP = ""
      var img = ""
      val db = Firebase.firestore

    companion object {
        const val EXTRA_PELUQUERO = "SelectorPeluquero:extraPeluqueros"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorPeluqueroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(USER_KEY, "").toString()

        //Aqui recoge desde que peluquero hemos accedido a esta pantalla
//        if (desdeUno) {
//            binding.nombre.setText("1")
//        } else if (desdeDos) {
//            binding.nombre.setText("2")
//        } else if (desdeTres) {
//            binding.nombre.setText("3")
//        } else if (desdeCuatro) {
//            binding.nombre.setText("4")
//        } else if (desdeCinco) {
//            binding.nombre.setText("5")
//        } else if (desdeSeis) {
//            binding.nombre.setText("6")
//        } else if (desdeSiete) {
//            binding.nombre.setText("7")
//        } else if (desdeOcho) {
//            binding.nombre.setText("8")
//        }


        if (user != "") {
            if (sharedPreferences.getString(USER_KEY, "").toString() != "" || sharedPreferences.getString(USER_KEY, "").toString().equals(null)) {
                nomC = sharedPreferences.getString(USER_KEY, "").toString()

                db.collection("favoritos").document(nomC).get().addOnSuccessListener {

                }
            }
        }

        db.collection("peluqueros")
            .document(intent.extras!!.get("usuario").toString())
            .get()
            .addOnSuccessListener {

                val image = FirebaseStorage.getInstance().getReferenceFromUrl(it.data!!["foto"].toString())
                binding.nombre.text = it.data!!["usuario"].toString()
                nomP= it.data!!["usuario"].toString()
                image.getBytes(10 * 200 * 200).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    val resized = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
                    binding.fotoPeluquero.setImageBitmap(resized)
                }.addOnFailureListener {
                    binding.fotoPeluquero.setImageDrawable(resources.getDrawable(R.drawable.peluquero1))
                }
            }

   /* if (db.document("$nomC fav $nomP").exists()){
        binding.fav.visibility= View.VISIBLE
        binding.nofav.visibility = View.GONE
    }else{
        binding.fav.visibility= View.GONE
        binding.nofav.visibility = View.VISIBLE
    }*/
if (db.collection("favoritos").document("$nomC fav $nomP")!=null){
    Log.d(ContentValues.TAG, "(db.document(\"$nomC fav $nomP\")al entrar")

    binding.fav.visibility= View.VISIBLE
    binding.nofav.visibility = View.GONE
}else{
    binding.fav.visibility= View.GONE
    binding.nofav.visibility = View.VISIBLE

}



        binding.fav.setOnClickListener(){
            binding.fav.visibility=View.GONE
            binding.nofav.visibility=View.VISIBLE
            quitarFav(db)
        }
            binding.nofav.setOnClickListener() {
                binding.fav.visibility = View.VISIBLE
                binding.nofav.visibility = View.GONE
                nomP = binding.nombre.text.toString()
                guardarFav(db)

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
        Log.d("GuardaFav.TAG", "(db.document(\"$nomC fav $nomP\").exists()")
    }
    fun quitarFav(db: FirebaseFirestore) {
        db.collection("favoritos")
            .document("$nomC fav $nomP")
            .delete()

    }
}