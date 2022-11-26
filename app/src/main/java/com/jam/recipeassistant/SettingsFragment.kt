package com.jam.recipeassistant

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import com.jam.recipeassistant.api.RecipeManagementAPI
import com.jam.recipeassistant.api.UserManagementAPI
import com.jam.recipeassistant.databinding.FragmentSettingsBinding
import com.jam.recipeassistant.model.Shared.SimpleStringList
import com.jam.recipeassistant.model.Suggestions.DetailedRecipeCard
import com.jam.recipeassistant.model.UserManagement.Intolerances
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast


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
        UserManagementAPI().GetUserIntolerances(activity?.getFilesDir()!!.path, fun(input: MutableList<Intolerances>) {
                activity?.runOnUiThread(java.lang.Runnable {
                    ingredientItems.addAll(input.map { (if (it.severity == 1) "Warn" else "Hide") + " When Recipe " + (if (it.metric == null) "Contains" else "Uses More Than " + it.amount + " " + it.metric + " Of" ) + " " + it.ingredient})
                    adapter1.notifyDataSetChanged()
                })

        })
        binding.addIntolerance.setOnClickListener {
            var contentOk = true;
            if((binding.spinnerIngredient.selectedItem).toString() == "Select One") {
                Toast.makeText(requireActivity(), "Select an Ingredient", Toast.LENGTH_LONG).show()
                contentOk = false
            }
            if(((binding.spinnerMetric.selectedItem).toString() != "Select One" && binding.editTextTextQuantity.text.toString().length == 0) || (binding.spinnerMetric.selectedItem).toString() == "Select One" && binding.editTextTextQuantity.text.toString().length > 0) {
                Toast.makeText(requireActivity(), "You Must Select None or Both an Amount and Metric", Toast.LENGTH_LONG).show()
                contentOk = false
            }
            if(contentOk) {
                UserManagementAPI().AddIntolerance(
                    activity?.getFilesDir()!!.path, Intolerances(
                        ingredient = (binding.spinnerIngredient.selectedItem).toString(),
                        metric = if ((binding.spinnerMetric.selectedItem).toString() == "Select One") null else (binding.spinnerMetric.selectedItem).toString(),
                        amount = if (binding.editTextTextQuantity.text.toString().isEmpty()) null else Integer.parseInt(binding.editTextTextQuantity.text.toString()).toDouble(),
                        severity = binding.seekBar.progress
                    )
                );

                UserManagementAPI().GetUserIntolerances(
                    activity?.getFilesDir()!!.path,
                    fun(input: MutableList<Intolerances>) {
                        activity?.runOnUiThread(java.lang.Runnable {
                            ingredientItems.clear()
                            ingredientItems.addAll(input.map { (if (it.severity == 1) "Warn" else "Hide") + " When Recipe " + (if (it.metric == null) "Contains" else ("Uses More Than " + it.amount + " " + it.metric + " Of")) + " " + it.ingredient })
                            adapter1.notifyDataSetChanged()
                        })

                    })
            }
        }

        RecipeManagementAPI().GetAllIngredientNames(fun(input:SimpleStringList) {
            activity?.runOnUiThread(java.lang.Runnable {
                var ItemList: MutableList<String> = input.Items.toMutableList()
                ItemList.add(0, "Select One")
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, ItemList)
                binding.spinnerIngredient.adapter = adapter
            })
        })

        RecipeManagementAPI().GetAllIngredientUnits(fun(input:SimpleStringList) {
            var ItemList: MutableList<String> = input.Items.toMutableList()
            ItemList.add(0, "Select One")
            activity?.runOnUiThread(java.lang.Runnable {
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, ItemList)
                binding.spinnerMetric.adapter = adapter
            })
        })

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                activity?.runOnUiThread(java.lang.Runnable {
                    if(progress > 0) {
                        binding.metricAmountPanel.visibility = VISIBLE
                        binding.addIntolerance.text = "Add Intolerance"
                    } else {
                        binding.metricAmountPanel.visibility = GONE
                        binding.addIntolerance.text = "Remove Intolerance"
                        binding.spinnerMetric.setSelection(0)
                        binding.editTextTextQuantity.text.clear()
                    }
                })
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        


        return binding.root
    }
}