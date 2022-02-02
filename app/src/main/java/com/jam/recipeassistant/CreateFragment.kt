package com.jam.recipeassistant

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jam.recipeassistant.api.SuggestionsAPI
//import com.jam.recipeassistant.databinding.FragmentCreateBinding
import com.jam.recipeassistant.model.Suggestions.RecipeDetails
import java.io.ByteArrayInputStream
import java.io.InputStream
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.databinding.FragmentRecipesBinding
import com.jam.recipeassistant.model.Suggestions.RecipeCard

class CreateFragment : Fragment() {

    lateinit var binding: FragmentRecipesBinding
    var recipeCards: MutableList<RecipeCard> = ArrayList()
    lateinit var adapter: UserRecipeAdapter

    var imgItems :MutableList<String> = ArrayList()
    var recipeItems :MutableList<String> = ArrayList()
    var authorItems :MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(this.layoutInflater, container, false)

        val recipeAdapter = UserRecipeAdapter(requireActivity(), imgItems, recipeItems, authorItems)

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

        SuggestionsAPI().GetUsersRecipesByUser( fun(input:MutableList<RecipeCard>) {
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

