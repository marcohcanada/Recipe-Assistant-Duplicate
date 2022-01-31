package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jam.recipeassistant.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(this.layoutInflater, container, false)

        return binding.root
    }


    /*fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment3_to_discoverFragment)
        /*var conn = SQLConnection()
        var result = conn.executeQuery("SELECT * FROM Ingredient");
        if(result != null) {
            result.next()
            System.out.println(result.getString(2))
        }*/
        //findNavController().navigate(R.id.action_testFragment3_to_testFragment1)
    }*/
}

