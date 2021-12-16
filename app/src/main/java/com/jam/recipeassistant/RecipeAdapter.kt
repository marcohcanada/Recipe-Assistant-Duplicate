package com.jam.recipeassistant

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

/**
* Created by 991470628 : MARCO HIDALGO ROMERO
* on 2021-11-26
*/
class RecipeAdapter(private val context: Activity, private val img: Array<Int>, private val name: Array<String>, private val author: Array<String>)
    : ArrayAdapter<String>(context, R.layout.single_recipe_row_layout, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_recipe_row_layout, null, true)

        val imageView = rowView.findViewById(R.id.ivRecipe) as ImageView
        val nameText = rowView.findViewById(R.id.tvRecipe) as TextView
        val authorText = rowView.findViewById(R.id.tvAuthor) as TextView

        imageView.setImageResource(img[position])
        nameText.text = name[position]
        authorText.text = author[position]

        return rowView
    }
}