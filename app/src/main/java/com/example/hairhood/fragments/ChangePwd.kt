package com.example.hairhood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hairhood.R
import com.example.hairhood.databinding.FragmentChangePwdBinding
import com.example.hairhood.databinding.FragmentProfileBinding
import com.example.hairhood.activities.LoginActivity.Companion.contra

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var binding: FragmentChangePwdBinding

class ChangePwd : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        binding.btnVolverContra.setOnClickListener {

            //binding.editTextContraActual.setText("Gaizka")

            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layoutContra, Profile())
            binding.btnGuardarContra.visibility = View.GONE
            binding.btnVolverContra.visibility = View.GONE
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