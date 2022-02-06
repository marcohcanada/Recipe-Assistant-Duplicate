package com.jam.recipeassistant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentDetailsBinding
import com.jam.recipeassistant.model.Suggestions.RecipeDetails
import java.io.ByteArrayInputStream
import java.io.InputStream

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    lateinit var adapter1: IngredientAdapter
    lateinit var adapter2: StepAdapter

    var ingredientItems :MutableList<String> = ArrayList()

    var stepNumberItems :MutableList<String> = ArrayList()
    var stepItems :MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recipeItems = args.recipeItems

        binding = FragmentDetailsBinding.inflate(this.layoutInflater, container, false)
        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.ingredientsList
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1

        val stepAdapter = StepAdapter(requireActivity(), stepNumberItems ,stepItems)

        val lvSteps = binding.stepsList
        adapter2 = stepAdapter
        lvSteps.adapter = adapter2

        SuggestionsAPI().addView(recipeItems)

        SuggestionsAPI().GetRecipeDetails(recipeItems, activity?.getFilesDir()!!.path,fun(input: RecipeDetails) {
            ingredientItems.addAll(input.RecipeIngredients.map { it.RecipeIngredientAmount.toString() + " " + it.RecipeIngredientUnit + " " + it.IngredientName })
            stepNumberItems.addAll( input.RecipeSteps.map { it.StepNumber.toString() })
            stepItems.addAll( input.RecipeSteps.map { it.StepText })
            activity?.runOnUiThread(java.lang.Runnable {
                val imageData: ByteArray = Base64.decode(
                    input.RecipeImage.substring(input.RecipeImage.indexOf(",") + 1),
                    Base64.DEFAULT
                )
                val inputStream: InputStream = ByteArrayInputStream(imageData)
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                binding.image.setImageBitmap(bitmap)

                binding.recipeTitle.text = input.RecipeName

                binding.description.text = input.RecipeDescription
                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            })
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}