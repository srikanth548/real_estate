package com.gs.realestate.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gs.realestate.R
import com.gs.realestate.base.BaseFragment
import com.gs.realestate.databinding.FragmentConfirmotpBinding
import com.gs.realestate.network.*
import com.gs.realestate.ui.home.HomeActivity
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.authToken
import com.gs.realestate.util.PreferenceHelper.mobilenumber
import com.gs.realestate.util.SnackBarToast

class ConfirmOtpFragment : BaseFragment() {

    private lateinit var mContext: Context
    private var _binding: FragmentConfirmotpBinding? = null

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
        _binding = FragmentConfirmotpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btConfirm.setOnClickListener {
            if(binding.cbTerms.isChecked){
                otpVerification(it)
            }else{
                SnackBarToast.showErrorSnackBar(it, getString(R.string.str_please_check_terms_to_continue))
            }
            //SnackBarToast.showErrorSnackBar(it, getString(R.string.invalidOtp))
            // findNavController().navigate(R.id.action_SignUpFragment_to_LoginFragment)
        }


        binding.cbTerms.setOnClickListener {
            binding.cbTerms.isChecked = false
            showTermsAndConditionsDialog()
        }
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


    private fun otpVerification(view: View) {
        val prefs = activity?.let { PreferenceHelper.customPreference(it) }
        val otpMessage = binding.etEntermobile.editText?.text.toString()
        val mobileNumber = prefs?.mobilenumber

        if (otpMessage.isEmpty()) {
            binding.etEntermobile.error = "Please Enter Otp"
        } else {
            val otpData = OtpVerifyData(getString(R.string.logintype), mobileNumber, otpMessage)
            prefs?.mobilenumber = mobileNumber
            val otpVerifyOtp = OtpVerify(otpData)
            activity?.let { context ->
                showLoader()
                val retrofit = RetrofitClient.getInstance(context)
                val apiInterface = retrofit.create(ApiInterface::class.java)
                val authenticationRequest =
                    AuthenticationRequest(getString(R.string.logintype), mobileNumber)
                lifecycleScope.launchWhenCreated {
                    try {
                        val response = apiInterface.verifyOtp(otpVerifyOtp)
                        if (response.isSuccessful) {
                            hideLoader()
                            //your code for handling success response
                            //response.body()?.data?.otp
                            response.body()?.data?.status?.let { it ->
                                Log.i("response {}", it)
                                if (it.equals("otp_expired", ignoreCase = true)) {
                                    SnackBarToast.showErrorSnackBar(
                                        view,
                                        getString(R.string.invalidOtp)
                                    )
                                } else {
                                    val authResponse =
                                        apiInterface.authentication(authenticationRequest)
                                    if (authResponse.isSuccessful) {
                                        PreferenceHelper.customPreference(context).authToken = authResponse.body()?.token
                                        startActivity(Intent(activity, HomeActivity::class.java))
                                    } else {
                                        Log.i("Auth failed {}", response.body().toString())
                                        activity?.let { mainCont ->
                                            SnackBarToast.failedCall(
                                                mainCont
                                            )
                                        }
                                    }
                                }
                            }
                            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
                        } else {
                            hideLoader()
                            Log.i("failed {}", response.body().toString())
                            activity?.let { SnackBarToast.failedCall(it) }
                        }
                    } catch (Ex: Exception) {
                        hideLoader()
                        Log.e("Error", Ex.localizedMessage ?: "")
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}