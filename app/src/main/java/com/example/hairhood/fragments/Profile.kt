package com.example.hairhood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairhood.databinding.FragmentProfileBinding
import com.example.hairhood.fragments.ProfileMoreInformation


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
        val vista: FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        //set variables in Binding
        binding.btnMasInfor.setOnClickListener {
            val intent = Intent(this@Profile.requireContext(), ProfileMoreInformation::class.java)
            startActivity(intent)
        }
        /*binding.btnMasInfor.setOnClickListener {
            val intent = Intent(this@Favorite.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }*/
        return vista.root
    }

}