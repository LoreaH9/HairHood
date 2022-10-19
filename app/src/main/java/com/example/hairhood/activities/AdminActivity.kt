package com.example.hairhood.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.R
import com.example.hairhood.UserAdapter
import com.example.hairhood.databinding.ActivityAdminBinding
import com.example.hairhood.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
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
        binding.progressBar.visibility = View.VISIBLE;
        binding.progressBar.visibility = View.INVISIBLE;

        queryFirebase()


        binding.logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("com.example.hairhood.activities.getUser", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(USER_KEY, "")
            editor.apply()
            var i = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
    private fun queryFirebase() {
        showLoading()
        db.collection("clientes").get().addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
            if (task.isSuccessful) {
                val userList: MutableList<User?> = ArrayList()
                for (document in task.result) {
                    if(document.data["usuario"].toString()!="admin")
                        userList.add(User(document.data["dni"].toString(), document.data["usuario"].toString(), "C"))
                }
                hideLoading()
                tableRecyclerView = findViewById(R.id.table_recycler_view)
                UserAdapter = UserAdapter(userList){ user->
                    if(user.tipo=="C"){
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
        })
    }

}