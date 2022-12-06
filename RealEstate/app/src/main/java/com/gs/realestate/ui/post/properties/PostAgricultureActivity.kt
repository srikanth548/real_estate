package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import com.gs.realestate.R
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPostAgricultureBinding
import com.gs.realestate.util.Constants

class PostAgricultureActivity : BaseActivity() {

    private lateinit var binding: ActivityPostAgricultureBinding
    private var isLandType : Boolean = true

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

        intent.extras?.let {
            val selectedType = it.getString(Constants.EXTRA_PROPERTY_TYPE)
            if(selectedType.equals(getString(R.string.str_agricultural_land))){
                isLandType = true
                binding.clFarmHouseFields.visibility = View.GONE
            }else{
                isLandType = false
                binding.clFarmHouseFields.visibility = View.VISIBLE
            }
        }
    }
}