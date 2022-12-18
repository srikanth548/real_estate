package com.gs.realestate.ui.post.properties

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gs.realestate.R
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPostAgricultureBinding
import com.gs.realestate.network.models.property.PostAgricultureRequest
import com.gs.realestate.network.models.property.UnitDetails
import com.gs.realestate.ui.post.PostHighlightActivity
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast

class PostAgricultureActivity : BaseActivity() {

    private lateinit var binding: ActivityPostAgricultureBinding
    private var isLandType: Boolean = true

    private var postAgricultureRequest: PostAgricultureRequest? = null

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

            postAgricultureRequest = it.getParcelable(Constants.EXTRA_POST_PROPERTY_REQUEST)
        }

        binding.btnPost.setOnClickListener {
            if (isLandType) {
                if (validateAgricultureLandType()) {
                    val landObj = prepareAgricultureLandObject()
                    val intent =
                        Intent(this@PostAgricultureActivity, PostHighlightActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PROPERTY_CATEGORY, Constants.EXTRA_AGRICULTURE)
                    intent.putExtra(Constants.EXTRA_POST_PROPERTY_REQUEST, landObj)
                    startActivity(intent)
                }
            } else {
                if (validateFarmHouseType()) {
                    val farmHouseObj = prepareFarmHouseObject()
                    val intent =
                        Intent(this@PostAgricultureActivity, PostHighlightActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PROPERTY_CATEGORY, Constants.EXTRA_AGRICULTURE)
                    intent.putExtra(Constants.EXTRA_POST_PROPERTY_REQUEST, farmHouseObj)
                    startActivity(intent)
                }
            }
        }
    }

    private fun prepareFarmHouseObject(): PostAgricultureRequest? {
        return postAgricultureRequest?.apply {
            villageName = binding.etVillage.getText()
            cityName = binding.etCity.getText()
            mandalName = binding.etMandal.getText()
            districtName = binding.etDistrict.getText()
            stateName = binding.etState.getText()
            extentInAcre = binding.etExtent.getText()
            extentInGunta = binding.etGuntas.getText()
            constructionType = binding.etConstructionType.getText()
            plotArea = binding.etConstructedPlotArea.getText()
            propertyFacing = binding.etFacing.getText()
            ageOfProperty = binding.etAgeOfFarmHouse.getText()
            plinthArea = binding.etFarmHousePlinthArea.getText()
            approachRoad = binding.etApproachRoad.getText()
            roadSizeFeet = binding.etApproachRoadWidth.getText()
            offerPricePerUnit = binding.etOfferPrice.getText()
            marketPricePerUnit = binding.etMarketPrice.getText()
            fieldPartnerCode = binding.etFieldPartner.getText()
            ownerName = binding.etOwnerName.getText()
            contactNumber1 = binding.etPrimaryContact.getText()
            contactNumber2 = binding.etAlternativeContact.getText()
            description = binding.etDescription.getText()
            offerPriceDetails = UnitDetails(
                value = binding.etOfferPrice.getText(),
                unitType = binding.etOfferPrice.getSwitchText()
            )
            marketPriceDetails = UnitDetails(
                value = binding.etMarketPrice.getText(),
                unitType = binding.etMarketPrice.getSwitchText()
            )
        }
    }

    private fun prepareAgricultureLandObject(): PostAgricultureRequest? {
        return postAgricultureRequest?.apply {
            villageName = binding.etVillage.getText()
            cityName = binding.etCity.getText()
            mandalName = binding.etMandal.getText()
            districtName = binding.etDistrict.getText()
            stateName = binding.etState.getText()
            extentInAcre = binding.etExtent.getText()
            extentInGunta = binding.etGuntas.getText()
            approachRoad = binding.etApproachRoad.getText()
            roadSizeFeet = binding.etApproachRoadWidth.getText()
            offerPricePerUnit = binding.etOfferPrice.getText()
            marketPricePerUnit = binding.etMarketPrice.getText()
            fieldPartnerCode = binding.etFieldPartner.getText()
            ownerName = binding.etOwnerName.getText()
            contactNumber1 = binding.etPrimaryContact.getText()
            contactNumber2 = binding.etAlternativeContact.getText()
            description = binding.etDescription.getText()
            offerPriceDetails = UnitDetails(
                value = binding.etOfferPrice.getText(),
                unitType = binding.etOfferPrice.getSwitchText()
            )
            marketPriceDetails = UnitDetails(
                value = binding.etMarketPrice.getText(),
                unitType = binding.etMarketPrice.getSwitchText()
            )
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