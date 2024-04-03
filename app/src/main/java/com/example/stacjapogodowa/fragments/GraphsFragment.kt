package com.example.stacjapogodowa.fragments

import ListItem
import TableAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stacjapogodowa.GraphsDialogFragment
import com.example.stacjapogodowa.Notes
import com.example.stacjapogodowa.NotesAdapter
import com.example.stacjapogodowa.R
import com.example.stacjapogodowa.databinding.FragmentGraphsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.database.*
import java.text.SimpleDateFormat


class GraphsFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var lastMeasurementID: String
    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var type: String
    private lateinit var test:TextView
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>
    private lateinit var arrayList: ArrayList<ListItem>

    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val bind = FragmentGraphsBinding.inflate(layoutInflater)
        val frag = GraphsFragment()
        bind.idTVHead.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, frag, GraphsFragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }

        }

        return bind.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeChart()

        recyclerView = view.findViewById(R.id.table)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        arrayList = arrayListOf<ListItem>()


        makeList()





        val settingsBnt = view.findViewById<LinearLayout>(R.id.graphSettings)
        settingsBnt.setOnClickListener {

            var dialog = GraphsDialogFragment()
            getActivity()?.supportFragmentManager?.let { it1 -> dialog.show(it1, "dialog") }

        }





    }



    private fun makeChart() {




        var allDataList = ArrayList<String>()
        var allTimeList = ArrayList<String>()
        var allValueList = ArrayList<Float>()

        database = FirebaseDatabase.getInstance().getReference("settings")
        database.child("graphs").get().addOnSuccessListener {

                var type =  it.child("type").value
                var startDate = it.child("minDate").value.toString()
                var endDate = it.child("maxDate").value.toString()
                var graphType = it.child("graphType").value.toString()

                val header = view?.findViewById<TextView>(R.id.idTVHead)!!
                if(type == "temperature" && graphType == "classic"){
                    header.text = "Wykres temperatury"
                }else if(type == "humidity" && graphType == "classic"){
                    header.text = "Wykres wilgotności"
                }else if(type == "pressure" && graphType == "classic"){
                    header.text = "Wykres ciśneinia"
                }else if(type == "temperature" && graphType == "mediana"){
                    header.text = "Wykres mediany temperatury"
                }else if(type == "humidity" && graphType == "mediana"){
                    header.text = "Wykres mediany wilgotności"
                }else if(type == "pressure" && graphType == "mediana"){
                    header.text = "Wykres mediany ciśneinia"
                }



            database = FirebaseDatabase.getInstance().getReference("measurement")
                database.orderByValue().addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(data: DataSnapshot) {
                       data.children.forEach{
                          val date =  it.child("date").value.toString()
                          val time =  it.child("time").value.toString()
                          val value =  it.child(type.toString()).value.toString().toFloat()
                           allDataList.add(date)
                           allValueList.add(value)
                           allTimeList.add(time)
                       }

                        var dataArray = ArrayList<String>()
                        var timeArray = ArrayList<String>()
                        var valueArray = ArrayList<Float>()

                        var formatter = SimpleDateFormat("dd.MM.yyyy")
                        val formattedStartDate = formatter.parse(startDate)
                        val formattedEndDate = formatter.parse(endDate)

                        var dataTimeArray = ArrayList<String>()

                        if(graphType == "classic"){
                            for(i in 0 .. allDataList.size - 1){
                                if((formatter.parse(allDataList.get(i)).after(formattedStartDate) || formatter.parse(allDataList.get(i)).equals(formattedStartDate))
                                    && (formatter.parse(allDataList.get(i)).before(formattedEndDate) || formatter.parse(allDataList.get(i)).equals(formattedEndDate) )){
                                    dataArray.add(allDataList.get(i))
                                    valueArray.add(allValueList.get(i))
                                    timeArray.add(allTimeList.get(i))
                                }

                                for (i in 0..dataArray.size - 1) {
                                    dataTimeArray.add(dataArray.get(i) + " " + timeArray.get(i))
                                }

                            }
                        }else if(graphType == "mediana"){

                            var findMin: Int = allValueList.get(0).toInt()
                            var findMax: Int = allValueList.get(0).toInt()
                            var labelArray = ArrayList<Int>()
                            var medianaDataArray = ArrayList<Int>()


                            for(i in 0 .. allDataList.size - 1){
                                if(allValueList.get(i).toInt() < findMin){
                                    findMin = allValueList.get(i).toInt()
                                }
                            }


                            for(i in 0 .. allDataList.size - 1){
                                if(allValueList.get(i).toInt() > findMax){
                                    findMax = allValueList.get(i).toInt()
                                }
                            }

                            for(i in findMin .. findMax){
                                labelArray.add(i)
                                medianaDataArray.add(0)
                            }

                            var pomArray = ArrayList<Int>()
                            for(i in 0 .. allValueList.size - 1){
                                pomArray.add(allValueList.get(i).toInt())
                            }

                            for (i in 0 .. pomArray.size - 1){
                                for(j in 0 ..medianaDataArray.size - 1){
                                    if(pomArray.get(i) == labelArray.get(j)){
                                        medianaDataArray.set(j, medianaDataArray.get(j) + 1)
                                    }
                                }
                            }

                            for(i in 0 .. medianaDataArray.size -1){
                                dataTimeArray.add(labelArray.get(i).toString())
                                valueArray.add(medianaDataArray.get(i).toFloat())
                            }






                        }

                        if(valueArray.size != 0) {
                            barChart = view?.findViewById(R.id.idBarChart)!!

                            barEntriesList = ArrayList()
                            for (i in 0..valueArray.size - 1) {
                                barEntriesList.add(BarEntry(i.toFloat(), valueArray.get(i).toFloat()))


                            }


                            barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")
                            barData = BarData(barDataSet)
                            barChart.data = barData
                            barDataSet.valueTextColor = Color.WHITE
                            barDataSet.setColor(resources.getColor(R.color.background))
                            barDataSet.valueTextSize = 10f
                            barChart.description.isEnabled = false
                            barChart.isDragEnabled = true
                            barChart.setVisibleXRangeMaximum(7f)
                            barChart.getAxisRight().setEnabled(false)
                            barChart.getAxisLeft().textColor = Color.WHITE

                            val leftAxis: YAxis = barChart.getAxisLeft()
                            leftAxis.setAxisMinValue((valueArray.min() - (valueArray.min() * 0.1)).toFloat())


                            val legend: Legend = barChart.getLegend()
                            legend.isEnabled = false

                            var xAxis = barChart.xAxis



                            xAxis.valueFormatter = IndexAxisValueFormatter(dataTimeArray)

                            xAxis.setCenterAxisLabels(false)
                            if(graphType == "classic") {
                                xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
                            }
                            xAxis.setGranularity(1f)
                            xAxis.textSize = 13f
                            xAxis.textColor = Color.WHITE
                            xAxis.setGranularityEnabled(true)

                            xAxis.labelRotationAngle = 90f

                        }else{
                            Toast.makeText(getActivity(), "brak danych do wyświetlenia", Toast.LENGTH_SHORT).show()
                        }


                   }




                    override fun onCancelled(error: DatabaseError) {

                    }

                })


        }


    }


    private fun makeList() {

        var allDataList = ArrayList<String>()
        var allTempList = ArrayList<String>()
        var allHumList = ArrayList<String>()
        var allPressList = ArrayList<String>()

        database = FirebaseDatabase.getInstance().getReference("settings")
        database.child("graphs").get().addOnSuccessListener {


            var startDate = it.child("minDate").value.toString()
            var endDate = it.child("maxDate").value.toString()



            database = FirebaseDatabase.getInstance().getReference("measurement")
            database.orderByValue().addValueEventListener(object: ValueEventListener{
                override fun onDataChange(data: DataSnapshot) {
                    data.children.forEach{
                        val date =  it.child("date").value.toString() + " " + it.child("time").value.toString()
                        val temp =  it.child("temperature").value.toString() + " °C"
                        val hum =  it.child("humidity").value.toString() + " %"
                        val press =  it.child("pressure").value.toString() + "hPa"
                        allDataList.add(date)
                        allTempList.add(temp)
                        allHumList.add(hum)
                        allPressList.add(press)
                    }

                    var dataArray = ArrayList<String>()
                    var tempArray = ArrayList<String>()
                    var humArray = ArrayList<String>()
                    var pressArray = ArrayList<String>()

                    var formatter = SimpleDateFormat("dd.MM.yyyy")
                    val formattedStartDate = formatter.parse(startDate)
                    val formattedEndDate = formatter.parse(endDate)


                    for(i in 0 .. allDataList.size - 1){
                        if((formatter.parse(allDataList.get(i)).after(formattedStartDate) || formatter.parse(allDataList.get(i)).equals(formattedStartDate)) && (formatter.parse(allDataList.get(i)).before(formattedEndDate) || formatter.parse(allDataList.get(i)).equals(formattedEndDate) )){
                            dataArray.add(allDataList.get(i))
                            tempArray.add(allTempList.get(i))
                            humArray.add(allHumList.get(i))
                            pressArray.add(allPressList.get(i))
                        }
                    }

                    for(i in 0 .. dataArray.size -1){
                        arrayList.add(ListItem(dataArray.get(i), tempArray.get(i),humArray.get(i), pressArray.get(i) ))
                    }
                    recyclerView.adapter = TableAdapter(arrayList)






                }




                override fun onCancelled(error: DatabaseError) {

                }

            })


        }


    }


}