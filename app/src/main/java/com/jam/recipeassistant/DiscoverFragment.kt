package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    lateinit var manager: DialogflowManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(this.layoutInflater, container, false)
        manager = DialogflowManager()
        manager.initAssistant(this.requireContext())

        binding.screen2btn.setOnClickListener {
        }

        binding.screen3btn.setOnClickListener {
            viewScreen3()
        }

        return binding.root
    }

    fun viewScreen2(){
        findNavController().navigate(R.id.action_testFragment1_to_testFragment2)
    }


    fun viewScreen3(){
        findNavController().navigate(R.id.action_testFragment1_to_testFragment3)
    }
}