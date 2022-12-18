package com.gs.realestate.ui.post.properties

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostResidentialBinding
import com.gs.realestate.network.models.property.PostResidentialPropertyRequest
import com.gs.realestate.network.models.property.UnitDetails
import com.gs.realestate.ui.post.PostHighlightActivity
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
                        val plotObj = prepareResidentialPlotObject()
                        startActivity(
                            Intent(
                                this@PostResidentialActivity,
                                PostHighlightActivity::class.java
                            )
                        )
                    }
                }
                getString(R.string.str_flats) -> {
                    if (validateFlatType()) {
                        val flatObj = prepareResidentialFlatObject()
                        startActivity(
                            Intent(
                                this@PostResidentialActivity,
                                PostHighlightActivity::class.java
                            )
                        )
                    }
                }
                getString(R.string.str_villas_houses) -> {
                    if (validateHouseType()) {
                        val villaObj = prepareResidentialVillaObject()
                        startActivity(
                            Intent(
                                this@PostResidentialActivity,
                                PostHighlightActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    private fun prepareResidentialVillaObject(): PostResidentialPropertyRequest {
        return PostResidentialPropertyRequest(
            villageName = binding.llPostHouse.etVillage.getText(),
            cityName = binding.llPostHouse.etCity.getText(),
            mandalName = binding.llPostHouse.etMandal.getText(),
            districtName = binding.llPostHouse.etDistrict.getText(),
            stateName = binding.llPostHouse.etState.getText(),
            projectName = binding.llPostHouse.etNameOfVenture.getText(),
            propertyFacing = binding.llPostHouse.etFacing.getText(),
            approachRoad = binding.llPostHouse.etApproachRoad.getText(),
            roadWidth = binding.llPostHouse.etApproachRoadWidth.getText(),
            isGatedCommunity = binding.llPostHouse.gatedCommunitySwitch.isChecked.toString(),
            plinthArea = binding.llPostHouse.etPlinthArea.getText(),
            plotArea = binding.llPostHouse.etPlotArea.getText(),
            numberOfFloors = binding.llPostHouse.etNoOfFloors.getText(),
            floorNumber = binding.llPostHouse.etFloorNumber.getText(),
            numberOfBedRooms = binding.llPostHouse.etNoOfBedRooms.getText(),
            numberOfBathrooms = binding.llPostHouse.etNoOfBathrooms.getText(),
            ageOfProperty = binding.llPostHouse.etBuildingAge.getText(),
            offerPriceDetails = UnitDetails(
                value = binding.llPostHouse.etOfferPrice.getText(),
                unitType = binding.llPostHouse.etOfferPrice.getSwitchText()
            ),
            marketPriceDetails = UnitDetails(
                value = binding.llPostHouse.etMarketPrice.getText(),
                unitType = binding.llPostHouse.etMarketPrice.getSwitchText()
            ),
            rentalAmount = binding.llPostHouse.etApproaxRent.getText(),
            suitableFor = binding.llPostHouse.etSuitableFor.getText(),
            fieldPartnerCode = binding.llPostHouse.etFieldPartner.getText(),
            ownerName = binding.llPostHouse.etOwnerName.getText(),
            contactNumber1 = binding.llPostHouse.etPrimaryContact.getText(),
            contactNumber2 = binding.llPostHouse.etAlternativeContact.getText(),
            description = binding.llPostHouse.etDescription.getText()
        )
    }

    private fun prepareResidentialFlatObject(): PostResidentialPropertyRequest {
        return PostResidentialPropertyRequest(
            villageName = binding.llPostFlat.etVillage.getText(),
            cityName = binding.llPostFlat.etCity.getText(),
            mandalName = binding.llPostFlat.etMandal.getText(),
            districtName = binding.llPostFlat.etDistrict.getText(),
            stateName = binding.llPostFlat.etState.getText(),
            projectName = binding.llPostFlat.etNameOfVenture.getText(),
            propertyFacing = binding.llPostFlat.etFacing.getText(),
            approachRoad = binding.llPostFlat.etApproachRoad.getText(),
            roadWidth = binding.llPostFlat.etApproachRoadWidth.getText(),
            isGatedCommunity = binding.llPostFlat.gatedCommunitySwitch.isChecked.toString(),
            plinthArea = binding.llPostFlat.etPlinthArea.getText(),
            numberOfFloors = binding.llPostFlat.etNoOfFloors.getText(),
            floorNumber = binding.llPostFlat.etFloorNumber.getText(),
            numberOfBedRooms = binding.llPostFlat.etNoOfBedRooms.getText(),
            numberOfBathrooms = binding.llPostFlat.etNoOfBathrooms.getText(),
            fourWheelerParking = binding.llPostFlat.etNoOfCarParkings.getText(),
            ageOfProperty = binding.llPostFlat.etBuildingAge.getText(),
            rentalAmount = binding.llPostFlat.etApproaxRent.getText(),
            offerPriceDetails = UnitDetails(
                value = binding.llPostFlat.etOfferPrice.getText(),
                unitType = binding.llPostFlat.etOfferPrice.getSwitchText()
            ),
            marketPriceDetails = UnitDetails(
                value = binding.llPostFlat.etMarketPrice.getText(),
                unitType = binding.llPostFlat.etMarketPrice.getSwitchText()
            ),
            suitableFor = binding.llPostFlat.etSuitableFor.getText(),
            fieldPartnerCode = binding.llPostFlat.etFieldPartner.getText(),
            ownerName = binding.llPostFlat.etOwnerName.getText(),
            contactNumber1 = binding.llPostFlat.etPrimaryContact.getText(),
            contactNumber2 = binding.llPostFlat.etAlternativeContact.getText(),
            description = binding.llPostFlat.etDescription.getText()
        )
    }

    private fun prepareResidentialPlotObject(): PostResidentialPropertyRequest {
        return PostResidentialPropertyRequest(
            villageName = binding.llPostPlot.etVillage.getText(),
            cityName = binding.llPostPlot.etCity.getText(),
            mandalName = binding.llPostPlot.etMandal.getText(),
            districtName = binding.llPostPlot.etDistrict.getText(),
            stateName = binding.llPostPlot.etState.getText(),
            projectName = binding.llPostPlot.etNameOfVenture.getText(),
            extentInSqryrds = binding.llPostPlot.etExtent.getText(),
            propertyFacing = binding.llPostPlot.etFacing.getText(),
            approachRoad = binding.llPostPlot.etApproachRoad.getText(),
            roadWidth = binding.llPostPlot.etApproachRoadWidth.getText(),
            isGatedCommunity = binding.llPostPlot.gatedCommunitySwitch.isChecked.toString(),
            offerPriceDetails = UnitDetails(
                value = binding.llPostPlot.etOfferPrice.getText(),
                unitType = binding.llPostPlot.etOfferPrice.getSwitchText()
            ),
            marketPriceDetails = UnitDetails(
                value = binding.llPostPlot.etMarketPrice.getText(),
                unitType = "Lakhs"
            ),
            suitableFor = binding.llPostPlot.etSuitableFor.getText(),
            fieldPartnerCode = binding.llPostPlot.etFieldPartner.getText(),
            ownerName = binding.llPostPlot.etOwnerName.getText(),
            contactNumber1 = binding.llPostPlot.etPrimaryContact.getText(),
            contactNumber2 = binding.llPostPlot.etAlternativeContact.getText(),
            description = binding.llPostPlot.etDescription.getText()
        )

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