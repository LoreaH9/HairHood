package com.example.hairhood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hairhood.R
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.databinding.FragmentMoreInfoPeluBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var nom : FragmentMoreInfoPeluBinding

class MoreInfoPelu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        nom = FragmentMoreInfoPeluBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val nom : FragmentMoreInfoPeluBinding = FragmentMoreInfoPeluBinding.inflate(inflater, container, false)

        nom.btnVolverPelu.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layouMasInfoPelu, PerfilPeluquero())
            fragmentTransaction.commit()
            nom.btnVolverPelu.visibility = View.GONE
            nom.btnGuardarPelu.visibility = View.GONE
            nom.btnCambiarContraPelu.visibility = View.GONE
        }

        nom.btnCambiarContraPelu.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layouMasInfoPelu, ChangePeluPwd())
            fragmentTransaction.commit()
            nom.btnVolverPelu.visibility = View.GONE
            nom.btnGuardarPelu.visibility = View.GONE
            nom.btnCambiarContraPelu.visibility = View.GONE
            nom.editTextEmailPelu.visibility = View.GONE
            nom.editTextEspecialidadPelu.visibility = View.GONE
            nom.editTextTfnoPelu.visibility = View.GONE
            nom.editTextNombrePelu2.visibility = View.GONE
        }

        return nom.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoreInfoPelu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoreInfoPelu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}