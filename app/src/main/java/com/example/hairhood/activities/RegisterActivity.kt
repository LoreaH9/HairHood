package com.example.hairhood.activities

//https://www.youtube.com/watch?v=QUC9BILIbJ8 Video para subir archivos
//https://adorahack.com/upload-gallery-image-to-firebase-from-android-app-kotlin Pagina pra archivos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest


@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {
    private var filePath: Uri? = null
    var imageURL = ""
    private var firebaseStore: FirebaseStorage? = null
    private val PICK_IMAGE_CLIENTE = 71
    private val PICK_IMAGE_PELUQUERO = 72
    private lateinit var binding: ActivityRegisterBinding
    lateinit var sharedPreferences: SharedPreferences
    var db=FirebaseFirestore.getInstance()
    private val File=1
    private val database = Firebase.database
    private val storage = Firebase.storage
    private var storageReference = storage.reference
    val myRef=database.getReference("Clientes")
    var errores=false

    companion object {
        var nomUs : String? = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    user = sharedPreferences.getString(USER_KEY, "").toString()

    db=FirebaseFirestore.getInstance()

    binding.singUpUser.setOnClickListener {
        binding.singUpUser.background = resources.getDrawable(R.drawable.switch_trcks,null)
        binding.singUpUser.setTextColor(resources.getColor(R.color.textColor,null))
        binding.singUpWorker.background = null
        binding.ScrollUsuario.visibility = View.VISIBLE
        binding.ScrollPeluquero.visibility = View.GONE
        binding.singUpWorker.setTextColor(resources.getColor(R.color.pinkColor,null))
    }

    binding.singUpWorker.setOnClickListener {
        binding.singUpUser.background = null
        binding.singUpUser.setTextColor(resources.getColor(R.color.pinkColor,null))
        binding.singUpWorker.background = resources.getDrawable(R.drawable.switch_trcks,null)
        binding.ScrollUsuario.visibility = View.GONE
        binding.ScrollPeluquero.visibility = View.VISIBLE
        binding.singUpWorker.setTextColor(resources.getColor(R.color.textColor,null))
    }

    binding.singInCliente.setOnClickListener {

        if (TextUtils.isEmpty(binding.usuarioCliente.text.toString()) ||
            TextUtils.isEmpty(binding.passCliente.text.toString()) ||
            TextUtils.isEmpty(binding.emailCliente.text.toString()) ||
            TextUtils.isEmpty(binding.nombreCliente.text.toString()) ||
            TextUtils.isEmpty(binding.dniCliente.text.toString()) ||
            TextUtils.isEmpty(binding.numTlfCliente.text.toString()) ||
            TextUtils.isEmpty(binding.direccionCliente.text.toString())||
            TextUtils.isEmpty(binding.fechaCliente.text.toString()) ||
                    imageURL == ""
        ) {
            Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            var i=0
            var mayus=false
            var numero=false
            if(binding.dniCliente.text.toString().length==9){
                while(i<binding.dniCliente.text.toString().length || (!mayus && !numero)){
                    val g = binding.dniCliente.text.toString()
                    val c = g.get(index = i)
                    if(c in 'A'..'Z') {
                        mayus=true
                    }
                    if( c in '0'..'9'){
                        numero=true
                    }

                    i++
                }
            } else{
                Toast.makeText(this, "Por favor compruebe el dni", Toast.LENGTH_SHORT).show();
            }
            val passCliente: String = binding.passCliente.getText().toString()
            val confirmPassCliente: String = binding.passConfirmCliente.getText().toString()
            if (passCliente != confirmPassCliente) {
                Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }else {
                lifecycleScope.launch(Dispatchers.IO){
                    verifyUser(binding.usuarioCliente.text.toString(), "c",passCliente)
                }
            }
        }

        nomUs = binding.usuarioCliente.text.toString()

    }

        binding.numTlfCliente.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var tfnoText = binding.numTlfCliente.text.toString()
                if (tfnoText.length != 9) {
                    binding.numTlfCliente.error = getString(R.string.tfnoError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

        binding.emailCliente.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var emailText = binding.emailCliente.text.toString()
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    binding.emailCliente.error = getString(R.string.emailError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

    binding.singInPelu.setOnClickListener {


        if (TextUtils.isEmpty(binding.usuarioPeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.passPeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.emailPeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.nombrePeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.dniPeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.numTlfPeluquero.text.toString()) ||
            TextUtils.isEmpty(binding.fechaPeluquero.text.toString()) ||
            imageURL == ""

        ) {
            Toast.makeText(this,R.string.vacio,Toast.LENGTH_SHORT).show();
        } else {
            val passPeluquero: String =binding.passPeluquero.getText().toString()
            val confirmPassPeluquero: String =binding.passConfirmPeluquero.getText().toString()

            if (passPeluquero != confirmPassPeluquero) {
                Toast.makeText(this,"No coinciden las contraseñas",Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO){
                    verifyUser(binding.usuarioPeluquero.text.toString(), "p",passPeluquero)
                }
            }
        }


    }

        binding.numTlfPeluquero.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var tfnoText = binding.numTlfPeluquero.text.toString()
                if (tfnoText.length != 9) {
                    binding.numTlfPeluquero.error = getString(R.string.tfnoError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

        binding.emailPeluquero.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                var emailText = binding.emailPeluquero.text.toString()
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    binding.emailPeluquero.error = getString(R.string.emailError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

    binding.btnFotoCliente.setOnClickListener{
        subir_Archivo_Cliente()
    }
    binding.btnFotoPeluquero.setOnClickListener{
        subir_Archivo_Peluquero()
    }
}

private suspend fun verifyUser(userName: String, s: String, pass: String) {
    var existeCli = db.collection("clientes").whereEqualTo("usuario",userName).get().await()
    var existeUsu = db.collection("clientes").whereEqualTo("usuario",userName).get().await()

    if (existeCli.isEmpty && existeUsu.isEmpty) {
        if(s=="c"){guardarDatosCliente(db)}else{guardarDatosPeluquero(db)}
       // saveChanges(binding.usuarioCliente.toString(),pass)
        // startActivity(Intent(this, LoginActivity::class.java))
    }

}


    fun subir_Archivo_Cliente(){
        var intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_CLIENTE)


    }

    fun subir_Archivo_Peluquero(){
        var intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_PELUQUERO)


    }


private fun uploadImageCliente(){
    if(filePath != null){
        val ref = storageReference.child("clientes/${binding.usuarioCliente.text}.jpg")
        ref.downloadUrl.addOnSuccessListener { Uri->
            imageURL = Uri.toString()
            Log.i("Firebase", "URL: $imageURL")
        }
        val uploadTask = ref.putFile(filePath!!)
        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }

            Log.i("Firebase", "PATH: $filePath")


            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                addUploadRecordToDb(downloadUri.toString())
                Log.i("Firebase", "EZDAKIT: " +downloadUri.toString())
                imageURL = downloadUri.toString()
            }
        }

    }else{
        Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
    }
}

private fun uploadImagePelu(){
    if(filePath != null){
        val ref = storageReference.child("peluqueros/${binding.usuarioPeluquero.text}.jpg")
        ref.downloadUrl.addOnSuccessListener { Uri->
            imageURL = Uri.toString()
        }
        val uploadTask = ref.putFile(filePath!!)
        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            Toast.makeText(this, "Foto subida", Toast.LENGTH_SHORT).show()

            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result

                addUploadRecordToDb(downloadUri.toString())
            }
        }

    }else{
        Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
    }
}

