package com.gs.realestate.ui.post

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.gs.realestate.R
import com.gs.realestate.base.BaseActivity
import com.gs.realestate.databinding.ActivityPosthighlightsBinding
import com.gs.realestate.network.ApiInterface
import com.gs.realestate.network.PlacesPOJO
import com.gs.realestate.network.RetrofitClient
import com.gs.realestate.network.models.property.*
import com.gs.realestate.network.models.propertyType.PropertyKnownForDetails
import com.gs.realestate.ui.home.HomeActivity
import com.gs.realestate.ui.login.TermsAdapter
import com.gs.realestate.ui.post.adapter.HighLightsAdapter
import com.gs.realestate.ui.post.adapter.ImageAdapter
import com.gs.realestate.ui.post.adapter.PicturesAdapter
import com.gs.realestate.util.CommonMethods
import com.gs.realestate.util.Constants
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.authToken
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.SnackBarToast
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dev.ronnie.github.imagepicker.ImagePicker
import dev.ronnie.github.imagepicker.ImageResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File


class PostHighlightActivity : BaseActivity(), PaymentResultWithDataListener, ExternalWalletListener,
    DialogInterface.OnClickListener {
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

    private lateinit var picturesAdapter: PicturesAdapter


    private var postAgricultureRequest: PostAgricultureRequest? = null
    private var postResidentialRequest: PostResidentialPropertyRequest? = null
    private var postCommercialPropertyRequest: CommercialPropertyRequest? = null
    private var selectedCategory: String = ""
    private var selectedSubTypeId: Int = 0
    private var selectedTypeId: Int = 0
    private var locationProximityList: List<PropertyKnownForDetails>? = null


    //Random UUID to use for uploading images
    private val imageUploadUUID = CommonMethods.getRandomUUID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosthighlightsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Checkout.preload(applicationContext)

        readExtras()

        imagePicker = ImagePicker(this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@PostHighlightActivity)

        setPicturesView()
//        setLocationProximityData()

        imagesUpload = ImageAdapter(imagesList, this)

        binding.clCustomPictureUpload.tvCamera.setOnClickListener {
            imagePicker.takeFromCamera { imageResult ->
                imageCallBack(
                    imageResult,
                    isFromCamera =true
                )
            }
        }
        binding.clCustomPictureUpload.tvGallery.setOnClickListener {
            imagePicker.pickFromStorage { imageResult ->
                imageCallBack(
                    imageResult,
                    isFromCamera = false
                )
            }
        }
        highLightsAdapter = HighLightsAdapter(highlistnearby, this)
        binding.gdHighlights.adapter = highLightsAdapter

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
//                startPayment()
                postPropertyDetails(null)
            } else {
                SnackBarToast.showErrorSnackBar(it, getString(R.string.pleasecheckterms))
            }
        }


        fetchPropertyTypesFromServer()
    }


    private fun readExtras() {
        intent?.extras?.let {
            if (it.containsKey(Constants.EXTRA_PROPERTY_CATEGORY)) {
                selectedCategory = it.getString(Constants.EXTRA_PROPERTY_CATEGORY, "")
                when (selectedCategory) {
                    Constants.EXTRA_AGRICULTURE -> {
                        postAgricultureRequest =
                            it.getParcelable(Constants.EXTRA_POST_PROPERTY_REQUEST)
                        selectedTypeId = postAgricultureRequest?.propertyTypeId ?: 0
                        selectedSubTypeId = postAgricultureRequest?.propertySubTypeId ?: 0
                        println("Agriculture : $postAgricultureRequest")
                    }
                    Constants.EXTRA_RESIDENTIAL -> {
                        postResidentialRequest =
                            it.getParcelable(Constants.EXTRA_POST_PROPERTY_REQUEST)
                        selectedTypeId = postResidentialRequest?.propertyTypeId ?: 0
                        selectedSubTypeId = postResidentialRequest?.propertySubTypeId ?: 0
                        print("Residential : $postResidentialRequest")
                    }
                    Constants.EXTRA_COMMERCIAL -> {
                        postCommercialPropertyRequest =
                            it.getParcelable(Constants.EXTRA_POST_PROPERTY_REQUEST)
                        selectedTypeId = postCommercialPropertyRequest?.propertyTypeId ?: 0
                        selectedSubTypeId = postCommercialPropertyRequest?.propertySubTypeId ?: 0
                        print("Commercial : $postCommercialPropertyRequest")
                    }
                }
            }
        }
    }


    private fun setLocationProximityData() {
        val itemsArray = listOf(
            "Near to Reserve Forest",
            "24 Hours Security",
            "Solar Fencing",
            "Near to Hospitals",
            "Near to Police Station",
            "Near to Town (can reach 10min 15min 30min)",
            "Near to Local Village",
            "Near to Market ( Fruit & Vegetable)",
            "Near to water Projects",
            "Any other Water body famous nearby",
            "Near to resorts",
            "Near to NH",
            "Near to River or pond"
        )
//        createChips(itemsArray)
    }


    private fun createChips(itemsArray: List<PropertyKnownForDetails>) {
        itemsArray.forEachIndexed { index, propertyKnownForDetails ->
            val chip = Chip(this@PostHighlightActivity).apply {
                text = propertyKnownForDetails.description
                isCheckable = true
//                setChipBackgroundColorResource(R.color.purple_500)
//                isCloseIconVisible = true
//                setTextColor(ContextCompat.getColor(context, R.color.white))
//                setTextAppearance(R.style.ChipTextAppearance)
                setOnCheckedChangeListener { compoundButton, b ->
                    itemsArray[index].isSelected = b
                }
            }
            binding.cgProximity.addView(chip)
        }
    }


    private fun setPicturesView() {
        binding.clCustomPictureUpload.gvUploadedPictures.apply {
            picturesAdapter = PicturesAdapter(imagesList, this@PostHighlightActivity)
            layoutManager = GridLayoutManager(this@PostHighlightActivity, 2)
            adapter = picturesAdapter
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
    private fun imageCallBack(imageResult: ImageResult<Uri>, isFromCamera: Boolean) {
        when (imageResult) {
            is ImageResult.Success -> {
                val uri = imageResult.value

                syncImagesToServer(isFromCamera,imageUri = uri, index = imagesList.size + 1)
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


    /*
    * API call to fetch the property types
    * */
    private fun fetchPropertyTypesFromServer() {
        val retrofit = RetrofitClient.getInstance(this)
        val apiInterface = retrofit.create(ApiInterface::class.java)

        lifecycleScope.launchWhenCreated {
            showLoader()

            val response = apiInterface.fetchPropertyTypes()
            response.let {
                if (it.isSuccessful) {
                    it.body()?.let { responseData ->
                        if (responseData.statusCode == 0) {
                            //fetch success
                            locationProximityList =
                                responseData.propertyCategoryList
                                    .firstOrNull { it.propertyTypeId == selectedTypeId }
                                    ?.propertySubTypesList
                                    ?.firstOrNull { it.propertySubTypeId == selectedSubTypeId }
                                    ?.knownForList

                            locationProximityList?.let { it1 -> createChips(it1) }
                        } else {
                            SnackBarToast.failedCall(this@PostHighlightActivity)
                        }
                        hideLoader()
                    }
                } else {
                    SnackBarToast.failedCall(this@PostHighlightActivity)
                    Log.i("failed {}", response.body().toString())
                    hideLoader()
                }
            }
        }
    }


    /*
    * Image sync to server
    * multi part file upload
    * */
    private fun syncImagesToServer(isFromCamera: Boolean, imageUri: Uri, index: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val crsfToken = PreferenceHelper.customPreference(this).csrftoken ?: ""

        lifecycleScope.launchWhenCreated {
            showLoader()

            val imageFile = if(isFromCamera){
                imageUri.lastPathSegment?.let { File(cacheDir, it) }
            }else{
                val filePath = CommonMethods.getRealPathFromURI(
                    mContext = this@PostHighlightActivity,
                    imageUri
                ) ?: ""

                File(filePath)
            }


            val fileBody = imageFile?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multiPartBody =
                fileBody?.let { MultipartBody.Part.createFormData("image", imageFile.name, it) }

            val response = multiPartBody?.let {
                apiInterface.syncImageToServer(
                    crsfToken = crsfToken,
                    file = it,
                    serialNo = index,
                    uuid = imageUploadUUID
                )
            }


            if (response?.isSuccessful == true) {
                //your code for handling success response
                println("Image upload response : ${response.body()}")
                response.body()?.let {
                    if (it.statusCode == 0) {
                        //success
                        imagesList.add(imageUri)
                        if (imagesList.isNotEmpty()) {
                            binding.clCustomPictureUpload.gvUploadedPictures.visibility =
                                View.VISIBLE
                        }
                        picturesAdapter.notifyDataSetChanged()
                    } else {
                        SnackBarToast.showErrorSnackBar(binding.root, it.data)
                    }
                }
                hideLoader()
            } else {
                SnackBarToast.failedCall(this@PostHighlightActivity)
                Log.i("failed {}", response?.body().toString())
                hideLoader()
            }
        }
    }


    /*
    * Upload property details
    * */
    private fun postPropertyDetails(prefData: Pref?) {
        when (selectedCategory) {
            Constants.EXTRA_AGRICULTURE -> {
                postAgricultureRequest?.apply {
                    prefDetails = Pref(
                        ref = 410,
                        ord = "order_KstYVgfU5c692S",
                        pref = "pay_KstYqWEi4Yeigb"
                    )
                    statecd = "TG"
                    distanceFromORR = "12.4"
                    distanceFromHyderabad = "1.9"
                    propertyLocationHighlights = arrayListOf(
                        PropertyLocationHighlights(
                            id = 131,
                            description = "bus stand",
                            mediaUrl = "",
                            highlightDescription = "",
                            type = "bus stand",
                            distance = null,
                            name = ""
                        )
                    )
                    propertyWellKnownFor = getSelectedLocationProximity()

                }

                syncAgriculturePropertyData()
            }

            Constants.EXTRA_RESIDENTIAL -> {
                postResidentialRequest?.apply {
                    prefDetails = Pref(
                        ref = 410,
                        ord = "order_KstYVgfU5c692S",
                        pref = "pay_KstYqWEi4Yeigb"
                    )
                    statecd = "TG"
                    distanceFromORR = "12.4"
                    distanceFromHyderabad = "1.9"
                    propertyLocationHighlights = arrayListOf(
                        PropertyLocationHighlights(
                            id = 131,
                            description = "bus stand",
                            mediaUrl = "",
                            highlightDescription = "",
                            type = "bus stand",
                            distance = null,
                            name = ""
                        )
                    )
                    propertyWellKnownFor = getSelectedLocationProximity()
                }

                syncResidentialPropertyData()
            }

            Constants.EXTRA_COMMERCIAL -> {
                postCommercialPropertyRequest?.apply {
                    prefDetails = Pref(
                        ref = 410,
                        ord = "order_KstYVgfU5c692S",
                        pref = "pay_KstYqWEi4Yeigb"
                    )
                    statecd = "TG"
                    distanceFromORR = "12.4"
                    distanceFromHyderabad = "1.9"
                    propertyLocationHighlights = arrayListOf(
                        PropertyLocationHighlights(
                            id = 131,
                            description = "bus stand",
                            mediaUrl = "",
                            highlightDescription = "",
                            type = "bus stand",
                            distance = null,
                            name = ""
                        )
                    )
                    propertyWellKnownFor = getSelectedLocationProximity()
                }

                syncCommercialPropertyData()
            }
        }
    }


    /*
    * Method to sync the agriculture data
    * */
    private fun syncAgriculturePropertyData() {
        val retrofit = RetrofitClient.getInstance(this)
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val crsfToken = PreferenceHelper.customPreference(this).csrftoken ?: ""
        val authToken = ("Bearer " + PreferenceHelper.customPreference(this).authToken) ?: ""

        lifecycleScope.launchWhenCreated {
            showLoader()

            val response = postAgricultureRequest?.let {
                apiInterface.syncAgricultureProperty(
                    token = authToken,
                    crsfToken = crsfToken,
                    uuid = imageUploadUUID,
                    postAgricultureRequest = it,
                    referer = Constants.REFERRER_URL
                )
            }

            response?.let {
                if (it.isSuccessful) {
                    it.body()?.let { responseData ->
                        if (responseData.status == 0) {
                            //sync success
                            moveToHomeScreen()
                        } else {
                            SnackBarToast.failedCall(this@PostHighlightActivity)
                        }
                    }
                    hideLoader()

                } else {
                    SnackBarToast.failedCall(this@PostHighlightActivity)
                    Log.i("failed {}", response.body().toString())
                    hideLoader()
                }
            }
        }
    }


    /*
    * Method to sync the residential data
    * */
    private fun syncResidentialPropertyData() {
        val retrofit = RetrofitClient.getInstance(this)
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val crsfToken = PreferenceHelper.customPreference(this).csrftoken ?: ""
        val authToken = ("Bearer " + PreferenceHelper.customPreference(this).authToken) ?: ""

        lifecycleScope.launchWhenCreated {
            showLoader()

            val response = postResidentialRequest?.let {
                apiInterface.syncResidentialProperty(
                    token = authToken,
                    crsfToken = crsfToken,
                    uuid = imageUploadUUID,
                    postResidentialPropertyRequest = it,
                    referer = Constants.REFERRER_URL
                )
            }

            response?.let {
                if (it.isSuccessful) {
                    it.body()?.let { responseData ->
                        if (responseData.status == 0) {
                            //sync success
                            moveToHomeScreen()
                        } else {
                            SnackBarToast.failedCall(this@PostHighlightActivity)
                        }
                    }
                    hideLoader()

                } else {
                    SnackBarToast.failedCall(this@PostHighlightActivity)
                    Log.i("failed {}", response.body().toString())
                    hideLoader()
                }
            }
        }
    }


    /*
   * Method to sync the commercial data
   * */
    private fun syncCommercialPropertyData() {
        val retrofit = RetrofitClient.getInstance(this)
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val crsfToken = PreferenceHelper.customPreference(this).csrftoken ?: ""
        val authToken = ("Bearer " + PreferenceHelper.customPreference(this).authToken) ?: ""


        lifecycleScope.launchWhenCreated {
            showLoader()

            val response = postCommercialPropertyRequest?.let {
                apiInterface.syncCommercialProperty(
                    token = authToken,
                    crsfToken = crsfToken,
                    uuid = imageUploadUUID,
                    postCommercialPropertyRequest = it,
                    referer = Constants.REFERRER_URL
                )
            }

            response?.let {
                if (it.isSuccessful) {
                    it.body()?.let { responseData ->
                        if (responseData.status == 0) {
                            //sync success
                            moveToHomeScreen()
                        } else {
                            SnackBarToast.failedCall(this@PostHighlightActivity)
                        }
                    }
                    hideLoader()

                } else {
                    SnackBarToast.failedCall(this@PostHighlightActivity)
                    Log.i("failed {}", response.body().toString())
                    hideLoader()
                }
            }
        }
    }


    /*
    * Method to get the selected location proximity
    * */
    private fun getSelectedLocationProximity(): List<String> {
        return locationProximityList
            ?.filter { it.isSelected }
            ?.map { it.id.toString() }
            ?: listOf()
    }


    /*
    * Move to home screen
    * */
    private fun moveToHomeScreen() {
        val i = Intent(this, HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }
}