package com.jam.recipeassistant

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jam.recipeassistant.api.RecipeManagementAPI
import com.jam.recipeassistant.databinding.FragmentMyListBinding

class MyListFragment : Fragment() {

    lateinit var binding: FragmentMyListBinding
    lateinit var adapter1: IngredientAdapter
    lateinit var adapter2: StepAdapter
    var ingredientItems :MutableList<String> = ArrayList()
    var stepNumberItems :MutableList<String> = ArrayList()
    var stepItems :MutableList<String> = ArrayList()
    val pickImage = 100
    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyListBinding.inflate(this.layoutInflater, container, false)

        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.listViewIngredients
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1

        val stepAdapter = StepAdapter(requireActivity(), stepNumberItems ,stepItems)

        val lvSteps = binding.listViewSteps
        adapter2 = stepAdapter
        lvSteps.adapter = adapter2

        binding.addIngredient.setOnClickListener {
            ingredientItems.add((binding.editTextTextQuantity.text.toString() + " " + binding.editTextTextMetric.text + " " + binding.editTextTextIngredient.text));
            binding.editTextTextQuantity.setText("")
            binding.editTextTextMetric.setText("")
            binding.editTextTextIngredient.setText("")
            adapter1.notifyDataSetChanged()
        }

        binding.btnAddImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.addSteps.setOnClickListener {
            stepNumberItems.add((stepNumberItems.count() +1).toString())
            stepItems.add(binding.editTextTextStep.text.toString())
            binding.editTextTextStep.setText("")
            adapter2.notifyDataSetChanged()
        }

        binding.CreateRecipe.setOnClickListener {
            var json = "{\n" +
                    "  \"recipeId\": 0,\n" +
                    "  \"recipeName\": \""+binding.editTextTextRecipeName.text+"\",\n" +
                    "  \"recipeImage\": \"data:image/png;base64,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\",\n" +
                    "  \"recipeDescription\": \""+binding.editTextTextDescription.text+"\",\n" +
                    "  \"createUserName\": \"Adrian\",\n" +
                    "  \"monetaryScale\": "+binding.seekBarMonetaryScale.progress+",\n" +
                    "  \"recipeIngredients\": [\n";
                    json += (adapter1.getAsJson().substring(0, adapter1.getAsJson().length-1) + "\n],\n")


                    json += "  \"recipeSteps\": [\n" + (adapter2.getAsJson().substring(0, adapter2.getAsJson().length-1) + "\n]\n}")
            RecipeManagementAPI().CreateNewRecipe(json)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.ivAddImage.setImageURI(imageUri)
        }
    }
}