package com.gs.realestate.util.customEditText

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.gs.realestate.R
import com.gs.realestate.databinding.LayoutCustomEditTextBinding


class CustomEditText(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var binding: LayoutCustomEditTextBinding


    /*
    * Variable to set the text to edit text
    * */
    private var text: String = ""
    set(value){
        field = value
        binding.etContent.setText(value)
    }


    /*
    * Variable to set the title above edit text
    * */
    private var title: String = ""
        set(value) {
            field = value
            binding.tvTitle.text = value
        }


    /*
    * Variable to set the hint to edit text
    * */
    private var hint: String = ""
        set(value) {
            field = value
            binding.etContent.hint = value
        }


    /*
    * Variable to set enable/disable of edit text
    * */
    private var isEditEnabled: Boolean = true
        set(value) {
            field = value
            binding.etContent.isEnabled = value
            binding.etContent.background = if (value) ContextCompat.getDrawable(
                context,
                R.drawable.box_bg
            ) else ContextCompat.getDrawable(context, R.drawable.disabled_box_bg)
        }


    /*
    * Variable to set the suffix text
    * */
    private var suffixText: String = ""
        set(value) {
            field = value
            binding.tvSuffix.text = value
            binding.tvSuffix.visibility = if (value.isEmpty()) View.GONE else View.VISIBLE
        }


    /*
    * Variable to enable the switch in edit text
    * */
    private var switchEnabled: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.tbSwitch.visibility = View.VISIBLE
                binding.tvSwitchLabel.visibility = View.VISIBLE
            } else {
                binding.tbSwitch.visibility = View.GONE
                binding.tvSwitchLabel.visibility = View.GONE
            }
        }


    /*
    * Variable to display the text when switch is on
    * */
    private var switchOnText: String = ""
        set(value) {
            field = value
            binding.tvSwitchLabel.text = value.ifEmpty { "On" }
        }

    /*
    * Variable to display the text when switch is off
    * */
    private var switchOffText: String = ""
        set(value) {
            field = value
            binding.tvSwitchLabel.text = value.ifEmpty { "Off" }
        }


    /*
    * Variable to display the auto suggestions
    * */
    var autoCompleteSuggestions: Array<String> = arrayOf()
        set(value) {
            field = value
            //Creating the instance of ArrayAdapter containing list of suggestions
            val suggestionsAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(context, android.R.layout.select_dialog_item, value)
            binding.etContent.threshold = 1
            binding.etContent.setAdapter(suggestionsAdapter)
            binding.etContent.isFocusable = false
            binding.etContent.setOnTouchListener { p0, p1 ->
                binding.etContent.showDropDown()
                return@setOnTouchListener true
            }
            binding.etContent.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_keyboard_arrow_down_24,
                0
            )
        }


    /*
    * Variable to enable multi line edit text
    * */
    private var isMultilineView: Boolean = false
        set(value) {
            field = value
            binding.etContent.isSingleLine = false
            binding.etContent.setLines(if (value) 4 else 1)
        }


    /*
    * Variable to set the input type
    * */
    private var inputType: String = ""
        set(value) {
            field = value
            if (inputType.isEmpty() || inputType == "0")
                binding.etContent.inputType = InputType.TYPE_CLASS_TEXT
            else if (inputType == "1")
                binding.etContent.inputType = InputType.TYPE_CLASS_NUMBER
            else if (inputType == "2")
                binding.etContent.inputType =
                    InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            else
                binding.etContent.inputType = InputType.TYPE_CLASS_TEXT
        }


    init {
        binding = LayoutCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

        //R.styleable has to be the same name as it is in attrs.xml
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomEditText).let {

            title = it.getString(R.styleable.CustomEditText_edit_text_title).orEmpty()
            hint = it.getString(R.styleable.CustomEditText_edit_text_hint).orEmpty()
            isEditEnabled = it.getBoolean(R.styleable.CustomEditText_edit_text_enabled, true)
            suffixText = it.getString(R.styleable.CustomEditText_edit_text_suffix).orEmpty()
            switchEnabled =
                it.getBoolean(R.styleable.CustomEditText_edit_text_switch_enabled, false)
            switchOnText =
                it.getString(R.styleable.CustomEditText_edit_text_switch_on_text).orEmpty()
            switchOffText =
                it.getString(R.styleable.CustomEditText_edit_text_switch_off_text).orEmpty()
            inputType = it.getString(R.styleable.CustomEditText_edit_text_input_type).orEmpty()
            isMultilineView =
                it.getBoolean(R.styleable.CustomEditText_edit_text_multi_line_view, false)

            it.recycle()
        }


        dataModifiers()
    }


    private fun dataModifiers() {
        binding.tbSwitch.setOnCheckedChangeListener { _, b ->
            binding.tvSwitchLabel.text = if (b) switchOnText else switchOffText
        }

        binding.etContent.setOnFocusChangeListener { view, isFocused ->
            view.background = if (isFocused) ContextCompat.getDrawable(
                context,
                R.drawable.focusable_box_bg
            ) else ContextCompat.getDrawable(context, R.drawable.box_bg)

            if (autoCompleteSuggestions.isNotEmpty()) {
                if (isFocused) {
                    binding.etContent.showDropDown()
                }
            }
        }
    }


    fun setInputText(value: String){
        text = value
    }


    fun getText(): String {
        return binding.etContent.text.toString()
    }

    fun isValidText(): Boolean {
        return binding.etContent.text.toString().isNotBlank()
    }

    fun getSwitchText(): String {
        return binding.tvSwitchLabel.text.toString()
    }

}