package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostResidentialBinding
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast

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

        binding.btnPost.setOnClickListener {
            when (selectedType) {
                getString(R.string.str_plots) -> {
                    if (validatePlotType()) {
                        Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                    }
                }
                getString(R.string.str_flats) -> {
                    if (validateFlatType()) {
                        Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                    }
                }
                getString(R.string.str_villas_houses) -> {
                    if (validateHouseType()) {
                        Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                    }
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


    private fun validatePlotType(): Boolean {
        if (!binding.llPostPlot.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.llPostPlot.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.llPostPlot.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.llPostPlot.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.llPostPlot.etExtent.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_enter_extent))
            return false
        } else if (!binding.llPostPlot.etFacing.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_facing))
            return false
        } else if (!binding.llPostPlot.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.llPostPlot.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.llPostPlot.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.llPostPlot.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.llPostPlot.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.llPostPlot.etPrimaryContact.isValidText()) {
            showErrorMessage(getString(R.string.str_err_contact_number))
            return false
        }
        return true
    }


    private fun validateFlatType(): Boolean {
        if (!binding.llPostFlat.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.llPostFlat.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.llPostFlat.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.llPostFlat.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.llPostFlat.etFacing.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_facing))
            return false
        } else if (!binding.llPostFlat.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.llPostFlat.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.llPostFlat.etPlinthArea.isValidText()) {
            showErrorMessage(getString(R.string.str_please_enter_plinth_area))
            return false
        } else if (!binding.llPostFlat.etNoOfFloors.isValidText()) {
            showErrorMessage(getString(R.string.str_err_no_of_floors))
            return false
        } else if (!binding.llPostFlat.etFloorNumber.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_floor_number))
            return false
        } else if (!binding.llPostFlat.etNoOfBedRooms.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_no_of_bedrooms))
            return false
        } else if (!binding.llPostFlat.etNoOfBathrooms.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_no_of_bathrooms))
            return false
        } else if (!binding.llPostFlat.etNoOfCarParkings.isValidText()) {
            showErrorMessage(getString(R.string.str_err_car_parking))
            return false
        } else if (!binding.llPostFlat.etBuildingAge.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_building_age))
            return false
        } else if (!binding.llPostFlat.etApproaxRent.isValidText()) {
            showErrorMessage(getString(R.string.str_err_approax_rent))
            return false
        } else if (!binding.llPostFlat.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.llPostFlat.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.llPostFlat.etSuitableFor.isValidText()) {
            showErrorMessage(getString(R.string.str_err_suitable_for))
            return false
        } else if (!binding.llPostFlat.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.llPostFlat.etPrimaryContact.isValidText()) {
            showErrorMessage(getString(R.string.str_err_contact_number))
            return false
        }
        return true
    }


    private fun validateHouseType(): Boolean {
        if (!binding.llPostHouse.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.llPostHouse.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.llPostHouse.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.llPostHouse.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.llPostHouse.etFacing.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_facing))
            return false
        } else if (!binding.llPostHouse.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.llPostHouse.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.llPostHouse.etPlotArea.isValidText()) {
            showErrorMessage(getString(R.string.str_err_plot_area))
            return false
        } else if (!binding.llPostHouse.etPlinthArea.isValidText()) {
            showErrorMessage(getString(R.string.str_please_enter_plinth_area))
            return false
        } else if (!binding.llPostHouse.etNoOfFloors.isValidText()) {
            showErrorMessage(getString(R.string.str_err_no_of_floors))
            return false
        } else if (!binding.llPostHouse.etFloorNumber.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_floor_number))
            return false
        } else if (!binding.llPostHouse.etNoOfBedRooms.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_no_of_bedrooms))
            return false
        } else if (!binding.llPostHouse.etNoOfBathrooms.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_no_of_bathrooms))
            return false
        } else if (!binding.llPostHouse.etBuildingAge.isValidText()) {
            showErrorMessage(getString(R.string.str_err_age_of_building))
            return false
        } else if (!binding.llPostFlat.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.llPostFlat.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.llPostFlat.etSuitableFor.isValidText()) {
            showErrorMessage(getString(R.string.str_err_suitable_for))
            return false
        } else if (!binding.llPostFlat.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.llPostFlat.etPrimaryContact.isValidText()) {
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