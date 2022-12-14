package com.gs.realestate.util.graph

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.gs.realestate.R
import com.gs.realestate.databinding.LayoutCustomGraphBinding


class CustomLineGraph(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private var _binding: LayoutCustomGraphBinding? = null

    init {
        _binding =
            LayoutCustomGraphBinding.inflate(LayoutInflater.from(context), this, true)
    }


    /*
    * Method to load the graph information
    * */
    fun loadGraphData(
        xAxisList: List<String>,
        viewsList: List<Int>,
        favList: List<Int>,
        viewsName: String,
        favoritesName: String
    ) {
        //Part1
        val viewEntries = ArrayList<Entry>()
        val favEntries = ArrayList<Entry>()

        //Part2
        viewsList.forEachIndexed { index, value ->
            viewEntries.add(Entry(index.toFloat(), value.toFloat()))
        }
        favList.forEachIndexed { index, value ->
            favEntries.add(Entry(index.toFloat(), value.toFloat()))
        }

        //Part3
        val viewsLine = LineDataSet(viewEntries, viewsName)
        val favLine = LineDataSet(favEntries, favoritesName)


        //Part4
        viewsLine.apply {
            setDrawFilled(true)
            setDrawValues(true)
            lineWidth = 3f
            color = ContextCompat.getColor(context, R.color.blue)
            setCircleColor(ContextCompat.getColor(context, R.color.blue))
            setDrawFilled(false)
//            fillColor = R.color.gray
//            fillAlpha = R.color.blue
        }
        favLine.apply {
            setDrawFilled(true)
            setDrawValues(true)
            lineWidth = 3f
            color = ContextCompat.getColor(context, R.color.red)
            setCircleColor(ContextCompat.getColor(context, R.color.red))
            setDrawFilled(false)
//            fillColor = R.color.black
//            fillAlpha = R.color.quantum_pink
        }


        //Part5
        _binding?.llChart?.apply {
            xAxis.labelRotationAngle = 0f
            data = LineData(viewsLine, favLine)
            axisRight.isEnabled = false
            xAxis.mAxisMaximum = 1f
            setTouchEnabled(true)
            setPinchZoom(true)
            description.text = ""
            axisLeft.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = IndexAxisValueFormatter(xAxisList)
        }
    }
}