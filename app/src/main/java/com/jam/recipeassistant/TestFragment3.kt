package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentTest2Binding
import com.jam.recipeassistant.databinding.FragmentTest3Binding

class TestFragment3 : Fragment() {

    lateinit var binding: FragmentTest3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTest3Binding.inflate(this.layoutInflater, container, false)

        binding.screen2btn.setOnClickListener {
            viewScreen2()
        }
        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }

        return binding.root
    }

    fun viewScreen2(){
        findNavController().navigate(R.id.action_testFragment3_to_testFragment2)
    }

    fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment3_to_testFragment1)
    }
}