package com.example.hairhood.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairhood.R
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.activities.USER_KEY
import com.example.hairhood.databinding.FragmentProfileBinding

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

        binding.btnCambiarContra.setOnClickListener {
            /*val editProfileIntent = Intent(getActivity(), ChangePwd()::class.java)
            getActivity()?.startActivity(editProfileIntent)*/
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cLayoutProfile, ChangePwd())
            //fragmentTransaction.addToBackStack(null)

            binding.editTextTextNombre.visibility = View.GONE
            binding.editTextTextCorreo.visibility = View.GONE
            binding.editTextDireccion.visibility = View.GONE
            binding.editTextTfno.visibility = View.GONE
            binding.btnCerrar.visibility = View.GONE
            binding.btnGuardar.visibility = View.GONE
            binding.btnCambiarContra.visibility = View.GONE

            fragmentTransaction.commit()
        }

        binding.btnCerrar.setOnClickListener {
            val preferences = this.requireActivity().getSharedPreferences("com.example.hairhood.activities.getUser", Context.MODE_PRIVATE)
            preferences.edit().remove("USER_KEY")
            val intent = Intent(this@Profile.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cLayoutProfile, PerfilPeluquero())
            binding.btnGuardar.visibility = View.GONE
            binding.btnCambiarContra.visibility = View.GONE
            binding.btnCerrar.visibility = View.GONE
            binding.editTextTfno.visibility = View.GONE
            binding.editTextDireccion.visibility = View.GONE
            binding.editTextTextNombre.visibility = View.GONE
            binding.editTextTextCorreo.visibility = View.GONE
            binding.button2.visibility = View.GONE
            fragmentTransaction.commit()
        }

        //set variables in Binding
        /*binding.btnMasInfor.setOnClickListener {
            val intent = Intent(this@Favorite.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }*/
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}