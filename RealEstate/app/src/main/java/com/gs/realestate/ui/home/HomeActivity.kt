package com.gs.realestate.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.gs.realestate.R
import com.gs.realestate.databinding.ActivityHomeBinding
import com.gs.realestate.ui.post.PostPropertyActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //  binding.appBarMain.toolbar.setTitleTextColor(resources.getColor(R.color.black))
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home, R.id.nav_allproperties -> {
                    navController.navigate(R.id.allPropertiesFrag)
                }
                R.id.nav_myaccount -> {
                    navController.navigate(R.id.accountFragment)
                }
                R.id.nav_postproperty -> {
                    startActivity(Intent(this@HomeActivity, PostPropertyActivity::class.java))
                }
                R.id.nav_settings -> {
                    navController.navigate(R.id.settingsFragement)
                }
                R.id.nav_help -> {
                    navController.navigate(R.id.helpFragment)
                }
                R.id.nav_notifications -> {
                    navController.navigate(R.id.notificationFragment)
                }
                R.id.nav_logout -> {
                    this.finish()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.activity_main_drawer, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}