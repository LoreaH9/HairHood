package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.UserAdapter
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    private var userList = ArrayList<User>()
    private lateinit var UserAdapter:UserAdapter
    private lateinit var tableRecyclerView : RecyclerView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllUsers()
    }

    private fun getAllUsers() {
        //Añade todos los usuarios
        db.collection("clientes")
            .get()
            .addOnSuccessListener { list ->
                list.forEach { usuario ->
                    if(usuario.data["usuario"].toString()!="admin")
                    userList.add(User(usuario.data["dni"].toString(), usuario.data["usuario"].toString(), "C"))
                }
                db.collection("peluqueros")
                    .get()
                    .addOnSuccessListener { list ->
                        list.forEach { peluquero ->
                            userList.add(User(peluquero.data["dni"].toString(), peluquero.data["usuario"].toString(), "P"))
                        }
                        //Recycle View tabla todos los usuarios
                        tableRecyclerView = findViewById(R.id.table_recycler_view)
                        UserAdapter = UserAdapter(userList){ user->
                            val intent= Intent(this@AdminActivity, AdminUserActivity::class.java)
                            intent.putExtra(AdminUserActivity.USER_INFO, user)
                            startActivity(intent)

                        }
                        tableRecyclerView.layoutManager = LinearLayoutManager(this)
                        tableRecyclerView.adapter = UserAdapter
                    }
                    .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
            .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}

    }

}