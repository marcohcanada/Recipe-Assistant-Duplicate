package com.jam.recipeassistant

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2021-12-14
 */
class CreatedRecipeAdapter(private val context: Activity, private val name: Array<String>, private val date: Array<String>)
    : ArrayAdapter<String>(context, R.layout.single_created_recipe_row_layout, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_created_recipe_row_layout, null, true)

        val nameText = rowView.findViewById(R.id.tvCreatedRecipe) as TextView
        val dateText = rowView.findViewById(R.id.tvDate) as TextView

        nameText.text = name[position]
        dateText.text = date[position]

        return rowView
    }
}