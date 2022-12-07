package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostResidentialBinding
import com.gs.realestate.util.Constants

class PostResidentialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostResidentialBinding
    var selectedType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostResidentialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        loadDropDowns()


        intent.extras?.let {
            selectedType = it.getString(Constants.EXTRA_PROPERTY_TYPE) ?: ""

            when (selectedType) {
                getString(R.string.str_plots) -> {
                    binding.llPostPlot.clRoot.visibility = View.VISIBLE
                    binding.llPostFlat.clRoot.visibility = View.GONE
                    binding.llPostHouse.clRoot.visibility = View.GONE
                }
                getString(R.string.str_flats) -> {
                    binding.llPostPlot.clRoot.visibility = View.GONE
                    binding.llPostFlat.clRoot.visibility = View.VISIBLE
                    binding.llPostHouse.clRoot.visibility = View.GONE
                }
                getString(R.string.str_villas_houses) -> {
                    binding.llPostPlot.clRoot.visibility = View.GONE
                    binding.llPostFlat.clRoot.visibility = View.GONE
                    binding.llPostHouse.clRoot.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadDropDowns() {
        val roadsArray =
            arrayOf("Black Top(BT)", "Metal Road / CC Road", "Kacha Road", "No Road")

        val facingArray = arrayOf(
            "East",
            "West",
            "North",
            "South",
            "South East",
            "South West",
            "North East",
            "North West"
        )

        binding.llPostPlot.etApproachRoad.autoCompleteSuggestions = roadsArray
        binding.llPostPlot.etFacing.autoCompleteSuggestions = facingArray

        binding.llPostFlat.etApproachRoad.autoCompleteSuggestions = roadsArray
        binding.llPostFlat.etFacing.autoCompleteSuggestions = facingArray

        binding.llPostHouse.etApproachRoad.autoCompleteSuggestions = roadsArray
        binding.llPostHouse.etFacing.autoCompleteSuggestions = facingArray
    }

}