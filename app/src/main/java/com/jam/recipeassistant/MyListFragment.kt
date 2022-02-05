package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jam.recipeassistant.databinding.FragmentMyListBinding

class MyListFragment : Fragment() {

    lateinit var binding: FragmentMyListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyListBinding.inflate(this.layoutInflater, container, false)

        return binding.root
    }
}