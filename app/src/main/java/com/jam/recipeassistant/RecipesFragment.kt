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
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentRecipesBinding
import com.jam.recipeassistant.model.Suggestions.RecipeCard


class RecipesFragment : Fragment() {

    lateinit var binding: FragmentRecipesBinding
    var recipeCards: MutableList<RecipeCard> = ArrayList()
    lateinit var adapter: RecipeAdapter

    var imgItems :MutableList<String> = ArrayList()
    var recipeItems :MutableList<String> = ArrayList()
    var authorItems :MutableList<String> = ArrayList()

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

        /*recipeCards.add(
            RecipeCard(
                RecipeId = 1,
            RecipeName = "Beef Wellington",
        RecipeImage = "",
        RecipeDescription = "Juicy Rare Beef Wellington made by Gordon Recipe with mushrooms, salt, beef tenderloin",
        CreateUserName = "Gordon Ramsay",
        MonetaryScale= 3
        ))*/


        val recipeAdapter = RecipeAdapter(requireActivity(), imgItems, recipeItems, authorItems)


        //val recipeAdapter = RecipeAdapter(requireActivity(), recipeCards)
        val lv = binding.searchList
        adapter = recipeAdapter
        lv.adapter = adapter

        SuggestionsAPI().getGeneralSuggestion( fun(input:MutableList<RecipeCard>) {
            recipeCards = input;
            recipeItems.clear()
            recipeItems.addAll(input.map { it.RecipeName })
            authorItems.clear()
            authorItems.addAll(input.map { it.CreateUserName })
            imgItems.clear()
            imgItems.addAll(input.map { it.RecipeImage })
            activity?.runOnUiThread(java.lang.Runnable {
                recipeAdapter.notifyDataSetChanged()
            })
        })
        //)}!!

        //binding.searchList.adapter = recipeAdapter;

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if ((recipeCards.map { it.RecipeName }).contains(query)){
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

    /*fun returnedRecipeCards(input: MutableList<RecipeCard>) {
        recipeCards = input;

    }*/

    /*fun viewScreen3(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment3)
    }

    fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment2_to_testFragment1)
    }*/
}