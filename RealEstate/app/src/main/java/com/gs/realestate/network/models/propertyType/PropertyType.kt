package com.gs.realestate.network.models.propertyType

import com.google.gson.annotations.SerializedName

data class PropertyTypesResponseModel(
    @SerializedName("data")
    val propertyCategoryList: List<PropertyTypeDetails>,
    @SerializedName("error")
    val errorMessage: String = "",
    @SerializedName("statusCode")
    val statusCode: Int = 0
)


data class PropertyTypeDetails(
    @SerializedName("propertytype_id")
    val propertyTypeId: Int = 0,
    @SerializedName("propertytype")
    val propertyType: String = "",
    @SerializedName("location_property_highlights")
    val propertyHighlightsList: List<LocationPropertyHighlights>,
    @SerializedName("property_subtypes")
    val propertySubTypesList: List<PropertySubType>
)

data class LocationPropertyHighlights(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("mediaurl")
    val mediaUrl: String = "",
    @SerializedName("highlightdescription")
    val highlightDescription: String = "",
    @SerializedName("type")
    val type: String = ""
)

data class PropertySubType(
    @SerializedName("propertytype_id")
    val propertyTypeId: Int = 0,
    @SerializedName("propertysubtype_id")
    val propertySubTypeId: Int = 0,
    @SerializedName("propertysubtype")
    val propertySubType: String = "",
    @SerializedName("imageuploadlimit")
    val imageUploadLimit: Int = 0,
    @SerializedName("videouploadlimit")
    val videoUploadLimit: Int = 0,
    @SerializedName("property_known_for")
    val knownForList: List<PropertyKnownForDetails>
)

data class PropertyKnownForDetails(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("iconurl")
    val iconUrl: String = "",
    @SerializedName("icon")
    val icon: String = ""
)
