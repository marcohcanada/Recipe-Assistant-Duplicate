package com.jam.recipeassistant

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-31
 */
class StepAdapter(private val context: Activity, private val stepNumber: MutableList<String> ,private val step: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.single_recipe_row_layout, stepNumber) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.recipe_list_step, null, true)

        val stepNumberText = rowView.findViewById(R.id.step_number) as TextView
        val stepText = rowView.findViewById(R.id.step) as TextView

        stepNumberText.text = stepNumber[position]
        stepText.text = step[position]

        return rowView
    }

    fun update() {
        notifyDataSetChanged()
    }
}