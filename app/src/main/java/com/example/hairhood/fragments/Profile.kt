package com.example.hairhood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.activities.ChangePassword
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.activities.RegisterActivity
import com.example.hairhood.databinding.ActivityChangePasswordBinding
import com.example.hairhood.databinding.FragmentProfileBinding
import androidx.fragment.app.FragmentActivity

class Profile : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val editProfileIntent = Intent(getActivity(), ChangePassword()::class.java)
            getActivity()?.startActivity(editProfileIntent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this@Profile.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cLayoutProfile, Map())
            //fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //set variables in Binding
        /*binding.btnMasInfor.setOnClickListener {
            val intent = Intent(this@Favorite.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }*/
        return binding.root
    }

}