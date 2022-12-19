package com.gs.realestate.network.models.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommercialPropertyRequest(
    @SerializedName("villagename")
    var villageName: String? = null,
    @SerializedName("citytown")
    var cityName: String? = null,
    @SerializedName("mandalname")
    var mandalName: String? = null,
    @SerializedName("districtname")
    var districtName: String? = null,
    @SerializedName("state")
    var stateName: String? = null,
    @SerializedName("projectname")
    var projectName: String? = null,
    @SerializedName("approachroad")
    var approachRoad: String? = null,
    @SerializedName("roadsizefeet")
    var roadWidth: String? = null,
    @SerializedName("empty")
    var empty: String? = null,
    @SerializedName("offerprice")
    var offerPriceDetails: UnitDetails? = null,
    @SerializedName("marketprice")
    var marketPriceDetails: UnitDetails? = null,
    @SerializedName("suitablefor")
    var suitableFor: String? = null,
    @SerializedName("fieldpartnercode")
    var fieldPartnerCode: String? = null,
    @SerializedName("ownername")
    var ownerName: String? = null,
    @SerializedName("contactnum1")
    var contactNumber1: String? = null,
    @SerializedName("contactnum2")
    var contactNumber2: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("pref")
    var prefDetails: Pref? = null,
    @SerializedName("latitute")
    var latitude: Double? = null,
    @SerializedName("longtitue")
    var longitude: Double? = null,
    @SerializedName("locationname")
    var locationName: String? = null,
    @SerializedName("statecd")
    var statecd: String? = null,
    @SerializedName("propsubtypeid")
    var propertySubTypeId: Int? = null,
    @SerializedName("proptypeid")
    var propertyTypeId: Int? = null,
    @SerializedName("distancefromorr")
    var distanceFromORR: String? = null,
    @SerializedName("distancefromhyd")
    var distanceFromHyderabad: String? = null,
    @SerializedName("propertyLocationHighlights")
    var propertyLocationHighlights: ArrayList<PropertyLocationHighlights> = arrayListOf(),
    @SerializedName("PropertyWellKnownFor")
    var propertyWellKnownFor: ArrayList<String> = arrayListOf(),
    @SerializedName("extentinsqryrds")
    var extentInSqrYrds: String? = null,


    //Building fields
    @SerializedName("ageofproperty")
    var ageOfProperty: String? = null,
    @SerializedName("floornum")
    var floorNumber: String? = null,
    @SerializedName("nooffloors")
    var numberOfFloors: String? = null,
    @SerializedName("plotarea")
    var plotArea: String? = null,
    @SerializedName("plintharea")
    var plinthArea: String? = null,
) : Parcelable
