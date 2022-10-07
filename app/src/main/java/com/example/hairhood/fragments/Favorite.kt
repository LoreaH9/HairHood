package com.example.hairhood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.databinding.FragmentFavoriteBinding

class Favorite : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        //set variables in Binding
        binding.btnPrueba.setOnClickListener {
            val intent = Intent(this@Favorite.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}