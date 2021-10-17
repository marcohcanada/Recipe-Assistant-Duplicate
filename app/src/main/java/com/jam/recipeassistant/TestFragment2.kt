package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentTest1Binding
import com.jam.recipeassistant.databinding.FragmentTest2Binding

class TestFragment2 : Fragment() {

    lateinit var binding: FragmentTest2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTest2Binding.inflate(this.layoutInflater, container, false)

        binding.screen3btn.setOnClickListener {
            viewScreen3()
        }
        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }

        return binding.root
    }

    fun viewScreen3(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment3)
    }

    fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment1)
    }
}