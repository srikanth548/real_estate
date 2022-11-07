package com.gs.realestate.network

data class AuthenticationRequest (
    // Setter Methods
    // Getter Methods
    var login_type: String? = "",
    var login_id: String? = ""
)

data class OtpRequest (
    // Setter Methods
    // Getter Methods
    var data: OtpData? = null

)

data class OtpData (
    // Setter Methods
    // Getter Methods
    var login_type: String? = null,
    var login_id: String? = null

)

data class OtpVerify (
    // Setter Methods
    // Getter Methods
    var data: OtpVerifyData? = null

)

data class OtpVerifyData (
    // Setter Methods
    // Getter Methods
    var login_type: String? = null,
    var login_id: String? = null,
    var otp: String? = null
)

