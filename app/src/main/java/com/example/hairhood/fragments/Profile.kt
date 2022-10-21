package com.example.hairhood.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.activities.PWD_KEY
import com.example.hairhood.activities.USER_KEY
import com.example.hairhood.databinding.FragmentProfileBinding
import com.example.hairhood.activities.LoginActivity.Companion.nombre
import com.example.hairhood.activities.RegisterActivity.Companion.nomUs
import com.example.hairhood.activities.LoginActivity.Companion.contra
import com.example.hairhood.activities.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Profile : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    val db = FirebaseFirestore.getInstance()

    lateinit var binding: FragmentProfileBinding

    var contraseña = ""
    var nan = ""
    var fecha = ""
    var usu = nombre
    var nom = ""
    var img = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        //Coger el nombre de usuario con el que se ha iniciado sesión y ponerlo en el campo de texto
        //binding.editTextTextNombre.setText(nombre)

        if (nombre != "") {
            nom = nombre.toString()
        } else {
            nom = nomUs.toString()
        }

        db.collection("clientes").document(nom).get().addOnSuccessListener {
            binding.editTextTextNombre.setText(it.get("nombre") as String?)
            binding.editTextTextCorreo.setText(it.get("email") as String?)
            val num = it.get("numTelefono").toString()
            binding.editTextTfno.setText(num)
            binding.editTextDireccion.setText(it.get("direccion") as String?)
            nan = it.get("dni").toString()
            fecha = it.get("fechaNacimiento").toString()
            contraseña = it.get("contraseña").toString()
            img = it.get("foto").toString()
            //binding.imgPerfil.set
        }

        binding.btnCambiarContra.setOnClickListener {
            /*val editProfileIntent = Intent(getActivity(), ChangePwd()::class.java)
            getActivity()?.startActivity(editProfileIntent)*/
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cLayoutProfile, ChangePwd())
            //fragmentTransaction.addToBackStack(null)

            binding.editTextTextNombre.visibility = View.GONE
            binding.editTextTextCorreo.visibility = View.GONE
            binding.editTextDireccion.visibility = View.GONE
            binding.editTextTfno.visibility = View.GONE
            binding.btnCerrar.visibility = View.GONE
            binding.btnGuardar.visibility = View.GONE
            binding.btnCambiarContra.visibility = View.GONE

            fragmentTransaction.commit()
        }

        binding.btnCerrar.setOnClickListener {
            val preferences = this.requireActivity()
                .getSharedPreferences("com.example.hairhood.activities.getUser", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(USER_KEY, "")
            editor.putString(PWD_KEY, "")
            editor.apply()
            val intent = Intent(this@Profile.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnGuardar.setOnClickListener {
            var nomb : String = binding.editTextTextNombre.text.toString()
            var email : String = binding.editTextTextCorreo.text.toString()
            var tfno : String = binding.editTextTfno.text.toString()
            var num : Int = tfno.toInt()
            var direccion : String = binding.editTextDireccion.text.toString()

            db.collection("clientes").document(nom).set(
                hashMapOf("nombre" to nomb,
                    "email" to email,
                    "numTelefono" to num,
                    "direccion" to direccion,
                    "contraseña" to contraseña,
                    "dni" to nan,
                    "fechaNacimiento" to fecha,
                    "usuario" to usu,
                    "foto" to img)
            )

            val intent = Intent(this@Profile.requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        //set variables in Binding
        /*binding.btnMasInfor.setOnClickListener {
            val intent = Intent(this@Favorite.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }*/
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}