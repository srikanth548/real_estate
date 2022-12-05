package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPostAgricultureBinding

class PostAgricultureActivity : BaseActivity() {

    companion object {
        const val TYPE_AGRICULTURE_LAND = "TYPE_AGRICULTURE_LAND"
        const val TYPE_FARM_HOUSE = "TYPE_FARM_HOUSE"
    }

    private lateinit var binding: ActivityPostAgricultureBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostAgricultureBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);


        binding.etApproachRoad.autoCompleteSuggestions =
            arrayOf("Black Top(BT)", "Metal Road / CC Road", "Kacha Road", "No Road")

        binding.etFacing.autoCompleteSuggestions = arrayOf(
            "East",
            "West",
            "North",
            "South",
            "South East",
            "South West",
            "North East",
            "North West"
        )

        val type = TYPE_FARM_HOUSE
        if (type == TYPE_AGRICULTURE_LAND) {
            binding.clFarmHouseFields.visibility = View.GONE
        } else {
            binding.clFarmHouseFields.visibility = View.VISIBLE
        }

    }
}