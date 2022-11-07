package com.gs.realestate.ui.login

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.gs.realestate.databinding.ActivityMainBinding
import com.gs.realestate.network.ApiInterface
import com.gs.realestate.network.AuthenticationRequest
import com.gs.realestate.network.RetrofitClient


class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // showTerms()

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

     //   authenticationUser()

    }

//    fun authenticationUser() {
//        val authenticationRequest = AuthenticationRequest("mobile_number","8121112310")
//        var retrofit = RetrofitClient.getInstance()
//        var apiInterface = retrofit.create(ApiInterface::class.java)
//        lifecycleScope.launchWhenCreated {
//            try {
//                val response = apiInterface.authentication(authenticationRequest)
//                if (response.isSuccessful()) {
//                    //your code for handaling success response
//                    response.body()?.token
//                    Toast.makeText(
//                        this@LoginActivity,
//                        response.message().toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                } else {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        response.errorBody().toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }catch (Ex:Exception){
//                Log.e("Error",Ex.localizedMessage)
//            }
//        }
//
//    }

    fun showTerms(){
        val terms = this@LoginActivity.resources.getStringArray(com.gs.realestate.R.array.terms)
        val adapter = TermsAdapter(this, terms)
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        builderSingle.setTitle("Terms")

        builderSingle.setAdapter(adapter) { dialog, which ->

        }
        builderSingle.setPositiveButton("Accept") { dialog, which -> dialog.dismiss() }
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
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


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}