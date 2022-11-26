package com.jam.recipeassistant

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentDiscoveryBinding
import com.jam.recipeassistant.model.Suggestions.DetailedRecipeCard
import com.jam.recipeassistant.model.Suggestions.RecipeCard


class DiscoveryFragment : Fragment() {

    lateinit var binding: FragmentDiscoveryBinding
    var recipeCards: MutableList<DetailedRecipeCard> = ArrayList()
    lateinit var adapter: RecipeAdapter

    var imgItems :MutableList<String> = ArrayList()
    var imgTypeItems :MutableList<String> = ArrayList()
    var recipeItems :MutableList<String> = ArrayList()
    var authorItems :MutableList<String> = ArrayList()
    var likesItems :MutableList<String> = ArrayList()
    var dislikesItems :MutableList<String> = ArrayList()
    var viewsItems :MutableList<String> = ArrayList()
    var ratingItems :MutableList<String> = ArrayList()
    var warningsItems :MutableList<String> = ArrayList()
    var SearchDetailStringItems :MutableList<String> = ArrayList()
    var index :Int = 0
    var stopFlag = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(this.layoutInflater, container, false)

        val recipeAdapter = RecipeAdapter(requireActivity(), imgItems, imgTypeItems, recipeItems, authorItems,
            likesItems, dislikesItems, viewsItems, ratingItems, warningsItems)

        val lv = binding.searchList
        adapter = recipeAdapter
        lv.adapter = adapter

        lv.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val imgItems = imgItems[position]
            //val imgTypeItems = imgTypeItems[position]
            val recipeItems = recipeItems[position]
            val authorItems = authorItems[position]
            val action = DiscoveryFragmentDirections.actionDiscoveryFragmentToDetailsFragment(imgItems, recipeItems, authorItems)
            findNavController().navigate(action)
        }

        fun QueryMoreSuggestionsRecursive () {
            SuggestionsAPI().getGeneralSuggestion(activity?.getFilesDir()!!.path, index, fun(input:MutableList<DetailedRecipeCard>) {
                recipeCards = input;
                if(input.size == 0) {
                    return
                }
                recipeItems.addAll(input.map { it.RecipeName })
                authorItems.addAll(input.map { it.CreateUserName })
                imgItems.addAll(input.map { it.RecipeImage })
                imgTypeItems.addAll(input.map { it.RecipeImageType })
                likesItems.addAll(input.map { "Likes: " + it.Likes })
                dislikesItems.addAll(input.map { "Dislikes: " + it.Dislikes })
                viewsItems.addAll(input.map { "Views: " + it.Views })
                ratingItems.addAll(input.map { "Rating: " + it.Rating })
                warningsItems.addAll(input.map { if (it.Severity == 1) "⚠ Warning ⚠" else ""  })
                SearchDetailStringItems.addAll(input.map { it.SearchDetailString })
                activity?.runOnUiThread(java.lang.Runnable {
                    recipeAdapter.notifyDataSetChanged()
                })
                index+=5
                QueryMoreSuggestionsRecursive()
            })


        }

        SuggestionsAPI().getGeneralSuggestion(activity?.getFilesDir()!!.path, index, fun(input:MutableList<DetailedRecipeCard>) {
            recipeCards = input;
            recipeItems.clear()
            recipeItems.addAll(input.map { it.RecipeName })
            authorItems.clear()
            authorItems.addAll(input.map { it.CreateUserName })
            imgItems.clear()
            imgItems.addAll(input.map { it.RecipeImage })
            imgTypeItems.clear()
            imgTypeItems.addAll(input.map { it.RecipeImageType })
            likesItems.clear()
            likesItems.addAll(input.map { "Likes: " + it.Likes })
            dislikesItems.clear()
            dislikesItems.addAll(input.map { "Dislikes: " + it.Dislikes })
            viewsItems.clear()
            viewsItems.addAll(input.map { "Views: " + it.Views })
            ratingItems.clear()
            ratingItems.addAll(input.map { "Rating: " + it.Rating })
            warningsItems.clear()
            warningsItems.addAll(input.map { if (it.Severity == 1) "⚠ Warning ⚠" else ""  })
            SearchDetailStringItems.clear()
            SearchDetailStringItems.addAll(input.map { it.SearchDetailString })
            activity?.runOnUiThread(java.lang.Runnable {
                recipeAdapter.notifyDataSetChanged()
            })
            index+=5
            QueryMoreSuggestionsRecursive()
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