package com.example.hairhood.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivitySingleItemBinding

class RvAdapter(val PeluList:List<Peluqueros>):RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.activity_single_item,parent, false)
        return ViewHolder(view)
    }

    //asignacion de datos
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(PeluList[position])
    }

    //numero de elementos (la lista en este caso)
    override fun getItemCount(): Int {
        return PeluList.size
    }

    //override fun getItemCount(): Int = AsigList.size
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val binding=ActivitySingleItemBinding.bind(view)
        fun bind(asig:Peluqueros){
            binding.tv1.text=asig.nombre
        }
    }
}