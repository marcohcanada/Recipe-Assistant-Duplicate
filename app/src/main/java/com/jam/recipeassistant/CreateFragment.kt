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
import com.jam.recipeassistant.databinding.FragmentCreateBinding
import com.jam.recipeassistant.model.Suggestions.RecipeDetails
import java.io.ByteArrayInputStream
import java.io.InputStream

class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    lateinit var adapter1: IngredientAdapter
    lateinit var adapter2: StepAdapter

    var ingredientItems :MutableList<String> = ArrayList()

    var stepNumberItems :MutableList<String> = ArrayList()
    var stepItems :MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(this.layoutInflater, container, false)
        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.ingredientsList
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1

        val stepAdapter = StepAdapter(requireActivity(), stepNumberItems ,stepItems)

        val lvSteps = binding.stepsList
        adapter2 = stepAdapter
        lvSteps.adapter = adapter2

        SuggestionsAPI().GetRecipeDetails(fun(input: RecipeDetails) {
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

