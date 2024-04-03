package com.example.stacjapogodowa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stacjapogodowa.DeleteDialogFragment
import com.example.stacjapogodowa.DeleteNotsDialogFragment
import com.example.stacjapogodowa.GraphsDialogFragment
import com.example.stacjapogodowa.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SettingsFragment : Fragment() {

    private lateinit var timeIntervalBnt: LinearLayout
    private lateinit var radioButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var database: DatabaseReference
    private lateinit var notificationBnt: LinearLayout
    private lateinit var checkboxTemperature: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteBnt = view.findViewById<LinearLayout>(R.id.delete_button)
        deleteBnt.setOnClickListener {

            var dialog = DeleteDialogFragment()
            getActivity()?.supportFragmentManager?.let { it1 -> dialog.show(it1, "dialog") }

        }

        val deleteBnt2 = view.findViewById<LinearLayout>(R.id.delete_nots_button)
        deleteBnt2.setOnClickListener {

            var dialog = DeleteNotsDialogFragment()
            getActivity()?.supportFragmentManager?.let { it1 -> dialog.show(it1, "dialog") }

        }



        radioGroup = view.findViewById(R.id.radioGroup)
        timeIntervalBnt = view.findViewById(R.id.timeIntervalBnt)

        timeIntervalBnt.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            radioButton = view.findViewById(selectedOption)
            var radioText = radioButton.text.subSequence(0, 2).toString()
            database = FirebaseDatabase.getInstance().getReference("settings")
            database.child("timeInterval").setValue(radioText.toInt())


            Toast.makeText(getActivity(), "Interwał czasowy ustawiony na " + radioText + " min", Toast.LENGTH_SHORT).show()
        }

        notificationBnt = view.findViewById(R.id.notificationsBnt)
        var checkboxTemperature   = view.findViewById<CheckBox>(R.id.checkboxTemperature)
        var checkboxHumidity   = view.findViewById<CheckBox>(R.id.checkboxHumidity)
        var checkboxPressure   = view.findViewById<CheckBox>(R.id.checkboxPressure)

        var minTempInput = view.findViewById<EditText>(R.id.minTempInput)
        var maxTempInput = view.findViewById<EditText>(R.id.maxTempInput)
        var minHumInput = view.findViewById<EditText>(R.id.minHumInput)
        var maxHumInput = view.findViewById<EditText>(R.id.maxHumInput)
        var minPressInput = view.findViewById<EditText>(R.id.minPressInput)
        var maxPressInput = view.findViewById<EditText>(R.id.maxPressInput)

        database = FirebaseDatabase.getInstance().getReference("settings")

        database.child("temperatureNotifications").get().addOnSuccessListener {
            if(it.exists()){
                if(it.child("enable").value == "true"){
                    checkboxTemperature!!.isChecked=true
                }

                minTempInput.setText(it.child("maxValue").value.toString())
                maxTempInput.setText(it.child("minValue").value.toString())

            }
        }

        database.child("humidityNotifications").get().addOnSuccessListener {
            if(it.exists()){
                if(it.child("enable").value == "true"){
                    checkboxHumidity!!.isChecked=true
                }

                minHumInput.setText(it.child("maxValue").value.toString())
                maxHumInput.setText(it.child("minValue").value.toString())
            }
        }

        database.child("pressureNotifications").get().addOnSuccessListener {
            if(it.exists()){
                if(it.child("enable").value == "true"){
                    checkboxPressure!!.isChecked=true
                }

                minPressInput.setText(it.child("maxValue").value.toString())
                maxPressInput.setText(it.child("minValue").value.toString())
            }
        }





        notificationBnt.setOnClickListener {


            if(!checkboxTemperature.isChecked){
                database.child("temperatureNotifications").child("enable").setValue("false")
            }

            if(!checkboxHumidity.isChecked){
                database.child("humidityNotifications").child("enable").setValue("false")
            }

            if(!checkboxPressure.isChecked){
                database.child("pressureNotifications").child("enable").setValue("false")
            }

            if(checkboxTemperature.isChecked){
                database.child("temperatureNotifications").child("enable").setValue("true")


                if(minTempInput.text.toString() != ""){
                    database.child("temperatureNotifications").child("minValue").setValue(minTempInput.text.toString() )
                }

                if(maxTempInput.text.toString() != ""){
                    database.child("temperatureNotifications").child("maxValue").setValue(maxTempInput.text.toString() )
                }

            }

            if(checkboxHumidity.isChecked){
                database.child("humidityNotifications").child("enable").setValue("true")


                if(minHumInput.text.toString() != ""){
                    database.child("humidityNotifications").child("minValue").setValue(minHumInput.text.toString() )
                }

                if(maxHumInput.text.toString() != ""){
                    database.child("humidityNotifications").child("maxValue").setValue(maxHumInput.text.toString() )
                }
            }

            if(checkboxPressure.isChecked){
                database.child("pressureNotifications").child("enable").setValue("true")

                if(minPressInput.text.toString() != ""){
                    database.child("pressureNotifications").child("minValue").setValue(minPressInput.text.toString() )
                }

                if(maxPressInput.text.toString() != ""){
                    database.child("pressureNotifications").child("maxValue").setValue(maxPressInput.text.toString() )
                }
            }


            Toast.makeText(getActivity(), "Zapisano ustawienia powiadomień!", Toast.LENGTH_SHORT).show()


        }



    }
}


