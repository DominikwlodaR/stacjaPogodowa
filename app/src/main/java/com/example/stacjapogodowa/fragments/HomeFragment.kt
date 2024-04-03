package com.example.stacjapogodowa.fragments

import android.os.Bundle
import android.text.TextUtils.substring
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.stacjapogodowa.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Subst


class HomeFragment : Fragment() {

    private lateinit var  lastMeasurementID: String
    private lateinit var database: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("settings")
        database.child("lastMeasurementID").get().addOnSuccessListener {

            if(it.exists()){
                lastMeasurementID = it.value.toString()


            }

            setBannerData(lastMeasurementID)
            changeImage(lastMeasurementID)
            lastMeasurementData(lastMeasurementID)
            lastHoursMeasurementData(lastMeasurementID)
            averageValues(lastMeasurementID)
            
        }

    }

    private fun averageValues(lastMeasurementID: String) {
        var avgereTemp = 0F
        var averageHum = 0F
        var averagePress = 0F
        var currentDate: String
        var counter = 0
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(lastMeasurementID).get().addOnSuccessListener {
            if (it.exists()) {
                currentDate = it.child("date").value.toString()

                for(id in 1..lastMeasurementID.toInt()) {
                    database.child(id.toString()).get().addOnSuccessListener {
                        if (it.exists()) {
                            var nextDate = it.child("date").value.toString()
                            if (nextDate == currentDate) {
                                counter++
                                avgereTemp += it.child("temperature").value.toString().toFloat()
                                averageHum += it.child("humidity").value.toString().toFloat()
                                averagePress += it.child("pressure").value.toString().toFloat()
                            }
                        }

                        val temp = view?.findViewById<TextView>(R.id.averageTemperature )!!
                        var finalVauleTemp = avgereTemp/counter
                        val roundTemp = Math.round(finalVauleTemp * 100.0) / 100.0
                        temp.text = roundTemp.toString() + "°C"

                        val hum = view?.findViewById<TextView>(R.id.averageHumidity )!!
                        var finalValueHum = averageHum/counter
                        val roundHum:Double =  Math.round(finalValueHum * 100.0) / 100.0
                        hum.text = roundHum.toString() + "%"

                        val press = view?.findViewById<TextView>(R.id.averagePressure )!!
                        var finalValuePress= (averagePress/counter).toInt()
                        press.text = finalValuePress.toString() + "hPa"

                    }

                }
            }
        }


    }

    private fun lastHoursMeasurementData(ID: String) {
        var num = ID.toInt() - 1
        var previousID = num.toString()
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(previousID).get().addOnSuccessListener {
            if(it.exists()){
                val temperature1 = it.child("temperature").value
                val humidity1 = it.child("humidity").value
                val pressure1 = it.child("pressure").value
                val time = it.child("time").value

                val temperatureCont = view?.findViewById<TextView>(R.id. infoTemp1)!!
                val humidityCont = view?.findViewById<TextView>(R.id.infoHumidity1 )!!
                val pressureCont = view?.findViewById<TextView>(R.id.infoPressure1 )!!
                val timeCont = view?.findViewById<TextView>(R.id.infoHour1 )!!



                temperatureCont.text = temperature1.toString() + "°C"
                humidityCont.text = humidity1.toString() +"%"
                pressureCont.text = pressure1.toString() + "hPa"
                timeCont.text = time.toString()




            }
        }

        num--
        previousID = num.toString()
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(previousID).get().addOnSuccessListener {
            if(it.exists()){
                val temperature1 = it.child("temperature").value
                val humidity1 = it.child("humidity").value
                val pressure1 = it.child("pressure").value
                val time = it.child("time").value

                val temperatureCont = view?.findViewById<TextView>(R.id. infoTemp2)!!
                val humidityCont = view?.findViewById<TextView>(R.id.infoHumidity2 )!!
                val pressureCont = view?.findViewById<TextView>(R.id.infoPressure2 )!!
                val timeCont = view?.findViewById<TextView>(R.id.infoHour2)!!



                temperatureCont.text = temperature1.toString() + "°C"
                humidityCont.text = humidity1.toString() +"%"
                pressureCont.text = pressure1.toString() + "hPa"
                timeCont.text = time.toString()
            }
        }


        num--
        previousID = num.toString()
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(previousID).get().addOnSuccessListener {
            if(it.exists()){
                val temperature1 = it.child("temperature").value
                val humidity1 = it.child("humidity").value
                val pressure1 = it.child("pressure").value
                val time = it.child("time").value

                val temperatureCont = view?.findViewById<TextView>(R.id. infoTemp3)!!
                val humidityCont = view?.findViewById<TextView>(R.id.infoHumidity3 )!!
                val pressureCont = view?.findViewById<TextView>(R.id.infoPressure3 )!!
                val timeCont = view?.findViewById<TextView>(R.id.infoHour3)!!



                temperatureCont.text = temperature1.toString() + "°C"
                humidityCont.text = humidity1.toString() +"%"
                pressureCont.text = pressure1.toString() + "hPa"
                timeCont.text = time.toString()
            }
        }

        num--
        previousID = num.toString()
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(previousID).get().addOnSuccessListener {
            if(it.exists()){
                val temperature1 = it.child("temperature").value
                val humidity1 = it.child("humidity").value
                val pressure1 = it.child("pressure").value
                val time = it.child("time").value

                val temperatureCont = view?.findViewById<TextView>(R.id. infoTemp4)!!
                val humidityCont = view?.findViewById<TextView>(R.id.infoHumidity4 )!!
                val pressureCont = view?.findViewById<TextView>(R.id.infoPressure4 )!!
                val timeCont = view?.findViewById<TextView>(R.id.infoHour4)!!



                temperatureCont.text = temperature1.toString() + "°C"
                humidityCont.text = humidity1.toString() +"%"
                pressureCont.text = pressure1.toString() + "hPa"
                timeCont.text = time.toString()
            }
        }


        num--
        previousID = num.toString()
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(previousID).get().addOnSuccessListener {
            if(it.exists()){
                val temperature1 = it.child("temperature").value
                val humidity1 = it.child("humidity").value
                val pressure1 = it.child("pressure").value
                val time = it.child("time").value



                val temperatureCont = view?.findViewById<TextView>(R.id. infoTemp5)!!
                val humidityCont = view?.findViewById<TextView>(R.id.infoHumidity5 )!!
                val pressureCont = view?.findViewById<TextView>(R.id.infoPressure5 )!!
                val timeCont = view?.findViewById<TextView>(R.id.infoHour5)!!



                temperatureCont.text = temperature1.toString() + "°C"
                humidityCont.text = humidity1.toString() +"%"
                pressureCont.text = pressure1.toString() + "hPa"
                timeCont.text = time.toString()
            }
        }


    }

    private fun lastMeasurementData(ID: String) {
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(ID).get().addOnSuccessListener {
            if(it.exists()){
                val actualTemperature = it.child("temperature").value
                val actualHumidity = it.child("humidity").value
                val actualPressure= it.child("pressure").value
                val actualDate= it.child("date").value
                val actualTime= it.child("time").value


                val currentTemperature = view?.findViewById<TextView>(R.id.currentTemperature)!!
                val currentHumidity = view?.findViewById<TextView>(R.id.currentHumidity)!!
                val currentPressure = view?.findViewById<TextView>(R.id.currentPressure)!!
                val currentDate = view?.findViewById<TextView>(R.id.currentDate)!!

                currentTemperature.text = actualTemperature.toString() + "°C"
                currentHumidity.text = actualHumidity.toString() +"%"
                currentPressure.text = actualPressure.toString() + "hPa"

                currentDate.text =  "Data i godzina pomiaru: " + actualDate +", " + actualTime




            }

        }
    }

    private fun changeImage(ID:String) {
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(ID).get().addOnSuccessListener {
            if (it.exists()) {
                val time = it.child("time").value.toString().subSequence(0,2)

                val img =view?.findViewById<ImageView>(R.id.image)!!
                if(time == "06" || time == "07" || time == "08" || time == "09" || time == "10" || time == "11" || time == "12"|| time == "13"|| time == "14"
                    || time == "15" || time == "16" || time == "17" || time == "08") {
                  img.setImageResource(R.drawable.sun)
                }
            }
        }
    }

    private fun setBannerData(ID:String) {
        database = FirebaseDatabase.getInstance().getReference("measurement")
        database.child(ID).get().addOnSuccessListener {
            if(it.exists()){
                val timeHeader  = it.child("time").value
                val dateHeader  = it.child("date").value
                val temperatureHeader = it.child("temperature").value
                val dateBanner = view?.findViewById<TextView>(R.id.stHour)!!
                dateBanner.text = dateHeader.toString()

                val timeBanner = view?.findViewById<TextView>(R.id.stDate)!!
                var pom: String = substring(timeHeader as CharSequence?, 3, 4)
                timeBanner.text = timeHeader.toString()



                val tempBanner = view?.findViewById<TextView>(R.id.bannerTemp)!!
                tempBanner.text = temperatureHeader.toString() +  "°C"
            }
        }
    }


}