package com.gs.realestate.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gs.realestate.R
import com.gs.realestate.databinding.FragmentSettingsBinding
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.baseUrl
import com.gs.realestate.util.SnackBarToast

class SettingsFragement : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = activity?.let { PreferenceHelper.customPreference(it) }
        binding.etBaseUrl.setInputText(prefs?.baseUrl ?: "")


        binding.btnPost.setOnClickListener {
            val enteredUrl = binding.etBaseUrl.getText()
            if (enteredUrl.isNotBlank()) {
                prefs?.baseUrl = enteredUrl
                Toast.makeText(context, getString(R.string.str_updated_success), Toast.LENGTH_SHORT)
                    .show()
            } else {
                SnackBarToast.showErrorSnackBar(
                    it,
                    getString(R.string.str_please_enter_url_to_save)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}