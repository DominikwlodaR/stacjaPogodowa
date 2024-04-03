package com.example.stacjapogodowa.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.stacjapogodowa.Notes
import com.example.stacjapogodowa.NotesAdapter

import com.example.stacjapogodowa.R
import com.google.firebase.database.*


class NotificationsFragment : Fragment() {

    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Notes>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.notsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        arrayList = arrayListOf<Notes>()
        getNotes()



    }

    private fun getNotes() {
        reference = FirebaseDatabase.getInstance().getReference("notifications")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   for(noteSnapshot in snapshot.children){
                       val note = noteSnapshot.getValue(Notes::class.java)
                       arrayList.add(note!!)
                   }

                   recyclerView.adapter = NotesAdapter(arrayList)
               }
            }

           override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}