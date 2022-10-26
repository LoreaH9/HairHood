package com.example.hairhood.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hairhood.R
import com.example.hairhood.databinding.FragmentChangePwdBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.example.hairhood.activities.LoginActivity.Companion.nombre
import com.example.hairhood.activities.MainActivity
import com.example.hairhood.activities.MainActivity.Companion.prfPelu
import com.example.hairhood.fragments.Profile.Companion.desdeCliente
import com.example.hairhood.fragments.MoreInfoPelu.Companion.desdePelu
import com.example.hairhood.fragments.Profile.Companion.usuario
import com.example.hairhood.fragments.PerfilPeluquero.Companion.usuPelu
import java.security.MessageDigest

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

        var pasahitza = ""
        var nombre = ""
        var direccion = ""
        var dni = ""
        var email = ""
        var fecha = ""
        var foto = ""
        var tfno = 0
        var verificado = false

        if (desdeCliente) {

            db.collection("clientes").document(usuario).get().addOnSuccessListener {
                pasahitza = it.get("contraseña").toString()
                nombre = it.get("nombre").toString()
                direccion = it.get("direccion").toString()
                dni = it.get("dni").toString()
                email = it.get("email").toString()
                fecha = it.get("fechaNacimiento").toString()
                foto = it.get("foto").toString()
                val numTelf = it.get("numTelefono").toString()
                tfno = numTelf.toInt()
            }

        } else {
            if (desdePelu){

                db.collection("peluqueros").document(usuPelu).get().addOnSuccessListener {
                    pasahitza = it.get("contraseña").toString()
                    nombre = it.get("nombre").toString()
                    dni = it.get("dni").toString()
                    email = it.get("email").toString()
                    foto = it.get("foto").toString()
                    val numTelf = it.get("numTelefono").toString()
                    tfno = numTelf.toInt()
                    val verified = it.get("verificado").toString()
                    verificado = verified.toBoolean()
                }

            }
        }

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

        binding.btnGuardarContra.setOnClickListener {

            var contraActual = hashPassword(binding.editTextContraActual.text.toString())
            var nuevaContra = binding.editTextContraNueva.text.toString()
            var repiteNuevaContra = binding.editTextRepiteContra.text.toString()

            if (desdeCliente) {
                if (contraActual.equals(pasahitza)) {
                    if (nuevaContra.equals(repiteNuevaContra)) {
                        var nuevaContraseña = hashPassword(nuevaContra)
                        db.collection("clientes").document(usuario).set(
                            hashMapOf(
                                "contraseña" to nuevaContraseña,
                                "direccion" to direccion,
                                "dni" to dni,
                                "email" to email,
                                "fechaNacimiento" to fecha,
                                "foto" to foto,
                                "nombre" to nombre,
                                "numTelefono" to tfno,
                                "usuario" to usuario
                            )
                        )
                        binding.cardViewContra.visibility = View.GONE
                        val intent = Intent(this@ChangePwd.requireContext(), MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        binding.editTextContraNueva.error = getString(R.string.passwordsMustCoincide)
                        binding.editTextRepiteContra.error = getString(R.string.passwordsMustCoincide)
                    }
                } else {
                    binding.editTextContraActual.error = getString(R.string.wrongPassword)
                }
            } else {
                if (desdePelu) {
                    if (contraActual.equals(pasahitza)) {
                        if (nuevaContra.equals(repiteNuevaContra)) {
                            var nuevaContraseña = hashPassword(nuevaContra)
                            db.collection("peluqueros").document(usuPelu).set(
                                hashMapOf(
                                    "contraseña" to nuevaContraseña,
                                    "dni" to dni,
                                    "email" to email,
                                    "foto" to foto,
                                    "nombre" to nombre,
                                    "numTelefono" to tfno,
                                    "verificado" to verificado,
                                    "usuario" to usuPelu
                                )
                            )
                            binding.cardViewContra.visibility = View.GONE
                            val intent = Intent(this@ChangePwd.requireContext(), MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            binding.editTextContraNueva.error = getString(R.string.passwordsMustCoincide)
                            binding.editTextRepiteContra.error = getString(R.string.passwordsMustCoincide)
                        }
                    } else {
                        binding.editTextContraActual.error = getString(R.string.wrongPassword)
                    }
                }
            }
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

    private fun hashPassword(psswd: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(psswd.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {sb.append(String.format("%02X", b))}
        return sb.toString()
    }



}