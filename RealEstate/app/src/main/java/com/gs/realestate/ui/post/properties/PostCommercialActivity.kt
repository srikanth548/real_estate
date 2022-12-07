package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostCommercialBinding
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast

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

        binding.btnPost.setOnClickListener {
            when (selectedType) {
                getString(R.string.str_open_spaces) -> {
                    if (validateOpenSpaceType()) {
                        Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                    }
                }
                getString(R.string.str_buildings) -> {
                    if (validateBuildingType()) {
                        Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setDropDownData() {
        val roadsArray =
            arrayOf("Black Top(BT)", "Metal Road / CC Road", "Kacha Road", "No Road")

        binding.llCommercialOpen.etApproachRoad.autoCompleteSuggestions = roadsArray
        binding.llCOmmercialBuilding.etApproachRoad.autoCompleteSuggestions = roadsArray
    }

    private fun validateOpenSpaceType(): Boolean {
        if (!binding.llCommercialOpen.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.llCommercialOpen.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.llCommercialOpen.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.llCommercialOpen.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.llCommercialOpen.etExtent.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_enter_extent))
            return false
        } else if (!binding.llCommercialOpen.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.llCommercialOpen.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.llCommercialOpen.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.llCommercialOpen.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.llCommercialOpen.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.llCommercialOpen.etPrimaryContact.isValidText()) {
            showErrorMessage(getString(R.string.str_err_contact_number))
            return false
        }
        return true
    }

    private fun validateBuildingType(): Boolean {
        if (!binding.llCOmmercialBuilding.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.llCOmmercialBuilding.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.llCOmmercialBuilding.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.llCOmmercialBuilding.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.llCOmmercialBuilding.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.llCOmmercialBuilding.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.llCOmmercialBuilding.etPlotArea.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_plot_area))
            return false
        } else if (!binding.llCOmmercialBuilding.etConstructedArea.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_constructed_area))
            return false
        } else if (!binding.llCOmmercialBuilding.etNoOfFloors.isValidText()) {
            showErrorMessage(getString(R.string.str_err_no_of_floors))
            return false
        } else if (!binding.llCOmmercialBuilding.etAgeOfBuilding.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_building_age))
            return false
        } else if (!binding.llCOmmercialBuilding.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.llCOmmercialBuilding.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.llCOmmercialBuilding.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.llCOmmercialBuilding.etPrimaryContact.isValidText()) {
            showErrorMessage(getString(R.string.str_err_contact_number))
            return false
        }
        return true
    }

    private fun showErrorMessage(message: String) {
        SnackBarToast.showErrorSnackBar(
            binding.root,
            message
        )
    }
}