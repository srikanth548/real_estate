package com.gs.realestate.ui.home.allproperties

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gs.realestate.R
import com.gs.realestate.base.BaseFragment
import com.gs.realestate.databinding.FragmentHomeBinding
import com.gs.realestate.network.ApiInterface
import com.gs.realestate.network.Properties
import com.gs.realestate.network.RetrofitClient
import com.gs.realestate.ui.home.property.PropertyDetailsActivity
import com.gs.realestate.ui.post.PostPropertyActivity
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.PreferenceHelper.messages
import com.gs.realestate.util.PreferenceHelper.sessionid
import com.gs.realestate.util.SnackBarToast

class AllPropertiesFrag : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    private val propertiesList = ArrayList<Properties>()
    private lateinit var propertyListAdap: PropertyListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        propertyListAdap = PropertyListAdapter(propertiesList, view.context)
        val layoutManager = LinearLayoutManager(view.context)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_listofitems)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = propertyListAdap
        getProperties()
        binding.btnPost.setOnClickListener {
            startActivity(Intent(activity, PropertyDetailsActivity::class.java))

//            startActivity(Intent(activity, PostPropertyActivity::class.java))
        }
//        binding.tvNext.setOnClickListener {
//            findNavController().navigate(R.id.action_SignUpFragment_to_ListFragment)
//        }

    }

    fun getProperties() {

//        val properties = Properties()
//        properties.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Sunflower_sky_backdrop.jpg/220px-Sunflower_sky_backdrop.jpg"
//        properties.expiryDate = "25/11/2022"
//        properties.location = "hyd"
//        properties.propertyType = "Agriculture"
//        properties.viewCount = 1
//        properties.favCount = 1
//        val del = Details()
//        del.unit = "Acres"
//        del.value = "10"
//        val del1 = Details()
//        del1.unit = "Lacs"
//        del1.value = "8"
//        properties.details = Arrays.asList(del,del1)
//        propertiesList.addAll(listOf(properties))
//        propertyListAdap.notifyDataSetChanged()

        activity?.let { context ->
            showLoader()
            val prefs = PreferenceHelper.customPreference(context)
            val retrofit = RetrofitClient.getInstance(context)
            val cookie =
                "messages" + prefs.messages + ";csrftoken=" + prefs.csrftoken + ";sessionid" + prefs.sessionid
            val apiInterface = retrofit.create(ApiInterface::class.java)
            lifecycleScope.launchWhenCreated {
                try {
                    val response = apiInterface.getMyProperties(cookie)
                    if (response.isSuccessful) {
                        //your code for handling success response
                        //response.body()?.data?.otp
                        response.body()?.properties?.let {
                            propertiesList.addAll(it)
                            propertyListAdap.notifyDataSetChanged()
                            Log.i("response {}", it.toString())
                        }
                        hideLoader()
                    } else {
                        activity?.let { SnackBarToast.failedCall(it) }
                        Log.i("failed {}", response.body().toString())
                        hideLoader()
                    }
                } catch (Ex: Exception) {
                    hideLoader()
                    Log.e("Error", Ex.localizedMessage ?: "")
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}