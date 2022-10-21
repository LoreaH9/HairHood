package com.example.hairhood.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hairhood.activities.Peluqueros
import com.example.hairhood.activities.RvAdapter
import com.example.hairhood.activities.SelectorPeluquero
import com.example.hairhood.databinding.FragmentFavoriteBinding

class Favorite : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var PeluList:List<Peluqueros>

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
        cargarAsig()

        /*rvAdapter=RvAdapter(PeluList)
        binding.rv.adapter=rvAdapter
        return binding.root*/
        binding.rv.adapter=RvAdapter(PeluList){ pel ->
            val intent= Intent(activity, SelectorPeluquero::class.java)
            intent.putExtra(SelectorPeluquero.EXTRA_MOVIE, pel)
            startActivity(intent)
        }
        return binding.root
    }

    private fun cargarAsig(){
        PeluList= listOf(
            Peluqueros("Juan"),
            Peluqueros("Mikel"),
            Peluqueros("Gorka"),
            Peluqueros("Martin"),
            Peluqueros("Jorge"),
            Peluqueros("Luis"),
            Peluqueros("Paco"),
            Peluqueros("Ruben"),
            Peluqueros("Eneko"),
            Peluqueros("Imanol"),
            Peluqueros("Fernando"),
            Peluqueros("Javier"),
            Peluqueros("Markel"),
            Peluqueros("Miguel")
        )
    }

}