package com.example. stacjapogodowa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stacjapogodowa.Notes
import com.example.stacjapogodowa.R


class NotesAdapter(private val notsList : ArrayList<Notes>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nots_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notsList[position]
        holder.dateTime.text  = currentItem.date + ", " + currentItem.time
        holder.message.text  = currentItem.type
        holder.noteValue.text  = currentItem.value
    }

    override fun getItemCount(): Int {
        return notsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateTime : TextView = itemView.findViewById(R.id.noteDate)
        val message : TextView = itemView.findViewById(R.id.noteMessage)
        val noteValue : TextView = itemView.findViewById(R.id.noteValue)
    }

}