package com.gs.realestate.ui.home.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gs.realestate.databinding.FragmentHelpBinding
import com.gs.realestate.databinding.FragmentSignupBinding

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.tvNext.setOnClickListener {
//            findNavController().navigate(R.id.action_SignUpFragment_to_ListFragment)
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}