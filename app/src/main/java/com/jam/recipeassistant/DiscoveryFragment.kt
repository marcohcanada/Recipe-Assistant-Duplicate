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
    var stopFlag = false

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

                recipeCards.addAll(input);
                if(input.size == 0) {
                    return
                }
                activity?.runOnUiThread(java.lang.Runnable {
                    recipeItems.clear()
                    authorItems.clear()
                    imgItems.clear()
                    imgTypeItems.clear()
                    likesItems.clear()
                    dislikesItems.clear()
                    viewsItems.clear()
                    ratingItems.clear()
                    warningsItems.clear()
                    SearchDetailStringItems.clear()
                    var searchItems : Array<String> = binding.searchView.query!!.split(" ").toTypedArray()
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
                            likesItems.add( "Likes: " + recipeCards[i].Likes )
                            dislikesItems.add( "Dislikes: " + recipeCards[i].Dislikes )
                            viewsItems.add("Views: " + recipeCards[i].Views )
                            ratingItems.add("Rating: " + recipeCards[i].Rating )
                            warningsItems.add(if (recipeCards[i].Severity == 1) "⚠ Warning ⚠" else ""  )
                            SearchDetailStringItems.add(recipeCards[i].SearchDetailString )

                                recipeAdapter.notifyDataSetChanged()

                        }
                    }
                })
                QueryMoreSuggestionsRecursive()
            })
            index+=6
        }

        if (index == 0) {
            index = 0
            SuggestionsAPI().getGeneralSuggestion(
                activity?.getFilesDir()!!.path,
                index,
                fun(input: MutableList<DetailedRecipeCard>) {
                    activity?.runOnUiThread(java.lang.Runnable {
                        recipeCards = input
                        recipeItems.clear()
                        authorItems.clear()
                        imgItems.clear()
                        imgTypeItems.clear()
                        likesItems.clear()
                        dislikesItems.clear()
                        viewsItems.clear()
                        ratingItems.clear()
                        warningsItems.clear()
                        SearchDetailStringItems.clear()
                        var searchItems : Array<String> = binding.searchView.query!!.split(" ").toTypedArray()
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
                                likesItems.add( "Likes: " + recipeCards[i].Likes )
                                dislikesItems.add( "Dislikes: " + recipeCards[i].Dislikes )
                                viewsItems.add("Views: " + recipeCards[i].Views )
                                ratingItems.add("Rating: " + recipeCards[i].Rating )
                                warningsItems.add(if (recipeCards[i].Severity == 1) "⚠ Warning ⚠" else ""  )
                                SearchDetailStringItems.add(recipeCards[i].SearchDetailString )

                                    recipeAdapter.notifyDataSetChanged()

                            }
                        }
                    })
                    index += 6
                    QueryMoreSuggestionsRecursive()
                })

            /*var searchItems : Array<String> = binding.searchView.query!!.split(" ").toTypedArray()
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
                    likesItems.add( "Likes: " + recipeCards[i].Likes )
                    dislikesItems.add( "Dislikes: " + recipeCards[i].Dislikes )
                    viewsItems.add("Views: " + recipeCards[i].Views )
                    ratingItems.add("Rating: " + recipeCards[i].Rating )
                    warningsItems.add(if (recipeCards[i].Severity == 1) "⚠ Warning ⚠" else ""  )
                    SearchDetailStringItems.add(recipeCards[i].SearchDetailString )
                    activity?.runOnUiThread(java.lang.Runnable {
                        recipeAdapter.notifyDataSetChanged()
                    })
                }
            }*/
            /*Thread {
                Thread.sleep(1000)
                activity?.runOnUiThread(java.lang.Runnable {
                    recipeAdapter.notifyDataSetChanged()
                })
            }.start()*/
        }


        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                /*binding.searchView.clearFocus()
                if ((recipeCards.map { it.RecipeName }).contains(query)){
                    recipeAdapter.filter.filter(query)
                }*/
                activity?.runOnUiThread(java.lang.Runnable {
                    recipeItems.clear()
                    authorItems.clear()
                    imgItems.clear()
                    imgTypeItems.clear()
                    likesItems.clear()
                    dislikesItems.clear()
                    viewsItems.clear()
                    ratingItems.clear()
                    warningsItems.clear()
                    SearchDetailStringItems.clear()
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
                            likesItems.add( "Likes: " + recipeCards[i].Likes )
                            dislikesItems.add( "Dislikes: " + recipeCards[i].Dislikes )
                            viewsItems.add("Views: " + recipeCards[i].Views )
                            ratingItems.add("Rating: " + recipeCards[i].Rating )
                            warningsItems.add(if (recipeCards[i].Severity == 1) "⚠ Warning ⚠" else ""  )
                            SearchDetailStringItems.add(recipeCards[i].SearchDetailString )

                                recipeAdapter.notifyDataSetChanged()

                        }
                    }
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                activity?.runOnUiThread(java.lang.Runnable {
                    recipeItems.clear()
                    authorItems.clear()
                    imgItems.clear()
                    imgTypeItems.clear()
                    likesItems.clear()
                    dislikesItems.clear()
                    viewsItems.clear()
                    ratingItems.clear()
                    warningsItems.clear()
                    SearchDetailStringItems.clear()
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
                            likesItems.add( "Likes: " + recipeCards[i].Likes )
                            dislikesItems.add( "Dislikes: " + recipeCards[i].Dislikes )
                            viewsItems.add("Views: " + recipeCards[i].Views )
                            ratingItems.add("Rating: " + recipeCards[i].Rating )
                            warningsItems.add(if (recipeCards[i].Severity == 1) "⚠ Warning ⚠" else ""  )
                            SearchDetailStringItems.add(recipeCards[i].SearchDetailString )

                                recipeAdapter.notifyDataSetChanged()

                        }
                    }
                })
                return false
            }


        })

        return binding.root
    }


}