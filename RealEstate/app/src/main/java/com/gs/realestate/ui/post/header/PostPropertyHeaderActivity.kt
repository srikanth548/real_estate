package com.gs.realestate.ui.post.header

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.gs.realestate.databinding.ActivityPostpropertyBinding

class PostPropertyHeaderActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPostpropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostpropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



}