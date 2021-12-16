package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentCreateBinding
import com.jam.recipeassistant.model.SQLConnection

class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(this.layoutInflater, container, false)

        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }
        //ask internet permission

        return binding.root
    }

    fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment3_to_discoverFragment)
        /*var conn = SQLConnection()
        var result = conn.executeQuery("SELECT * FROM Ingredient");
        if(result != null) {
            result.next()
            System.out.println(result.getString(2))
        }*/
        //findNavController().navigate(R.id.action_testFragment3_to_testFragment1)
    }
}