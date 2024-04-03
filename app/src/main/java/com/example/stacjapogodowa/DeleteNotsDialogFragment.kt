package com.example.stacjapogodowa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteNotsDialogFragment : DialogFragment() {
    private lateinit var graph1bnt: TextView
    private lateinit var database: DatabaseReference
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_delete_nots_dialog, container, false)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        val confirmBnt = view.findViewById<LinearLayout>(R.id.deleteBnt)

        confirmBnt.setOnClickListener {
            if (checkBox.isChecked) {
                database = FirebaseDatabase.getInstance().getReference("notifications")
                database.removeValue()
                Toast.makeText(getActivity(), "Usnęto zawartość bazy pomiarów!", Toast.LENGTH_SHORT).show()
                dismiss()

            }else{
                Toast.makeText(getActivity(), "Brak potwierdzenia operacji. Nie usunięto danych!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }




    }
}