package com.example.hairhood.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hairhood.activities.LoginActivity
import com.example.hairhood.activities.Peluqueros
import com.example.hairhood.activities.RvAdapter
import com.example.hairhood.activities.SelectorPeluquero
import com.example.hairhood.databinding.FragmentFavoriteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Favorite : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var PeluList: ArrayList<Peluqueros>
    private val db = Firebase.firestore
    lateinit var sharedPreferences: SharedPreferences


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

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)


        /*rvAdapter=RvAdapter(PeluList)
        binding.rv.adapter=rvAdapter
        return binding.root*/
        PeluList = ArrayList()

        db.collection("favoritos")
            .whereEqualTo("usuarioCliente", sharedPreferences.getString(USER_KEY, ""))
            .get()
            .addOnSuccessListener { list ->
                list.forEach { peluquero ->
                    if (peluquero.data["usuarioPelu"].toString().isNotEmpty()) {
                        // Toast.makeText(requireContext(), peluquero.data["usuarioPelu"].toString(), Toast.LENGTH_SHORT).show()
                        db.collection("peluqueros")
                            .document(peluquero.data["usuarioPelu"].toString())
                            .get()
                            .addOnSuccessListener {
                                PeluList.add(Peluqueros(it.data!!["usuario"].toString(), it.data!!["foto"].toString()))

                                binding.rv.adapter = RvAdapter(PeluList) { pel ->

                                    val intent= Intent(activity, SelectorPeluquero::class.java)
                                    intent.putExtra("usuario", pel.nombre)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
            }

        return binding.root
    }


}