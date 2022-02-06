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
    var likesItems :MutableList<String> = ArrayList()
    var dislikesItems :MutableList<String> = ArrayList()
    var viewsItems :MutableList<String> = ArrayList()
    var warningsItems :MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(this.layoutInflater, container, false)

        val recipeAdapter = RecipeAdapter(requireActivity(), imgItems, recipeItems, authorItems,
            likesItems, dislikesItems, viewsItems, warningsItems)

        val lv = binding.searchList
        adapter = recipeAdapter
        lv.adapter = adapter

        lv.setOnItemClickListener { parent, view, position, id ->
            val imgItems = imgItems[position]
            val recipeItems = recipeItems[position]
            val authorItems = authorItems[position]
            val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsFragment(imgItems, recipeItems, authorItems)
            findNavController().navigate(action)
        }

        SuggestionsAPI().getGeneralSuggestion(activity?.getFilesDir()!!.path, fun(input:MutableList<RecipeCard>) {
            recipeCards = input;
            recipeItems.clear()
            recipeItems.addAll(input.map { it.RecipeName })
            authorItems.clear()
            authorItems.addAll(input.map { it.CreateUserName })
            imgItems.clear()
            imgItems.addAll(input.map { it.RecipeImage })
            likesItems.clear()
            likesItems.addAll(input.map { "Likes: " + it.Likes })
            dislikesItems.clear()
            dislikesItems.addAll(input.map { "Dislikes: " + it.Dislikes })
            viewsItems.clear()
            viewsItems.addAll(input.map { "Views: " + it.Views })
            warningsItems.clear()
            warningsItems.addAll(input.map { if (it.Severity == 1) "⚠️Warning ⚠️" else ""  })
            activity?.runOnUiThread(java.lang.Runnable {
                recipeAdapter.notifyDataSetChanged()
            })
        })

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
}