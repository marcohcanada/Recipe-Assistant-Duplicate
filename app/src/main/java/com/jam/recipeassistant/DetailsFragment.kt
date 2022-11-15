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
import android.net.Uri
import android.util.Base64
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentDetailsBinding
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import com.jam.recipeassistant.model.Suggestions.RecipeDetails
import com.squareup.picasso.Picasso
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

        SuggestionsAPI().addView(activity?.getFilesDir()!!.path, recipeItems)

        SuggestionsAPI().GetRecipeDetails(recipeItems, activity?.getFilesDir()!!.path,fun(input: RecipeDetails) {
            ingredientItems.addAll(input.RecipeIngredients.map { it.RecipeIngredientAmount.toString() + " " + it.RecipeIngredientUnit + " " + it.IngredientName })
            stepNumberItems.addAll( input.RecipeSteps.map { it.StepNumber.toString() })
            stepItems.addAll( input.RecipeSteps.map { it.StepText })
            activity?.runOnUiThread(java.lang.Runnable {

                if(input.RecipeImageType == "WEB") {
                    Picasso.with(context).load(input.RecipeImage).resize(250, 250).into(binding.image);
                } else if(input.RecipeImageType == "SVG") {
                    val imageData: ByteArray = Base64.decode(
                        input.RecipeImage.substring(input.RecipeImage.indexOf(",") + 1),
                        Base64.DEFAULT
                    )
                    val inputStream: InputStream = ByteArrayInputStream(imageData)
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.image.setImageBitmap(bitmap)
                } else if(input.RecipeImageType == "BYTEARRAY") {
                    binding.image.setImageBitmap(BitmapFactory.decodeByteArray(input.RecipeImage.toByteArray(), 0, input.RecipeImage.toByteArray().size))
                }


                binding.recipeTitle.text = input.RecipeName

                binding.description.text = input.RecipeDescription
                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            })
        })

        SuggestionsAPI().GetRelatedContent(args.recipeItems, activity?.getFilesDir()!!.path, fun(input:MutableList<RecipeCard>) {

            activity?.runOnUiThread(java.lang.Runnable {
                binding.recText1.text = input[0].RecipeName
                if(input[0].RecipeImageType == "WEB") {
                    Picasso.with(context).load(input[0].RecipeImage).resize(250, 250).into(binding.recImage1);
                } else if(input[0].RecipeImageType == "SVG"){
                    var imageData: ByteArray = Base64.decode(input[0].RecipeImage.substring(input[0].RecipeImage.indexOf(",") + 1), Base64.DEFAULT)
                    val inputStream: InputStream = ByteArrayInputStream(imageData)
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.recImage1.setImageBitmap(bitmap)
                } else if(input[0].RecipeImageType == "BYTEARRAY") {
                    binding.recImage1.setImageBitmap(BitmapFactory.decodeByteArray(input[0].RecipeImage.toByteArray(), 0, input[0].RecipeImage.toByteArray().size))
                }

                binding.recText2.text = input[1].RecipeName
                if(input[1].RecipeImageType == "WEB") {
                    Picasso.with(context).load(input[1].RecipeImage).resize(250, 250).into(binding.recImage2);
                } else if(input[1].RecipeImageType == "SVG"){
                    var imageData: ByteArray = Base64.decode(input[1].RecipeImage.substring(input[1].RecipeImage.indexOf(",") + 1), Base64.DEFAULT)
                    val inputStream: InputStream = ByteArrayInputStream(imageData)
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.recImage2.setImageBitmap(bitmap)
                } else if(input[1].RecipeImageType == "BYTEARRAY") {
                    binding.recImage2.setImageBitmap(BitmapFactory.decodeByteArray(input[1].RecipeImage.toByteArray(), 0, input[1].RecipeImage.toByteArray().size))
                }

                binding.recText3.text = input[2].RecipeName
                if(input[2].RecipeImageType == "WEB") {
                    Picasso.with(context).load(input[2].RecipeImage).resize(250, 250).into(binding.recImage3);
                } else if(input[2].RecipeImageType == "SVG"){
                    var imageData: ByteArray = Base64.decode(input[2].RecipeImage.substring(input[2].RecipeImage.indexOf(",") + 1), Base64.DEFAULT)
                    val inputStream: InputStream = ByteArrayInputStream(imageData)
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.recImage3.setImageBitmap(bitmap)
                } else if(input[2].RecipeImageType == "BYTEARRAY") {
                    binding.recImage3.setImageBitmap(BitmapFactory.decodeByteArray(input[2].RecipeImage.toByteArray(), 0, input[2].RecipeImage.toByteArray().size))
                }
            })
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}