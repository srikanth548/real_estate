package com.gs.realestate.ui.post

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityHomeBinding
import com.gs.realestate.databinding.ActivityMainBinding
import com.gs.realestate.databinding.ActivityPostpropertyBinding
import com.gs.realestate.ui.login.TermsAdapter

class PostPropertyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityPostpropertyBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostpropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@PostPropertyActivity)
        fetchLocation()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location->
            if (location != null) {
                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + "" +
                        currentLocation.longitude, Toast.LENGTH_SHORT).show()
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@PostPropertyActivity)
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED) {
            fetchLocation()
        }
        }
    }


}