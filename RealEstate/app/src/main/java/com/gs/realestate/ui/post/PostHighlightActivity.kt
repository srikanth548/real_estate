package com.gs.realestate.ui.post

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gs.realestate.R
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPosthighlightsBinding
import com.gs.realestate.network.ApiInterface
import com.gs.realestate.network.PlacesPOJO
import com.gs.realestate.network.RetrofitClient
import com.gs.realestate.ui.login.TermsAdapter
import com.gs.realestate.ui.post.adapter.HighLightsAdapter
import com.gs.realestate.ui.post.adapter.ImageAdapter
import com.gs.realestate.util.SnackBarToast
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dev.ronnie.github.imagepicker.ImagePicker
import dev.ronnie.github.imagepicker.ImageResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class PostHighlightActivity : BaseActivity(), PaymentResultWithDataListener, ExternalWalletListener,
    DialogInterface.OnClickListener {
    private lateinit var imagesGrid: GridView
    private lateinit var binding: ActivityPosthighlightsBinding
    private lateinit var imagesUpload: ImageAdapter
    private lateinit var highLightsAdapter: HighLightsAdapter
    private val highlistnearby = ArrayList<String>()
    private val imagesList = ArrayList<Uri>()
    private lateinit var imagePicker: ImagePicker //You need to instantiate it in Oncreate
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private lateinit var alertDialogBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosthighlightsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Checkout.preload(applicationContext)
        imagePicker = ImagePicker(this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@PostHighlightActivity)

        imagesGrid = binding.imagesgrid
        imagesUpload = ImageAdapter(imagesList, this)
        imagesGrid.adapter = imagesUpload

        binding.rlCamera.setOnClickListener {
            imagePicker.takeFromCamera { imageResult ->
                imageCallBack(
                    imageResult
                )
            }
        }
        binding.rlGallery.setOnClickListener {
            imagePicker.pickFromStorage { imageResult ->
                imageCallBack(
                    imageResult
                )
            }
        }
        highLightsAdapter = HighLightsAdapter(highlistnearby, this)
        binding.gdHighlights.adapter = highLightsAdapter
//        highlistnearby.add("h1")
//        highlistnearby.add("h")
//        highLightsAdapter.notifyDataSetChanged()
        fetchLocation()
        alertDialogBuilder = AlertDialog.Builder(this@PostHighlightActivity)
        alertDialogBuilder.setTitle("Payment Result")
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton("Ok", this)

        binding.terms.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showTerms()
            }
        }

        binding.btnPost.setOnClickListener {
            if (binding.terms.isChecked) {
                startPayment()
            } else {
                SnackBarToast.showErrorSnackBar(it, getString(R.string.pleasecheckterms))
            }
        }
    }

    private fun showTerms() {
        val terms =
            this@PostHighlightActivity.resources.getStringArray(com.gs.realestate.R.array.terms)
        val adapter = TermsAdapter(this, terms)
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@PostHighlightActivity)
        builderSingle.setTitle("Terms")

        builderSingle.setAdapter(adapter) { _, _ ->

        }
        builderSingle.setPositiveButton("Accept") { dialog, _ -> dialog.dismiss() }
        builderSingle.setNegativeButton("cancel") { dialog, _ ->
            run {
                binding.terms.isChecked = false
                dialog.dismiss()
            }
        }
        builderSingle.show()

//        DialogPlus.newDialog(this)
//            .setHeader(com.gs.realestate.R.layout.view_header)
//            .setPadding(10,20,10,10)
//           .setAdapter(adapter)
//            .setGravity(Gravity.BOTTOM)
//            .setOnClickListener(OnClickListener { dialog, view ->
//                Toast.makeText(this@LoginActivity,"test",Toast.LENGTH_LONG).show()
//            })
//            .setCancelable(true)
//            .setExpanded(true) // This will enable the expand feature, (similar to android L share dialog)
//            .create().show()

//        val dialog = DialogPlus.newDialog(this)
//            .setAdapter(adapter)
//            .setOnItemClickListener { dialog, item, view, position -> }
//            .setExpanded(true) // This will enable the expand feature, (similar to android L share dialog)
//            .create()
//        dialog.show()
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
                getPlaces()
            }
        }
    }

    private fun getPlaces() {
        showLoader()
        val retrofit = RetrofitClient.getClient()
        val apiInterface = retrofit?.create(ApiInterface::class.java)

        /**
         * For Locations In India McDonalds stores aren't returned accurately
         */
        lifecycleScope.launchWhenCreated {
            try {
                val latLngString =
                    currentLocation.latitude.toString() + "," + currentLocation.longitude.toString()
                //String.format("%d,%d",currentLocation.latitude,currentLocation.longitude)
                val response = apiInterface?.doPlaces(
                    "restaurants",
                    latLngString,
                    "restaurants",
                    true,
                    "distance",
                    "AIzaSyD3Dh9p2T3Oghmdmdbbj7sQL50isgbefag"
                )
                response?.enqueue(object : retrofit2.Callback<PlacesPOJO.Root?> {
                    override fun onResponse(
                        call: Call<PlacesPOJO.Root?>,
                        response: Response<PlacesPOJO.Root?>
                    ) {
                        val root = response.body()
                        if (response.isSuccessful) {
                            hideLoader()
                            if (root!!.status.equals("OK")) {

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "No matches found near you",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (response.code() != 200) {
                            hideLoader()
                            Toast.makeText(
                                applicationContext,
                                "Error " + response.code() + " found.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<PlacesPOJO.Root?>, t: Throwable) {
                        // Log error here since request failed
                        call.cancel()
                        hideLoader()
                    }
                })

            } catch (Ex: Exception) {
                hideLoader()
                Log.e("Error", Ex.localizedMessage ?: "")
            }
        }

    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_yRQ1OAs9MTKyF3")


        try {
            val options = JSONObject()

            options.put("name", "Real Estate")
            options.put("description", "Posting Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put(
                "image",
                "http://dev.rightmyproperty.in/static/media/right_my_property_logo.b2909bab8bfc2a254934.png"
            )
            options.put("currency", "INR")
            options.put("amount", "100")
            options.put("send_sms_hash", true)

            val prefill = JSONObject()
            prefill.put("email", "srikanth.sri001@gmail.com")
            prefill.put("contact", "8121112310")

            options.put("prefill", prefill)


            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    //CallBack for result
    private fun imageCallBack(imageResult: ImageResult<Uri>) {
        when (imageResult) {
            is ImageResult.Success -> {
                val uri = imageResult.value
                imagesList.add(uri)
                imagesUpload.notifyDataSetChanged()
            }
            is ImageResult.Failure -> {
                val errorString = imageResult.errorString
                Toast.makeText(this@PostHighlightActivity, errorString, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("Payment Failed : Payment Data: ${p2?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
    }
}