package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentTest1Binding

class TestFragment1 : Fragment() {

    lateinit var binding: FragmentTest1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTest1Binding.inflate(this.layoutInflater, container, false)

        binding.screen2btn.setOnClickListener {
            viewScreen2()
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