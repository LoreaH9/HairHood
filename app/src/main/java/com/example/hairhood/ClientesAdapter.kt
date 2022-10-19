package com.example.hairhood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hairhood.model.User


class UserAdapter(private var userArrayList: MutableList<User?>, private val listener:(User)->Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
            val v: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.table_row_layout, viewGroup, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
            viewHolder.dni.text = userArrayList[i]?.dni
            viewHolder.username.text = userArrayList[i]?.usuario
            viewHolder.type.text = userArrayList[i]?.tipo
            viewHolder.username.setOnClickListener {
                userArrayList[i]?.let { it1 -> listener(it1) }
            }

        }

        override fun getItemCount(): Int {
            return userArrayList.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val dni: TextView = itemView.findViewById(R.id.tv_user_dni)
            val username: TextView = itemView.findViewById(R.id.tv_user_username)
            val type: TextView = itemView.findViewById(R.id.tv_user_type)
        }
    }


