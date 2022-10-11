package com.example.hairhood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivityChangePasswordBinding
import com.example.hairhood.fragments.Profile

class ChangePassword : AppCompatActivity() {

    lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button6.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cLayout, Profile())
            desaparece()
            //fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    //fun hacerVisibleLaFoto(v: View) { imageView.visibility = View.VISIBLE }
    fun desaparece(/*v : View*/) {
        binding.txtOldPwd.visibility = View.GONE
        binding.txtOldPwd2.visibility = View.GONE
        binding.txtOldPwd3.visibility = View.GONE
        binding.editTextTextPassword.visibility = View.GONE
        binding.editTextTextPassword2.visibility = View.GONE
        binding.editTextTextPassword3.visibility = View.GONE
        binding.button5.visibility = View.GONE
        binding.button6.visibility = View.GONE
    }

}