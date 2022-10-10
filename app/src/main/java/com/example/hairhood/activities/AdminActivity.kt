package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.UserAdapter
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.model.User

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    private var userList = ArrayList<User>()
    private lateinit var UserAdapter:UserAdapter
    private lateinit var tableRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userList.add(User("12345678A", "prueba", "peluquero"))
        userList.add(User("12345678A", "prueba", "cliente"))
        tableRecyclerView = findViewById(R.id.table_recycler_view)
        UserAdapter = UserAdapter(userList)
        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = UserAdapter

    }

}