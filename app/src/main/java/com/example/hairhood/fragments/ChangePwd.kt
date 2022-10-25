package com.example.hairhood.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hairhood.R
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.databinding.FragmentChangePwdBinding
import com.example.hairhood.databinding.FragmentProfileBinding
import com.example.hairhood.activities.LoginActivity.Companion.contra
import com.google.firebase.firestore.FirebaseFirestore
import com.example.hairhood.activities.LoginActivity.Companion.nombre
import com.example.hairhood.activities.MainActivity.Companion.prfPelu

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var binding: FragmentChangePwdBinding
val dato = nombre

class ChangePwd : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var sharedPreferences: SharedPreferences
    val db = FirebaseFirestore.getInstance()

    var nom = ""
    var contra = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentChangePwdBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentChangePwdBinding = FragmentChangePwdBinding.inflate(inflater, container, false)
        // FragmentProfileBinding.inflate(inflater, container, false)

        /*if (sharedPreferences.getString(USER_KEY, "").toString() != "") {

            nom = sharedPreferences.getString(USER_KEY, "").toString()

            db.collection("clientes").document(nom).get().addOnSuccessListener {

            }

        } else {
            Toast.makeText(requireActivity().baseContext, "No has iniciado sesion", Toast.LENGTH_SHORT).show()
        }*/

        binding.btnVolverContra.setOnClickListener {

            //binding.editTextContraActual.setText("Gaizka")

            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layoutContra,
                if (prfPelu == false) {
                    /*val input = dato
                    val bundle = Bundle()
                    bundle.putString("nameUser", input)*/
                    Profile()
                } else {
                    val input = dato
                    val bundle = Bundle()
                    bundle.putString("nameUser", input)
                    PerfilPeluquero()
                })
            binding.btnGuardarContra.visibility = View.GONE
            binding.btnVolverContra.visibility = View.GONE
            binding.txtContraActual.visibility = View.GONE
            binding.txtContraNueva.visibility = View.GONE
            binding.txtRepiteContra.visibility = View.GONE
            binding.editTextContraActual.visibility = View.GONE
            binding.editTextContraNueva.visibility = View.GONE
            binding.editTextRepiteContra.visibility = View.GONE
            binding.cardViewContra.visibility = View.GONE
            fragmentTransaction.commit()

        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_change_pwd, container, false)
    }

    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChangePwd.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangePwd().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}