private fun addUploadRecordToDb(uri: String){
    val db = FirebaseFirestore.getInstance()

    val data = HashMap<String, Any>()
    data["imageUrl"] = uri

    db.collection("posts")
        .add(data)
        .addOnSuccessListener { documentReference ->
            Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
        }
}


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == PICK_IMAGE_CLIENTE && resultCode== Activity.RESULT_OK){
        if (data==null|| data.data==null){
            return
        }
        filePath = data.data

        uploadImageCliente()

    }
    if(requestCode == PICK_IMAGE_PELUQUERO && resultCode== Activity.RESULT_OK){
        if (data==null|| data.data==null){
            return
        }
        filePath = data.data
        uploadImagePelu()

    }
    Log.i("SUBIRARCHIVO", "FOTOSEL: " + filePath.toString())


}


private fun guardarDatosCliente(db: FirebaseFirestore) {
    val pass = hashPassword(binding.passCliente.text.toString())
    val datoC = hashMapOf(
        "usuario" to binding.usuarioCliente.text.toString(),
        "nombre" to binding.nombreCliente.text.toString(),
        "dni" to binding.dniCliente.text.toString(),
        "numTelefono" to binding.numTlfCliente.text.toString().toInt(),
        "fechaNacimiento" to binding.fechaCliente.text.toString(),
        "direccion" to binding.direccionCliente.text.toString(),
        "email" to binding.emailCliente.text.toString(),
        "contraseña" to pass,
        "foto" to imageURL
    )
    db.collection("clientes")
        .document(binding.usuarioCliente.text.toString())
        .set(datoC)
        .addOnSuccessListener {
            //startActivity(Intent(this, MainActivity::class.java))
            startActivity(Intent(this, LoginActivity::class.java))

        }
        .addOnFailureListener {
            Toast.makeText(this,"Error al crear el usuario",Toast.LENGTH_SHORT).show()
        }
}
private fun hashPassword(psswd: String): String {
    val digest = MessageDigest.getInstance("SHA-1")
    val result = digest.digest(psswd.toByteArray(Charsets.UTF_8))
    val sb = StringBuilder()
    for (b in result) {sb.append(String.format("%02X", b))}
    return sb.toString()
}

private fun guardarDatosPeluquero(db: FirebaseFirestore) {
    val pass = hashPassword(binding.passPeluquero.text.toString())

    val datoP = hashMapOf(
        "usuario" to binding.usuarioPeluquero.text.toString(),
        "nombre" to binding.nombrePeluquero.text.toString(),
        "dni" to binding.dniPeluquero.text.toString(),
        "numTelefono" to binding.numTlfPeluquero.text.toString().toInt(),
        "email" to binding.emailPeluquero.text.toString(),
        "verificado" to false,
        "contraseña" to pass,
        "foto" to imageURL
    )
    db.collection("peluqueros")
        .document(binding.usuarioPeluquero.text.toString())
        .set(datoP)
        .addOnSuccessListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
        .addOnFailureListener { Exception ->
            Toast.makeText(
                this,
                "Error al crear el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
}

/*private fun saveChanges(user: String, PassCliente: String) {
    val psswd = hashPassword(PassCliente)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString(USER_KEY, user)
    editor.putString(PWD_KEY, psswd)
    editor.apply()

}*/
}
