package com.gs.realestate.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gs.realestate.R
import com.gs.realestate.databinding.FragmentLoginBinding
import com.gs.realestate.network.*
import com.gs.realestate.ui.home.HomeActivity
import com.gs.realestate.util.PreferenceHelper.customPreference
import com.gs.realestate.util.PreferenceHelper.mobilenumber
import com.gs.realestate.util.SnackBarToast
import java.util.Objects

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener{
            getOtp()
          //  startActivity(Intent(this.activity, HomeActivity::class.java))
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getOtp() {
        val mobileNumber = binding.loginmobilenum.text.toString()
        val prefs = activity?.let { customPreference(it) }
        if(mobileNumber.isEmpty()){
            binding.etEntermobile.error = "Please Enter Mobile Number"
        }else {

            activity?.let { context ->
                val otpData = OtpData(getString(R.string.logintype), mobileNumber)
                prefs?.mobilenumber = mobileNumber
                val otpRequest = OtpRequest(otpData)
                val retrofit = RetrofitClient.getInstance(context)
                val apiInterface = retrofit.create(ApiInterface::class.java)
                lifecycleScope.launchWhenCreated {
                    try {
                        val response = apiInterface.getOtp(otpRequest)
                        if (response.isSuccessful()) {
                            //your code for handaling success response
                            //response.body()?.data?.otp
                            response.body()?.data?.otp?.let { Log.i("response {}", it) }
                            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
                        } else {
                            activity?.let { SnackBarToast.failedCall(it) }
                            Log.i("failed {}", response.body().toString())

                        }
                    } catch (Ex: Exception) {
                        Log.e("Error", Ex.localizedMessage)
                    }
                }
            }
        }
    }


}