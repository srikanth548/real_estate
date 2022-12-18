package com.gs.realestate.network.models.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnitDetails(
    @SerializedName("value")
    val value: String = "",
    @SerializedName("unit")
    val unitType: String = ""
) : Parcelable


@Parcelize
data class Pref(
    @SerializedName("ref")
    val ref: Int = 0,
    @SerializedName("ord")
    val ord: String = "",
    @SerializedName("pref")
    val pref: String = ""
) : Parcelable


@Parcelize
data class PropertyLocationHighlights(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("mediaurl")
    val mediaUrl: String = "",
    @SerializedName("highlightdescription")
    val highlightDescription: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("distance")
    val distance: String? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("icon")
    val icon: String? = null
) : Parcelable
