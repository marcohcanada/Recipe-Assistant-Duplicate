package com.jam.recipeassistant

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentRecipesBinding


class RecipesFragment : Fragment() {

    lateinit var binding: FragmentRecipesBinding

    val imgItems = arrayOf<Int>(R.drawable.beefwellington, R.drawable.ovenbakedsalmon)
    val recipeItems = arrayOf<String>("Beef Wellington","Oven-Baked Salmon")
    val authorItems = arrayOf<String>("By Gordon Ramsay", "By Insanely Good Recipes")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipesBinding.inflate(this.layoutInflater, container, false)

        /*binding.screen3btn.setOnClickListener {
            viewScreen3()
        }
        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }*/

        /*val items:Array<String> = arrayOf("Beef Strogenof","Beef Wellington","Oven Baked Salmon","Ceaser Salad","Over Easy Egg","Eggs Benedict","Chicken Karage","Kung-Pow Chicken","Chicken Noodle Soup")*/

        /*val recipeAdapter : ArrayAdapter<String> = activity?.let {ArrayAdapter(it,android.R.layout.simple_list_item_1,
            recipeItems*/
        val recipeAdapter = RecipeAdapter(requireActivity(), imgItems, recipeItems, authorItems)
        val lv = binding.searchList
        lv.adapter = recipeAdapter
        //)}!!

        //binding.searchList.adapter = recipeAdapter;

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (recipeItems.contains(query)){
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