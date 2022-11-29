package com.gs.realestate.ui.post

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gs.realestate.databinding.ActivityPostflatBinding

class PostFlatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostflatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostflatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        val footballPlayers: MutableList<String?> = ArrayList()
        footballPlayers.add(0, "Select a player from the list")
        footballPlayers.add("Cristian Ronaldo")
        footballPlayers.add("Lionel Messi")
        footballPlayers.add("Neymar Jr")
        footballPlayers.add("Isco")
        footballPlayers.add("Gareth Bale")
        footballPlayers.add("Luis Suarez")

        val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(this, R.layout.simple_list_item_1, footballPlayers)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spFacing.adapter = arrayAdapter
        binding.spFacing.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (parent.getItemAtPosition(position) == "Choose Football players from lis") {
                }
                else {
                    val item = parent.getItemAtPosition(position).toString()
                    Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.btnPost.setOnClickListener{
            startActivity(Intent(this@PostFlatActivity, PostHighlightActivity::class.java))

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}