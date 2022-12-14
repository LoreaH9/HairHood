package com.example.hairhood.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.hairhood.databinding.ActivityAdminWorkerBinding
import com.example.hairhood.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminWorkerActivity : AppCompatActivity() {
    private lateinit var usuario: MutableMap<String, Any>
    private lateinit var binding: ActivityAdminWorkerBinding
    private val db = Firebase.firestore
    private lateinit var routine: CoroutineScope

    companion object{
        const val USER_INFO ="AdminWorkerActivity:info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading()
        val user: User =intent?.getParcelableExtra<User>(AdminWorkerActivity.USER_INFO)?:throw IllegalStateException()
        searchUserInfo(user)
        binding.infoWorker.typeWrite(this, "Editar usuario: "+user.usuario , 300L)

        binding.btnRemoveWorker.setOnClickListener {
            db.collection("peluqueros").document(user.usuario)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminWorkerActivity, AdminActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
        binding.btnSaveChangesWorker.setOnClickListener {
            val dato = hashMapOf(
                "nombre" to binding.nombrePeluquero.text.toString(),
                "dni" to binding.dniPeluquero.text.toString(),
                "numTelefono" to binding.numTlfPeluquero.text.toString().toInt(),
                "email" to binding.emailPeluquero.text.toString(),
                "contrase??a" to binding.passPeluquero.text.toString(),
                "verificado" to binding.verificado.isChecked
            )

            db.collection("peluqueros").document(user.usuario).update(dato as Map<String, Any>)
            Toast.makeText(this,"Usuario actualizado correctamente",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@AdminWorkerActivity, AdminActivity::class.java))
        }
    }
    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long) {
        this@typeWrite.text = ""
        if(::routine.isInitialized) routine.cancel()

        lifecycleOwner.lifecycleScope.launch {
            routine = this
            repeat(text.length) {
                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)
            }
        }
    }
    private fun showLoading() {
        binding.progressBarWorker.visibility = View.VISIBLE
        binding.ScrollPeluquero.visibility= View.GONE;
    }

    private fun hideLoading() {
        binding.progressBarWorker.visibility = View.GONE
        binding.ScrollPeluquero.visibility= View.VISIBLE;
    }

    private fun searchUserInfo(user: User) {
        showLoading()

        db.collection("peluqueros").whereEqualTo("usuario", user.usuario).get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        usuario = document.data
                        binding.nombrePeluquero.setText(usuario["nombre"].toString())
                        binding.dniPeluquero.setText(usuario["dni"].toString())
                        binding.numTlfPeluquero.setText(usuario["numTelefono"].toString())
                        binding.tituloPeluquero.setText(usuario["titulo"].toString())
                        binding.emailPeluquero.setText(usuario["email"].toString())
                        binding.passPeluquero.setText(usuario["contrase??a"].toString())
                        binding.verificado.isChecked = usuario["verificado"] as Boolean
                }
                    hideLoading()
                }
            })
    }
}