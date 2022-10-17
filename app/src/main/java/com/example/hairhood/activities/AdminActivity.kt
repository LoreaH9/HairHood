package com.example.hairhood.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.R
import com.example.hairhood.UserAdapter
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
        getAllUsers()

        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("com.example.hairhood.activities.getUser", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(USER_KEY, "")
            editor.apply()
            var i = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
        Thread(Runnable {
            // performing some dummy time taking operation


        }).start()
    }

    private fun getAllUsers() {
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
                        tableRecyclerView = findViewById(R.id.table_recycler_view)
                        UserAdapter = UserAdapter(userList){ user->
                            if(user.tipo=="P"){
                                val intent= Intent(this@AdminActivity, AdminUserActivity::class.java)
                                intent.putExtra(AdminUserActivity.USER_INFO, user)
                                startActivity(intent)
                            }else{
                                val intent= Intent(this@AdminActivity, AdminWorkerActivity::class.java)
                                intent.putExtra(AdminWorkerActivity.USER_INFO, user)
                                startActivity(intent)
                            }
                        }
                        tableRecyclerView.layoutManager = LinearLayoutManager(this)
                        tableRecyclerView.adapter = UserAdapter
                    }
                    .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}
            }
            .addOnFailureListener { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()}

    }

}