@file:Suppress("DEPRECATION")

package com.example.hairhood.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityLoginBinding
import com.example.hairhood.fragments.Favorite
import com.google.android.gms.tasks.Tasks.call
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.util.jar.Manifest

class LoginActivity : AppCompatActivity() {

    companion object {
        var pelu : Boolean? = false
        var nombre : String? = ""
        var contra : String? = ""
    }

    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "com.example.hairhood.activities.getUser"
    var USER_KEY = "USER_KEY"
    var PWD_KEY = "PWD_KEY"

    var user = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_HairHood)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Firebase.firestore

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(USER_KEY, "").toString()
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()

        //Redireccion al formulario del registro
        binding.registerRedirect.setOnClickListener {
            val intentRegitro = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegitro)
        }

        //Pulsa boton login
        binding.btnLogin.setOnClickListener {

            //Si no mete alguno de los datos
            if (TextUtils.isEmpty(binding.user.text.toString()) && TextUtils.isEmpty(binding.password.text.toString())) {
                Toast.makeText(this, "Por favor introduzca el usuario y la contraseña", Toast.LENGTH_SHORT).show();
            } else {

                val hashedPassword = hashPassword(binding.password.text.toString().replace(" ", ""))
                val userName = binding.user.text.toString().replace(" ", "")

                //Mira si es un usuario
                db.collection("clientes")
                    .get()
                    .addOnSuccessListener { list ->
                        list.forEach { usuario ->
                            if (userName == usuario.data["usuario"] && hashedPassword == usuario.data["contraseña"]) {
                                pelu = false
                                nombre = binding.user.text.toString()
                                contra = binding.password.text.toString()
                                saveChanges(usuario.data["usuario"].toString(), usuario.data["contraseña"].toString())
                            }else{
                                //En caso de no ser usuario mira si es peluquero
                                db.collection("peluqueros")
                                    .get()
                                    .addOnSuccessListener { list ->
                                        list.forEach { peluquero ->
                                            if (userName == peluquero.data["usuario"] && hashedPassword == peluquero.data["contraseña"]) {
                                                pelu = true
                                                nombre = binding.user.text.toString()
                                                contra = binding.password.text.toString()
                                                saveChanges(peluquero.data["usuario"].toString(), peluquero.data["contraseña"].toString())
                                            }}}
                                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}}}}
                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
        }

        //Volver a la página principal

        binding.closeLoginFrm.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding.btnLlamada.setOnClickListener{ requestPermissions() }

    }

    private fun requestPermissions() {
        val phone="645 52 87 12".toString()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED->{
                    call(phone)
                }else->requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }else{
            call(phone)

        }
    }

    private fun call(phone:String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
        val phone="645 52 87 12".toString()
        if(isGranted){
            call(phone)
        }else{
            Toast.makeText(this, "Se necesitan permisos", Toast.LENGTH_SHORT).show()
        }
    }

    //Hash password and rm blank spaces
    private fun hashPassword(psswd: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(psswd.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {sb.append(String.format("%02X", b))}
        return sb.toString()
    }

    //Guarda el inicio de sesion en SharedPreferences
    private fun saveChanges(usuario: String, psswd: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_KEY, usuario)
        editor.putString(PWD_KEY, psswd)
        editor.apply()
        user = sharedPreferences.getString(USER_KEY, "").toString()
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()
        intentLogin()

    }
    private fun intentLogin(){
        var i = Intent(this@LoginActivity, MainActivity::class.java)
        if(user=="admin") {i = Intent(this@LoginActivity, AdminActivity::class.java)}
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        //Meterse a la app si ya tenia una sesión iniciada
        if (user != "" && pwd != "") {
            intentLogin()
        }
    }

}