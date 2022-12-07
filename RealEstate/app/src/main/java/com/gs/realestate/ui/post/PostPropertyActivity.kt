package com.gs.realestate.ui.post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityPostpropertyBinding
import com.gs.realestate.ui.post.properties.PostAgricultureActivity
import com.gs.realestate.ui.post.properties.PostCommercialActivity
import com.gs.realestate.ui.post.properties.PostResidentialActivity
import com.gs.realestate.util.Constants
import com.gs.realestate.util.SnackBarToast
import com.gs.realestate.util.propertyCategoryView.CustomPropertyCategoryView


class PostPropertyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityPostpropertyBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private val TAG: String = PostPropertyActivity::class.java.simpleName
    private lateinit var map: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostpropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Post Property"

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@PostPropertyActivity)
        fetchLocation()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val apiKey = getString(R.string.api_key)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        autocompleteFragment!!.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map.let {
                    it.clear()
                    if (place.latLng != null) {
                        val markerOptions =
                            MarkerOptions().position(place.latLng).title("I am here!")
                        it.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 2f))
                        it.addMarker(markerOptions)
                    }

                }
                Log.i(TAG, "Place: " + place.name + ", " + place.id)
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Log.i(TAG, "An error occurred: $status")
            }
        })

        binding.btnPost.setOnClickListener {
            val selectedOption = binding.cvPropertyCategory.getSelectedOption()
            if (selectedOption?.second?.isEmpty() == true) {
                SnackBarToast.showErrorSnackBar(it, getString(R.string.str_select_property_type))
            } else {
                when (selectedOption?.first) {
                    CustomPropertyCategoryView.TYPE_AGRICULTURE -> {
                        val intent = Intent(this@PostPropertyActivity, PostAgricultureActivity::class.java)
                        intent.putExtra(Constants.EXTRA_PROPERTY_TYPE, selectedOption.second)
                        startActivity(intent)
                    }
                    CustomPropertyCategoryView.TYPE_RESIDENTIAL -> {
                        val intent = Intent(this@PostPropertyActivity, PostResidentialActivity::class.java)
                        intent.putExtra(Constants.EXTRA_PROPERTY_TYPE, selectedOption.second)
                        startActivity(intent)
                    }
                    CustomPropertyCategoryView.TYPE_COMMERCIAL -> {
                        val intent = Intent(this@PostPropertyActivity, PostCommercialActivity::class.java)
                        intent.putExtra(Constants.EXTRA_PROPERTY_TYPE, selectedOption.second)
                        startActivity(intent)
//                        startActivity(
//                            Intent(
//                                this@PostPropertyActivity,
//                                OpenSpaceActivity::class.java
//                            )
//                        )
                    }
                }
            }
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                Toast.makeText(
                    applicationContext, currentLocation.latitude.toString() + "" +
                            currentLocation.longitude, Toast.LENGTH_SHORT
                ).show()
                val supportMapFragment =
                    (supportFragmentManager.findFragmentById(R.id.map) as
                            SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@PostPropertyActivity)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        googleMap.setMyLocationEnabled(true)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2f))
        googleMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }


}