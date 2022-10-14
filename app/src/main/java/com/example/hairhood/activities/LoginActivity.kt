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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Tasks.call
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.util.jar.Manifest

class LoginActivity : AppCompatActivity() {

    public var pelu : Boolean = false
    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "com.example.hairhood.activities.getUser"
    var USER_KEY = ""
    var PWD_KEY = ""

    var user = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
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
                                saveChanges(usuario.data["usuario"].toString(), usuario.data["contraseña"].toString())
                                var i = Intent(this@LoginActivity, MainActivity::class.java)
                                if(userName=="admin") {i = Intent(this@LoginActivity, AdminActivity::class.java)}
                                startActivity(i)
                            }else{
                                //En caso de no ser usuario mira si es peluquero
                                db.collection("peluqueros")
                                    .get()
                                    .addOnSuccessListener { list ->
                                        list.forEach { peluquero ->
                                            if (userName == peluquero.data["usuario"] && hashedPassword == peluquero.data["contraseña"]) {
                                                saveChanges(peluquero.data["usuario"].toString(), peluquero.data["contraseña"].toString())
                                                val intentCorrecto =Intent(this, MainActivity::class.java)
                                                startActivity(intentCorrecto)
                                                pelu = true
                                            }else if(USER_KEY==""){
                                                //Los datos no son correctos
                                                Toast.makeText(this, "Contraseña o usuario incorrecto", Toast.LENGTH_SHORT).show()}}}
                                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}}}}
                    .addOnFailureListener {Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
        }

        //Volver a la página principal
        binding.closeLoginFrm.setOnClickListener {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //binding.btnLlamada.setOnClickListener{requestPermissions()}

        }/*
    private fun requestPermission() {
        //https://www.youtube.com/watch?v=Z1v0EVhDSsk
val phone="645 52 87 12".toString()

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            when{
                ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED->{
                    call(phone)
                }else->requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)

                }
            }


    }

    private fun call() {
startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->if(isGranted){
            call()
        }else{
            Toast.makeText(this, "Se necesitan permisos", Toast.LENGTH_SHORT).show()


/*No funciona
binding.btnLlamada.setOnClickListener{
    val dial="645528712".toString()
    val intentLlamada =Intent(Intent.ACTION_DIAL,  Uri.parse(dial))
    startActivity(intentLlamada)
}*/
        }
    }


*/
    //Hash password and rm blank spaces
    private fun hashPassword(psswd: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(psswd.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {sb.append(String.format("%02X", b))}
        return sb.toString()
    }

    //Guarda el inicio de sesion en SharedPreferences
    private fun saveChanges(user: String, psswd: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_KEY, user)
        editor.putString(PWD_KEY, psswd)
        editor.apply()
        var i = Intent(this@LoginActivity, MainActivity::class.java)
        if(user=="admin") {i = Intent(this@LoginActivity, AdminActivity::class.java)}
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        //Meterse a la app si ya tenia una sesión iniciada
        if (user != "" && pwd != "") {
            var i = Intent(this@LoginActivity, MainActivity::class.java)
            if(user=="admin") {i = Intent(this@LoginActivity, AdminActivity::class.java)}
            startActivity(i)
            finish()
        }
    }

}