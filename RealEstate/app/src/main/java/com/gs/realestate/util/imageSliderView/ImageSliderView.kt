package com.gs.realestate.util.imageSliderView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.gs.realestate.databinding.LayoutImageSliderBinding

class ImageSliderView(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private var _binding: LayoutImageSliderBinding? = null


    init {
        _binding =
            LayoutImageSliderBinding.inflate(LayoutInflater.from(context), this, true)
    }


    fun setImagesList(imagesList: List<String>) {
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding?.rvImages?.apply {
            layoutManager = horizontalLayoutManager
            adapter = ImagesAdapter(context, imagesList)
        }

        _binding?.ivLeft?.setOnClickListener {
            if (horizontalLayoutManager.findFirstVisibleItemPosition() > 0) {
                _binding?.rvImages?.smoothScrollToPosition(horizontalLayoutManager.findFirstVisibleItemPosition() - 1);
            } else {
                _binding?.rvImages?.smoothScrollToPosition(0);
            }
        }

        _binding?.ivRight?.setOnClickListener {
            _binding?.rvImages?.smoothScrollToPosition(horizontalLayoutManager.findLastVisibleItemPosition() + 1);
        }
    }
}