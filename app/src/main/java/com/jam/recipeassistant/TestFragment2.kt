package com.jam.recipeassistant

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment

import com.jam.recipeassistant.databinding.FragmentTest2Binding

class TestFragment2 : Fragment() {

    lateinit var binding: FragmentTest2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTest2Binding.inflate(this.layoutInflater, container, false)

        /*binding.screen3btn.setOnClickListener {
            viewScreen3()
        }
        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }*/

        var items:Array<String> = arrayOf("Beef Strogenof","Beef Wellington","Oven Baked Salmon","Ceaser Salad","Over Easy Egg","Eggs Benedict","Chicken Karage","Kung-Pow Chicken","Chicken Noodle Soup")

        val recipeAdapter : ArrayAdapter<String> = activity?.let {ArrayAdapter(it,android.R.layout.simple_list_item_1,
            items
        )}!!

        binding.searchList.adapter = recipeAdapter;

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (items.contains(query)){
                    recipeAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recipeAdapter.filter.filter(newText)
                return false
            }


        })

        return binding.root
    }

    /*fun viewScreen3(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment3)
    }

    fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment1)
    }*/
}