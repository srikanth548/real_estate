package com.gs.realestate.ui.post.properties

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostCommercialBinding
import com.gs.realestate.network.models.property.CommercialPropertyRequest
import com.gs.realestate.network.models.property.UnitDetails
import com.gs.realestate.ui.post.PostHighlightActivity
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast

class PostCommercialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostCommercialBinding
    private var selectedType = ""
    private var postCommercialPropertyRequest: CommercialPropertyRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostCommercialBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.extras?.let {
            postCommercialPropertyRequest = it.getParcelable(Constants.EXTRA_POST_PROPERTY_REQUEST)
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
                        val openSpaceObj = prepareCommercialOpenSpaceObject()
                        val intent =
                            Intent(this@PostCommercialActivity, PostHighlightActivity::class.java)
                        intent.putExtra(
                            Constants.EXTRA_PROPERTY_CATEGORY,
                            Constants.EXTRA_COMMERCIAL
                        )
                        intent.putExtra(Constants.EXTRA_POST_PROPERTY_REQUEST, openSpaceObj)
                        startActivity(intent)
                    }
                }
                getString(R.string.str_buildings) -> {
                    if (validateBuildingType()) {
                        val buildingObj = prepareCommercialBuildingObject()
                        val intent =
                            Intent(this@PostCommercialActivity, PostHighlightActivity::class.java)
                        intent.putExtra(
                            Constants.EXTRA_PROPERTY_CATEGORY,
                            Constants.EXTRA_COMMERCIAL
                        )
                        intent.putExtra(Constants.EXTRA_POST_PROPERTY_REQUEST, buildingObj)
                        startActivity(intent)
                    }
                }
            }
        }
    }


    private fun prepareCommercialBuildingObject(): CommercialPropertyRequest? {
        // need to ask key for constructed area
        return postCommercialPropertyRequest?.apply {
            villageName = binding.llCOmmercialBuilding.etVillage.getText()
            cityName = binding.llCOmmercialBuilding.etCity.getText()
            mandalName = binding.llCOmmercialBuilding.etMandal.getText()
            districtName = binding.llCOmmercialBuilding.etDistrict.getText()
            stateName = binding.llCOmmercialBuilding.etState.getText()
            projectName = binding.llCOmmercialBuilding.etNameOfVenture.getText()
            approachRoad = binding.llCOmmercialBuilding.etApproachRoad.getText()
            roadWidth = binding.llCOmmercialBuilding.etApproachRoadWidth.getText()
            plotArea = binding.llCOmmercialBuilding.etPlotArea.getText()
            numberOfFloors = binding.llCOmmercialBuilding.etNoOfFloors.getText()
            floorNumber = binding.llCOmmercialBuilding.etFloorNumber.getText()
            ageOfProperty = binding.llCOmmercialBuilding.etAgeOfBuilding.getText()
            offerPriceDetails = UnitDetails(
                value = binding.llCOmmercialBuilding.etOfferPrice.getText(),
                unitType = binding.llCOmmercialBuilding.etOfferPrice.getSwitchText()
            )
            marketPriceDetails = UnitDetails(
                value = binding.llCOmmercialBuilding.etMarketPrice.getText(),
                unitType = "Lakhs"
            )
            suitableFor = binding.llCOmmercialBuilding.etSuitableFor.getText()
            fieldPartnerCode = binding.llCOmmercialBuilding.etFieldPartner.getText()
            ownerName = binding.llCOmmercialBuilding.etOwnerName.getText()
            contactNumber1 = binding.llCOmmercialBuilding.etPrimaryContact.getText()
            contactNumber2 = binding.llCOmmercialBuilding.etAlternativeContact.getText()
            description = binding.llCOmmercialBuilding.etDescription.getText()
        }
    }


    private fun prepareCommercialOpenSpaceObject(): CommercialPropertyRequest? {
        return postCommercialPropertyRequest?.apply {
            villageName = binding.llCommercialOpen.etVillage.getText()
            cityName = binding.llCommercialOpen.etCity.getText()
            mandalName = binding.llCommercialOpen.etMandal.getText()
            districtName = binding.llCommercialOpen.etDistrict.getText()
            stateName = binding.llCommercialOpen.etState.getText()
            extentInSqrYrds = binding.llCommercialOpen.etExtent.getText()
            approachRoad = binding.llCommercialOpen.etApproachRoad.getText()
            roadWidth = binding.llCommercialOpen.etApproachRoadWidth.getText()
            offerPriceDetails = UnitDetails(
                value = binding.llCommercialOpen.etOfferPrice.getText(),
                unitType = binding.llCommercialOpen.etOfferPrice.getSwitchText()
            )
            marketPriceDetails = UnitDetails(
                value = binding.llCommercialOpen.etMarketPrice.getText(),
                unitType = "Lakhs"
            )
            suitableFor = binding.llCommercialOpen.etMarketPrice.getText()
            fieldPartnerCode = binding.llCommercialOpen.etFieldPartner.getText()
            ownerName = binding.llCommercialOpen.etOwnerName.getText()
            contactNumber1 = binding.llCommercialOpen.etPrimaryContact.getText()
            contactNumber2 = binding.llCommercialOpen.etAlternativeContact.getText()
            description = binding.llCommercialOpen.etDescription.getText()
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
        }else if(!binding.llCommercialOpen.etAlternativeContact.isValidText()){
            showErrorMessage(getString(R.string.str_err_alt_contact_number))
            return false
        }else if(!binding.llCommercialOpen.etDescription.isValidText()){
            showErrorMessage(getString(R.string.str_err_description))
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
        }else if(!binding.llCOmmercialBuilding.etAlternativeContact.isValidText()){
            showErrorMessage(getString(R.string.str_err_alt_contact_number))
            return false
        }else if(!binding.llCOmmercialBuilding.etDescription.isValidText()){
            showErrorMessage(getString(R.string.str_err_description))
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