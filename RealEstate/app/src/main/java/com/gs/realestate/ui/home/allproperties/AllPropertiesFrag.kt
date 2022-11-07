package com.gs.realestate.ui.home.allproperties

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gs.realestate.R
import com.gs.realestate.databinding.FragmentHomeBinding
import com.gs.realestate.databinding.FragmentListpropertyBinding
import com.gs.realestate.databinding.FragmentSignupBinding
import com.gs.realestate.network.*
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.PreferenceHelper.messages
import com.gs.realestate.util.PreferenceHelper.mobilenumber
import com.gs.realestate.util.PreferenceHelper.sessionid
import com.gs.realestate.util.SnackBarToast

class AllPropertiesFrag : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val propertiesList = ArrayList<Properties>()
    private lateinit var propertyListAdap: PropertyListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        propertyListAdap = PropertyListAdapter(propertiesList,view.context)
        val layoutManager = LinearLayoutManager(view.context)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_listofitems)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = propertyListAdap
        getProperties()
        binding.btnPost.setOnClickListener{

        }
//        binding.tvNext.setOnClickListener {
//            findNavController().navigate(R.id.action_SignUpFragment_to_ListFragment)
//        }

    }

    fun getProperties() {


            activity?.let { context ->
                val prefs = PreferenceHelper.customPreference(context)
                val retrofit = RetrofitClient.getInstance(context)
                val cookie = "messages"+prefs.messages+";csrftoken="+prefs.csrftoken+";sessionid"+prefs.sessionid
                val apiInterface = retrofit.create(ApiInterface::class.java)
                lifecycleScope.launchWhenCreated {
                    try {
                        val response = apiInterface.getMyProperties(cookie)
                        if (response.isSuccessful()) {
                            //your code for handaling success response
                            //response.body()?.data?.otp
                            response.body()?.properties?.let {
                                propertiesList.addAll(it)
                                propertyListAdap.notifyDataSetChanged()
                                Log.i("response {}", it.toString()) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}