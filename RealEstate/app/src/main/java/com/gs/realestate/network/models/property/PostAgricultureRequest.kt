package com.gs.realestate.network.models.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostAgricultureRequest(
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
    @SerializedName("empty")
    var empty: String? = null,
    @SerializedName("extentinacre")
    var extentInAcre: String? = null,
    @SerializedName("offerpriceperunit")
    var offerPricePerUnit: String? = null,
    @SerializedName("marketpriceperunit")
    var marketPricePerUnit: String? = null,
    @SerializedName("extentingunta")
    var extentInGunta: String? = null,
    @SerializedName("approachroad")
    var approachRoad: String? = null,
    @SerializedName("roadsizefeet")
    var roadSizeFeet: String? = null,
    @SerializedName("offerprice")
    var offerPriceDetails: UnitDetails? = null,
    @SerializedName("marketprice")
    var marketPriceDetails: UnitDetails? = null,
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
    var latitute: Double? = null,
    @SerializedName("longtitue")
    var longtitue: Double? = null,
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


    //Farmhouse fields
    @SerializedName("ageofproperty")
    var ageOfProperty: String? = null,
    @SerializedName("constructiontype")
    var constructionType: String? = null,
    @SerializedName("empty1")
    var empty1: String? = null,
    @SerializedName("plintharea")
    var plinthArea : String? = null,
    @SerializedName("plotarea")
    var plotArea: String? = null,
    @SerializedName("propertyfacing")
    var propertyFacing: String? = null,

): Parcelable
