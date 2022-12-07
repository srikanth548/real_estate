package com.gs.realestate.ui.post.properties

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gs.realestate.R
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPostAgricultureBinding
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast

class PostAgricultureActivity : BaseActivity() {

    private lateinit var binding: ActivityPostAgricultureBinding
    private var isLandType: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostAgricultureBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setDropDownData()

        intent.extras?.let {
            val selectedType = it.getString(Constants.EXTRA_PROPERTY_TYPE)
            if (selectedType.equals(getString(R.string.str_agricultural_land))) {
                isLandType = true
                binding.clFarmHouseFields.visibility = View.GONE
            } else {
                isLandType = false
                binding.clFarmHouseFields.visibility = View.VISIBLE
            }
        }

        binding.btnPost.setOnClickListener {
            if (isLandType) {
                if (validateAgricultureLandType()) {
                    Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (validateFarmHouseType()) {
                    Toast.makeText(this, "Valid data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setDropDownData() {
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
    }


    private fun validateAgricultureLandType(): Boolean {
        if (!binding.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.etExtent.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_enter_extent))
            return false
        } else if (!binding.etGuntas.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_guntas))
            return false
        } else if (!binding.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.etPrimaryContact.isValidText()) {
            showErrorMessage(getString(R.string.str_err_contact_number))
            return false
        }
        return true
    }

    private fun validateFarmHouseType(): Boolean {
        if (!binding.etVillage.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_village))
            return false
        } else if (!binding.etCity.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_city))
            return false
        } else if (!binding.etMandal.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_mandal))
            return false
        } else if (!binding.etState.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_select_state))
            return false
        } else if (!binding.etExtent.isValidText()) {
            showErrorMessage(getString(R.string.str_err_please_enter_extent))
            return false
        } else if (!binding.etGuntas.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_guntas))
            return false
        } else if (!binding.etConstructionType.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_construction_type))
            return false
        } else if (!binding.etConstructedPlotArea.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_construction_area))
            return false
        } else if (!binding.etFarmHousePlinthArea.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_farm_house_plinth_area))
            return false
        } else if (!binding.etApproachRoad.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_type_of_approach_road))
            return false
        } else if (!binding.etApproachRoadWidth.isValidText()) {
            showErrorMessage(getString(R.string.str_error_road_width))
            return false
        } else if (!binding.etFacing.isValidText()) {
            showErrorMessage(getString(R.string.str_error_enter_facing))
            return false
        } else if (!binding.etAgeOfFarmHouse.isValidText()) {
            showErrorMessage(getString(R.string.str_enter_age_of_farm_house))
            return false
        } else if (!binding.etOfferPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_enter_offer_price))
            return false
        } else if (!binding.etMarketPrice.isValidText()) {
            showErrorMessage(getString(R.string.str_err_market_price))
            return false
        } else if (!binding.etOwnerName.isValidText()) {
            showErrorMessage(getString(R.string.str_err_owner_name))
            return false
        } else if (!binding.etPrimaryContact.isValidText()) {
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