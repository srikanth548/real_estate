package com.gs.realestate.ui.home.property

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.databinding.ActivityPropertyDetailsBinding
import com.gs.realestate.network.Details
import com.gs.realestate.network.Properties
import com.gs.realestate.network.Series

class PropertyDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPropertyDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        intent.extras?.let {
            if(it.containsKey("EXTRA_PROPERTY_DETAILS")){
                it.getParcelable<Properties>("EXTRA_PROPERTY_DETAILS")?.let {propertyDetails ->
//                    loadData(propertyDetails)
                }
            }
        }

        loadData(getDummyData())
    }

    private fun loadData(propertyDetails: Properties) {
        binding.imageSlider.setImagesList(
            listOf(
                propertyDetails.imageUrl ?: ""
            )
        )

        binding.tvPropertyName.text = propertyDetails.propertyType
        binding.tvLocationDetails.text = propertyDetails.location
        binding.tvExpiresIn.text = "Post expires in ${propertyDetails.expiryDate}"
        binding.tvPropertyPrice.text =
            "${propertyDetails.details?.get(1)?.value}  ${propertyDetails.details?.get(1)?.unit}"
        binding.tvLikesCount.text = propertyDetails.favCount.toString()
        binding.tvViewsCount.text = propertyDetails.viewCount.toString()


        binding.cGraph.loadGraphData(
            xAxisList = propertyDetails.categories ?: listOf(),
            viewsList = propertyDetails.series?.get(0)?.data ?: listOf(),
            favList = propertyDetails.series?.get(1)?.data ?: listOf(),
            viewsName = propertyDetails.series?.get(0)?.name ?: "",
            favoritesName = propertyDetails.series?.get(1)?.name ?: ""
        )
    }


    private fun getDummyData(): Properties {
        return Properties(
            propertyid = 0,
            imageUrl = "https://picsum.photos/200",
            propertyType = "(TG2211000021) Farm House",
            details = listOf(
                Details(
                    value = "12.00",
                    unit = "Acres"
                ),
                Details(
                    value = "₹20.00",
                    unit = "Lakhs"
                ),
                Details(
                    value = null,
                    unit = "km  from ORR"
                ),
                Details(
                    value = null,
                    unit = "km  from Hyd"
                )
            ),
            location = "Nizampet,Hyderabad....",
            viewCount = 10,
            favCount = 3,
            commentCount = 6,
            expiryDate = "19-Nov-2022",
            categories = getDatesList(),
            series = listOf(
                Series(
                    name = "Views",
                    data = getValuesList()
                ),
                Series(
                    name = "Fav",
                    data = getFavList()
                )
            )
        )
    }

    private fun getFavList(): List<Int> {
        return listOf(
            1,
            4,
            2,
            1,
            3,
            10,
            12,
            9,
            8
        )
    }

    private fun getDatesList(): List<String> {
        return listOf(
            "01-01-2022",
            "15-01-2022",
            "29-01-2022",
            "07-02-2022",
            "24-02-2022",
            "28-02-2022",
            "09-03-2022",
            "11-03-2022",
            "13-03-2022"
        )
    }

    private fun getValuesList(): List<Int> {
        return listOf(
            5,
            10,
            8,
            6,
            16,
            22,
            10,
            18,
            2
        )
    }
}