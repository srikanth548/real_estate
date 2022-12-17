package com.gs.realestate.ui.home.account

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gs.realestate.R
import com.gs.realestate.databinding.FragmentAccountBinding
import com.gs.realestate.ui.login.TermsAdapter
import com.gs.realestate.util.SnackBarToast

class AccountFragment : Fragment() {

    private lateinit var mContext: Context
    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPost.setOnClickListener {
            if(binding.cbTerms.isChecked){
                //TODO
            }else{
                SnackBarToast.showErrorSnackBar(it, getString(R.string.str_please_check_terms_to_continue))
            }
        }


        binding.cbTerms.setOnClickListener {
            binding.cbTerms.isChecked = false
            showTermsAndConditionsDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * Method to show terms and conditions to user
    * */
    private fun showTermsAndConditionsDialog() {
        val terms = this.resources.getStringArray(com.gs.realestate.R.array.terms)
        val adapter = TermsAdapter(mContext, terms)
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builderSingle.setTitle(getString(R.string.str_terms_conditions))

        builderSingle.setAdapter(adapter) { _, _ ->

        }
        builderSingle.setPositiveButton("Accept") { dialog, _ ->
            binding.cbTerms.isChecked = true
            dialog.dismiss()
        }
        builderSingle.setNegativeButton("cancel") { dialog, _ ->
            binding.cbTerms.isChecked = false
            dialog.dismiss()
        }
        builderSingle.show()
    }
}