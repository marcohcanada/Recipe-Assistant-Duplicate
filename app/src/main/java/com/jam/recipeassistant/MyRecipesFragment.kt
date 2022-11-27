package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentMyRecipesBinding
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.model.Suggestions.DetailedRecipeCard

class MyRecipesFragment : Fragment() {

    lateinit var binding: FragmentMyRecipesBinding
    var recipeCards: MutableList<DetailedRecipeCard> = ArrayList()
    lateinit var adapter: UserRecipeAdapter

    var imgItems :MutableList<String> = ArrayList()
    var imgTypeItems :MutableList<String> = ArrayList()
    var recipeItems :MutableList<String> = ArrayList()
    var authorItems :MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRecipesBinding.inflate(this.layoutInflater, container, false)

        val userRecipeAdapter = UserRecipeAdapter(requireActivity(), imgItems, imgTypeItems, recipeItems, authorItems)
        val lv = binding.searchList
        adapter = userRecipeAdapter
        lv.adapter = adapter

        lv.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val imgItems = imgItems[position]
            //val imgTypeItems = imgTypeItems[position]
            val recipeItems = recipeItems[position]
            val authorItems = authorItems[position]
            val action = MyRecipesFragmentDirections.actionMyRecipesFragmentToDetailsFragment(imgItems, recipeItems, authorItems)
            findNavController().navigate(action)
        }

        SuggestionsAPI().GetUsersRecipesByUser(activity?.getFilesDir()!!.path, fun(input:MutableList<DetailedRecipeCard>) {
            recipeCards = input;
            //sourceRecipeItems.clear()
            recipeItems.clear()
            recipeItems.addAll(input.map { it.RecipeName })
            //sourceRecipeItems.addAll(input.map { it.RecipeName })

            //sourceAuthorItems.clear()
            authorItems.clear()
            authorItems.addAll(input.map { it.CreateUserName })
            //sourceAuthorItems.addAll(input.map { it.CreateUserName })

            //sourceImgItems.clear()
            imgItems.clear()
            imgItems.addAll(input.map { it.RecipeImage })
            //sourceImgItems.addAll(input.map { it.RecipeImage })

            //sourceImgTypeItems.clear()
            imgTypeItems.clear()
            imgTypeItems.addAll(input.map { it.RecipeImageType })
            //sourceImgTypeItems.addAll(input.map { it.RecipeImageType })
            activity?.runOnUiThread(java.lang.Runnable {
                userRecipeAdapter.notifyDataSetChanged()
            })
        })

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                /*binding.searchView.clearFocus()
                if ((recipeCards.map { it.RecipeName }).contains(query)){
                    recipeAdapter.filter.filter(query)
                }*/
                recipeItems.clear()
                authorItems.clear()
                imgItems.clear()
                imgTypeItems.clear()
                var searchItems : Array<String> = query!!.split(" ").toTypedArray()
                for (i in 0..recipeCards.size - 1) {
                    var addFlag = true
                    for (j in 0..searchItems.size - 1) {
                        if(searchItems[j] !in recipeCards[i].SearchDetailString) {
                            addFlag = false
                        }
                    }
                    if(addFlag) {
                        recipeItems.add(recipeCards[i].RecipeName)
                        authorItems.add(recipeCards[i].CreateUserName)
                        imgItems.add(recipeCards[i].RecipeImage)
                        imgTypeItems.add(recipeCards[i].RecipeImageType)
                        activity?.runOnUiThread(java.lang.Runnable {
                            userRecipeAdapter.notifyDataSetChanged()
                        })
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recipeItems.clear()
                authorItems.clear()
                imgItems.clear()
                imgTypeItems.clear()
                var searchItems : Array<String> = newText!!.split(" ").toTypedArray()
                for (i in 0..recipeCards.size - 1) {
                    var addFlag = true
                    for (j in 0..searchItems.size - 1) {
                        if(searchItems[j] !in recipeCards[i].SearchDetailString) {
                            addFlag = false
                        }
                    }
                    if(addFlag) {
                        recipeItems.add(recipeCards[i].RecipeName)
                        authorItems.add(recipeCards[i].CreateUserName)
                        imgItems.add(recipeCards[i].RecipeImage)
                        imgTypeItems.add(recipeCards[i].RecipeImageType)
                        activity?.runOnUiThread(java.lang.Runnable {
                            userRecipeAdapter.notifyDataSetChanged()
                        })
                    }
                }
                return false
            }


        })

        return binding.root
    }
}

