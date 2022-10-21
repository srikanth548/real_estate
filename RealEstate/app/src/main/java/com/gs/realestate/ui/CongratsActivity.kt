package com.gs.realestate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.databinding.ActivityCongratsBinding

class CongratsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCongratsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCongratsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}