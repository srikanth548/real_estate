package com.gs.realestate.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gs.realestate.R
import com.gs.realestate.base.BaseFragment
import com.gs.realestate.databinding.FragmentLoginBinding
import com.gs.realestate.network.ApiInterface
import com.gs.realestate.network.OtpData
import com.gs.realestate.network.OtpRequest
import com.gs.realestate.network.RetrofitClient
import com.gs.realestate.util.PreferenceHelper.customPreference
import com.gs.realestate.util.PreferenceHelper.mobilenumber
import com.gs.realestate.util.SnackBarToast

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener {
            getOtp()
            //  startActivity(Intent(this.activity, HomeActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getOtp() {
        val mobileNumber = binding.loginmobilenum.text.toString()
        val prefs = activity?.let { customPreference(it) }
        if (mobileNumber.isEmpty()) {
            binding.etEntermobile.error = "Please Enter Mobile Number"
        } else {

            activity?.let { context ->
                showLoader()

                val otpData = OtpData(getString(R.string.logintype), mobileNumber)
                prefs?.mobilenumber = mobileNumber
                val otpRequest = OtpRequest(otpData)
                val retrofit = RetrofitClient.getInstance(context)
                val apiInterface = retrofit.create(ApiInterface::class.java)
                lifecycleScope.launchWhenCreated {
                    try {
                        val response = apiInterface.getOtp(otpRequest)
                        if (response.isSuccessful) {
                            hideLoader()
                            //your code for handling success response
                            //response.body()?.data?.otp
                            response.body()?.data?.otp?.let { Log.i("response {}", it) }
                            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
                        } else {
                            hideLoader()
                            activity?.let { SnackBarToast.failedCall(it) }
                            Log.i("failed {}", response.body().toString())

                        }
                    } catch (Ex: Exception) {
                        hideLoader()
                        Log.e("Error", Ex.localizedMessage ?: "")
                    }
                }
            }
        }
    }


}