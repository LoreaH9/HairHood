package com.example.hairhood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hairhood.R
import com.example.hairhood.model.User

class AdminWorkerActivity : AppCompatActivity() {
    companion object{
        const val USER_INFO ="AdminWorkerActivity:info"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_worker)
        val user: User =intent?.getParcelableExtra<User>(AdminWorkerActivity.USER_INFO)?:throw IllegalStateException()

    }
}