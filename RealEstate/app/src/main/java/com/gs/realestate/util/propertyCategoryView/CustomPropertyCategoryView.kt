package com.gs.realestate.util.propertyCategoryView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.gs.realestate.databinding.LayoutPropertyCategorySelectionBinding


class CustomPropertyCategoryView(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    companion object {
        const val TYPE_AGRICULTURE = 0
        const val TYPE_RESIDENTIAL = 1
        const val TYPE_COMMERCIAL = 2
    }

    private var _binding: LayoutPropertyCategorySelectionBinding? = null

    init {
        _binding =
            LayoutPropertyCategorySelectionBinding.inflate(LayoutInflater.from(context), this, true)
        setDataToUI()
    }

    /*
    * Method to initialize tab change listener and update the category
    * */
    private fun setDataToUI() {
        _binding?.tlCategories?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateCategorySelection(position = tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


    /*
    * Method to update the selected category
    * */
    fun updateCategorySelection(position: Int) {
        when (position) {
            0 -> {
                _binding?.rgResidential?.clearCheck()
                _binding?.rgCommercial?.clearCheck()

                _binding?.rgAgriculture?.visibility = View.VISIBLE
                _binding?.rgResidential?.visibility = View.GONE
                _binding?.rgCommercial?.visibility = View.GONE

            }
            1 -> {
                _binding?.rgAgriculture?.clearCheck()
                _binding?.rgCommercial?.clearCheck()

                _binding?.rgAgriculture?.visibility = View.GONE
                _binding?.rgResidential?.visibility = View.VISIBLE
                _binding?.rgCommercial?.visibility = View.GONE
            }
            2 -> {
                _binding?.rgResidential?.clearCheck()
                _binding?.rgAgriculture?.clearCheck()

                _binding?.rgAgriculture?.visibility = View.GONE
                _binding?.rgResidential?.visibility = View.GONE
                _binding?.rgCommercial?.visibility = View.VISIBLE
            }
        }
    }


    /*
    * Method to get the selected Category option text
    * */
    fun getSelectedOption(): Pair<Int, String>? {
        when (_binding?.tlCategories?.selectedTabPosition) {
            0 -> {
                _binding?.rgAgriculture?.checkedChipId?.let {
                    val selectedItem =
                        _binding?.rgAgriculture?.findViewById<Chip>(it)?.text.toString()
                    return Pair(TYPE_AGRICULTURE, if(selectedItem == "null") "" else selectedItem)
                }
            }
            1 -> {
                _binding?.rgResidential?.checkedChipId?.let {
                    val selectedItem =
                        _binding?.rgResidential?.findViewById<Chip>(it)?.text.toString()
                    return Pair(TYPE_RESIDENTIAL, if(selectedItem == "null") "" else selectedItem)
                }
            }
            2 -> {
                _binding?.rgCommercial?.checkedChipId?.let {
                    val selectedItem =
                        _binding?.rgCommercial?.findViewById<Chip>(it)?.text.toString()
                    return Pair(TYPE_COMMERCIAL, if(selectedItem == "null") "" else selectedItem)
                }
            }
        }

        return null
    }


}