package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostCommercialBinding
import com.gs.realestate.util.Constants

class PostCommercialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostCommercialBinding
    private var selectedType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostCommercialBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.extras?.let {
            selectedType = it.getString(Constants.EXTRA_PROPERTY_TYPE) ?: ""

            when (selectedType) {
                getString(R.string.str_open_spaces) -> {
                    binding.llCommercialOpen.clRoot.visibility = View.VISIBLE
                    binding.llCOmmercialBuilding.clRoot.visibility = View.GONE
                }
                getString(R.string.str_buildings) -> {
                    binding.llCommercialOpen.clRoot.visibility = View.GONE
                    binding.llCOmmercialBuilding.clRoot.visibility = View.VISIBLE
                }
            }
        }

        setDropDownData()
    }

    private fun setDropDownData() {
        val roadsArray =
            arrayOf("Black Top(BT)", "Metal Road / CC Road", "Kacha Road", "No Road")

        binding.llCommercialOpen.etApproachRoad.autoCompleteSuggestions = roadsArray
        binding.llCOmmercialBuilding.etApproachRoad.autoCompleteSuggestions = roadsArray
    }
}