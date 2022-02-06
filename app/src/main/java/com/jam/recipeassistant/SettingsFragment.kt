package com.jam.recipeassistant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jam.recipeassistant.api.LoginAPI
import com.jam.recipeassistant.databinding.FragmentSettingsBinding
import com.jam.recipeassistant.model.Login.Intolerances
import com.jam.recipeassistant.model.Login.UserLogin

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    lateinit var adapter1: IngredientAdapter
    var ingredientItems :MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(this.layoutInflater, container, false)
        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.listViewIntolerances
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1
        LoginAPI().GetUserIntolerances(activity?.getFilesDir()!!.path, fun(input: MutableList<Intolerances>) {
                activity?.runOnUiThread(java.lang.Runnable {
                    ingredientItems.addAll(input.map { it.ingredient + ",     Severity: " + (if (it.severity == 0) "Show" else (if (it.severity == 1) "Warn" else "Hide"))  })
                    adapter1.notifyDataSetChanged();
                })

        })
        binding.addIntolerance.setOnClickListener {
            LoginAPI().AddIntolerance(activity?.getFilesDir()!!.path, Intolerances(ingredient = binding.editTextIngredientName.text.toString(), severity = binding.seekBar.progress))
            var listItem : String = "";
            for(i in ingredientItems) {
                if (i.contains(binding.editTextIngredientName.text)) {
                    listItem = i
                    break
                }
            }
            if (listItem.isNotEmpty()) {
                ingredientItems.remove(listItem)
            }
            ingredientItems.add(binding.editTextIngredientName.text.toString() + ",     Severity: " + (if (binding.seekBar.progress == 0) "Show" else (if (binding.seekBar.progress == 1) "Warn" else "Hide")))
            adapter1.notifyDataSetChanged()
        }
        


        return binding.root
    }
}