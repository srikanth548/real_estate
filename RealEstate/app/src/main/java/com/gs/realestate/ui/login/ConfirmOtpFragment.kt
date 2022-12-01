package com.gs.realestate.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gs.realestate.R
import com.gs.realestate.base.BaseFragment
import com.gs.realestate.databinding.FragmentConfirmotpBinding
import com.gs.realestate.network.*
import com.gs.realestate.ui.home.HomeActivity
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.mobilenumber
import com.gs.realestate.util.SnackBarToast

class ConfirmOtpFragment : BaseFragment() {

    private var _binding: FragmentConfirmotpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            otpVerification(it)
            //SnackBarToast.showErrorSnackBar(it, getString(R.string.invalidOtp))
            // findNavController().navigate(R.id.action_SignUpFragment_to_LoginFragment)
        }

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