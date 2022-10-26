package com.example.hairhood.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.R
import com.example.hairhood.databinding.ActivitySingleItemBinding

class RvAdapter(val PeluList:List<Peluqueros>, private val listener:(Peluqueros)->Unit):RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.activity_single_item,parent, false)
        return ViewHolder(view)
    }

    //asignacion de datos
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*holder.bind(PeluList[position])
        holder.itemView.setOnClickListener{
            listener(PeluList)
        }*/
        val pel=PeluList[position]
        holder.bind(pel)
        holder.itemView.setOnClickListener{
            listener(pel)
        }
    }

    //numero de elementos (la lista en este caso)
    /* override fun getItemCount(): Int {
         return PeluList.size
     }*/
    override fun getItemCount(): Int = PeluList.size
    //override fun getItemCount(): Int = AsigList.size
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val binding=ActivitySingleItemBinding.bind(view)
        fun bind(pelu:Peluqueros){
            binding.tv1.text=pelu.nombre
        }
    }
}

