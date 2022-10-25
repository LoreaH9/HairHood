package com.example.hairhood.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hairhood.R
import com.example.hairhood.databinding.FragmentMoreInfoPeluBinding
import com.example.hairhood.activities.MainActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var nom : FragmentMoreInfoPeluBinding


class MoreInfoPelu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var PREFS_KEY :String = "com.example.hairhood.activities.getUser"
    var USER_KEY :String = "USER_KEY"
    var PWD_KEY :String = "PWD_KEY"
    var ROL_KEY :String = "ROL_KEY"

    lateinit var sharedPreferences: SharedPreferences

    var us = ""
    val db = Firebase.firestore
    var dni = ""
    var usuario = ""
    var verificado = false
    var contraseña = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        nom = FragmentMoreInfoPeluBinding.inflate(layoutInflater)

        sharedPreferences = requireActivity().getSharedPreferences(com.example.hairhood.activities.PREFS_KEY, Context.MODE_PRIVATE);

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val nom : FragmentMoreInfoPeluBinding = FragmentMoreInfoPeluBinding.inflate(inflater, container, false)

        us = sharedPreferences.getString(USER_KEY, "")!!

        if (us != "") {

            db.collection("peluqueros")
                .document(us)
                .get()
                .addOnSuccessListener { user ->
                    if (user != null) {
                        //Toast.makeText(requireContext(), user.data.toString(), Toast.LENGTH_SHORT).show()
                        //binding.editTextTextNombre.setText(it.get("nombre") as String?)
                        nom.editTextNombrePelu2.setText(user.data?.get("nombre").toString())
                        nom.editTextEmailPelu.setText(user.data?.get("email").toString())
                        nom.editTextTfnoPelu.setText(user.data?.get("numTelefono").toString())
                        dni = user.data?.get("dni").toString()
                        usuario = user.data?.get("usuario").toString()
                        var verificadoP = user.data?.get("verificado").toString()

                        if (verificadoP.equals("true")){
                            verificado = true
                        }
                        contraseña = user.data?.get("contraseña").toString()
                    } else {
                        Toast.makeText(requireContext(), "problema", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "No has iniciado sesion", Toast.LENGTH_SHORT).show()
        }

        nom.btnVolverPelu.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layouMasInfoPelu, PerfilPeluquero())
            fragmentTransaction.commit()
            nom.card.visibility = View.GONE
        }

        nom.btnCambiarContraPelu.setOnClickListener {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layouMasInfoPelu, ChangePwd())
            fragmentTransaction.commit()
            nom.card.visibility = View.GONE
        }

        var errores = false

        nom.editTextEmailPelu.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val emailText = nom.editTextEmailPelu.text.toString()
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    //binding.errorCorreo.visibility = View.VISIBLE
                    nom.editTextEmailPelu.error = getString(R.string.emailError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

        nom.editTextTfnoPelu.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                val tfnoText = nom.editTextTfnoPelu.text.toString()
                if (tfnoText.length != 9) {
                    nom.editTextTfnoPelu.error = getString(R.string.tfnoError)
                    errores = true
                } else {
                    errores = false
                }
            }
        }

        nom.btnGuardarPelu.setOnClickListener {

            val emailText = nom.editTextEmailPelu.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                //binding.errorCorreo.visibility = View.VISIBLE
                nom.editTextEmailPelu.error = getString(R.string.emailError)
                errores = true
            } else {
                errores = false
            }

            val tfnoText = nom.editTextTfnoPelu.text.toString()
            if (tfnoText.length != 9) {
                nom.editTextTfnoPelu.error = getString(R.string.tfnoError)
                errores = true
            } else {
                errores = false
            }

            if (!errores) {
                var nombrePelu = nom.editTextNombrePelu2.text.toString()
                var emailPelu = nom.editTextEmailPelu.text.toString()
                var tfnoPelu = nom.editTextTfnoPelu.text.toString()

                db.collection("peluqueros").document(us).set(
                    hashMapOf(
                        "contraseña" to contraseña,
                        "dni" to dni,
                        "email" to emailPelu,
                        "nombre" to nombrePelu,
                        "numTelefono" to tfnoPelu.toInt(),
                        "usuario" to us,
                        "verificado" to verificado
                    )
                )

                val intent = Intent(this@MoreInfoPelu.requireContext(), MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireActivity().baseContext, getString(R.string.fields), Toast.LENGTH_SHORT).show()
            }

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