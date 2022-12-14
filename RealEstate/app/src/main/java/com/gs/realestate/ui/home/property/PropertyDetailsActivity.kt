package com.gs.realestate.ui.home.property

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.databinding.ActivityPropertyDetailsBinding

class PropertyDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPropertyDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        buildStaticData()


    }

    private fun buildStaticData() {
        binding.imageSlider.setImagesList(
            listOf(
                "https://picsum.photos/200",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/seed/picsum/200/300"
            )
        )

        binding.tvPropertyName.text = "(TG2211000021) Farm House"
        binding.tvLocationDetails.text = "Nizampet,Hyderabad...."
        binding.tvExpiresIn.text = "Post expires in 19-Nov-2022"
        binding.tvPropertyPrice.text = "$ 100,00"
        binding.tvLikesCount.text = "15"
        binding.tvViewsCount.text = "26"


        binding.cGraph.loadGraphData(
            xAxisList = getDatesList(),
            viewsList = getValuesList(),
            favList = getFavList(),
            viewsName = "Views",
            favoritesName = "Fav"
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