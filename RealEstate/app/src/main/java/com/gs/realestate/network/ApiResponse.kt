package com.gs.realestate.network

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class GetOtp {
    // Setter Methods
    // Getter Methods
    var data: Data? = null
    var error: String? = null
    var statusCode = 0f

}

class Data {
    // Setter Methods
    // Getter Methods
    var status: String? = null
    var otp: String? = null
    var cust_status: String? = null
}

class VerifyOtp {
    // Setter Methods
    // Getter Methods
    var data: VerifyData? = null
    var error: String? = null
    var statusCode = 0f

}

class VerifyData {
    // Setter Methods
    // Getter Methods
    var status: String? = null
    var auth: String? = null
    var user_act = false
    var cust_status: String? = null

}

class AuthenticationResponse {
    // Setter Methods
    // Getter Methods
    var data: String? = null
    var username: String? = null
    var customername: String? = null
    var token: String? = null
    var error: String? = null
    var statusCode = 0f
    var customeremail: String? = null

}

@Parcelize
data class Details (
    var value: String? = null,
    var unit: String? = null
):Parcelable

@Parcelize
data class Series (
    var name: String? = null,
    var data: List<Int>? = null
): Parcelable

@Parcelize
data class Properties(
    var propertyid: Int = 0,
    var imageUrl: String? = null,
    var propertyType: String? = null,
    var details: List<Details>? = null,
    var location: String? = null,
    var viewCount: Int = 0,
    var favCount: Int = 0,
    var commentCount: Int = 0,
    var expiryDate: String? = null,
    var categories: List<String>? = null,
    var series: List<Series>? = null
): Parcelable

class AllPropertiesResponse {
    var data: String? = null
    var properties: List<Properties>? = null
    var error: String? = null
    var statusCode = 0
}

class PlacesPOJO {
    inner class Root : Serializable {
        @SerializedName("results")
        var customA: List<CustomA> = ArrayList()

        @SerializedName("status")
        var status: String? = null
    }

    inner class CustomA : Serializable {
        @SerializedName("geometry")
        var geometry: Geometry? = null

        @SerializedName("vicinity")
        var vicinity: String? = null

        @SerializedName("name")
        var name: String? = null
    }

    inner class Geometry : Serializable {
        @SerializedName("location")
        var locationA: LocationA? = null
    }

    inner class LocationA : Serializable {
        @SerializedName("lat")
        var lat: String? = null

        @SerializedName("lng")
        var lng: String? = null
    }
}


data class ImageUploadResponse(
    @SerializedName("data")
    val data: String = "",
    @SerializedName("tags")
    val tags: String? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("imgUrl")
    val imageUrl: String = "",
    @SerializedName("statusCode")
    val statusCode: Int= 0,
)


data class PropertyUploadResponse(
    @SerializedName("resp")
    val propertyId: String = "",
    @SerializedName("stat")
    val status: Int = 0
)
