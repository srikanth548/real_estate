package com.gs.realestate.ui.login

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.gs.realestate.databinding.ActivityMainBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showTerms()

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)


    }

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