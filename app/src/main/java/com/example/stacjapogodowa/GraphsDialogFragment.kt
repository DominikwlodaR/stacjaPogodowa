package com.example.stacjapogodowa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.stacjapogodowa.databinding.ActivityMainBinding
import com.example.stacjapogodowa.fragments.GraphsFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class GraphsDialogFragment: DialogFragment() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var graph1bnt:  TextView
    private lateinit var database: DatabaseReference
    private lateinit var radioGroup: RadioGroup
    private lateinit var typeRadioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var typeRadioButton: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_fragment, container, false)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        graph1bnt = view.findViewById(R.id.graph1bnt)


        graph1bnt.setOnClickListener {
            var startDate:EditText = view.findViewById(R.id.startDate)
            var endDate:EditText = view.findViewById(R.id.endDate)
            var pattern = Pattern.compile("^[0-3]?[0-9].[0-3]?[0-9].(?:[0-9]{2})?[0-9]{2}$");



            if(pattern.matcher(startDate.text.toString()).matches() && pattern.matcher(endDate.text.toString()).matches() ){

                var formatter = SimpleDateFormat("dd.MM.yyyy")
                val date1 = formatter.parse(startDate.text.toString())
                val date2 = formatter.parse(endDate.text.toString())

                val cmp = date1.compareTo(date2)
                when {
                    cmp > 0 -> {
                        Toast.makeText( getActivity(), "Data początkowa nie może być wcześniejsza niż data końcowa", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        database = FirebaseDatabase.getInstance().getReference("settings")
                        database.child("graphs").child("minDate").setValue(startDate.text.toString())
                        database.child("graphs").child("maxDate").setValue(endDate.text.toString())
                        radioGroup = view.findViewById(R.id.radioGroupStat)
                        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                        radioButton = view.findViewById(selectedOption)
                        var radioText = radioButton.text
                        if(radioText.toString() == "wilgotność") {
                            database.child("graphs").child("type").setValue("humidity")
                        }else if(radioText.toString() == "temperatura") {
                            database.child("graphs").child("type").setValue("temperature")
                        }else if(radioText.toString() == "ciśnienie") {
                            database.child("graphs").child("type").setValue("pressure")
                        }

                        typeRadioGroup = view.findViewById(R.id.radioGroupType)
                        val selectedOption1: Int = typeRadioGroup!!.checkedRadioButtonId
                        typeRadioButton = view.findViewById(selectedOption1)

                        var radioText2 = typeRadioButton.text

                       if(radioText2.toString() == "klasyczny wyres słupkowy"){
                           database.child("graphs").child("graphType").setValue("classic")
                           Toast.makeText(getActivity(), "klasyka", Toast.LENGTH_SHORT).show()
                       }else if(radioText2.toString() == "wykres ilości wystapień wartości"){
                           database.child("graphs").child("graphType").setValue("mediana")
                       }else{
                           database.child("graphs").child("graphType").setValue("nie działa")

                       }



                        dismiss()
                    }
                }


            }else{
                Toast.makeText( getActivity(), "Błędny format daty. Data powinna być w formacie dd.mm.rrrr", Toast.LENGTH_SHORT).show()
            }

        }




    }




}