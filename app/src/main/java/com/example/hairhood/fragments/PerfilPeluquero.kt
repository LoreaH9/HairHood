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
import com.example.hairhood.activities.PREFS_KEY
import com.example.hairhood.activities.PWD_KEY
import com.example.hairhood.activities.USER_KEY
import com.example.hairhood.databinding.FragmentPerfilPeluqueroBinding
import com.example.hairhood.databinding.FragmentProfileBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var PREFS_KEY :String = "com.example.hairhood.activities.getUser"
var USER_KEY :String = "USER_KEY"
var PWD_KEY :String = "PWD_KEY"
var ROL_KEY :String = "ROL_KEY"

private lateinit var llamada : FragmentPerfilPeluqueroBinding
private lateinit var otraP : FragmentProfileBinding

class PerfilPeluquero : Fragment() {

    companion object {

        var usuPelu = ""

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilPeluquero.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilPeluquero().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        sharedPreferences = activity!!.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        llamada = FragmentPerfilPeluqueroBinding.inflate(layoutInflater)
        otraP = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val llamada : FragmentPerfilPeluqueroBinding = FragmentPerfilPeluqueroBinding.inflate(inflater, container, false)

        llamada.editTextNombrePelu.setText(sharedPreferences.getString(USER_KEY, ""))


        llamada.btnMasInforPelu.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layoutPelu, MoreInfoPelu())
            usuPelu = llamada.editTextNombrePelu.text.toString()
            fragmentTransaction.commit()
            llamada.card.visibility = View.GONE
        }

        llamada.btnCerrarSesionPelu.setOnClickListener {
            val preferences = this.requireActivity()
                .getSharedPreferences("com.example.hairhood.activities.getUser", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(USER_KEY, "")
            editor.putString(PWD_KEY, "")
            editor.putBoolean(ROL_KEY, false)
            editor.apply()
            val intent = Intent(this@PerfilPeluquero.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        return llamada.root
    }
}