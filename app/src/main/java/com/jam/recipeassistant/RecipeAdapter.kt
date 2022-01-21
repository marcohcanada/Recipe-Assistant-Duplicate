package com.jam.recipeassistant

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

        /*rowView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (position == 0 || position == 1) {
                    val a = p0!!.context as AppCompatActivity
                    val vcrf = ViewCreatedRecipesFragment()
                    a.supportFragmentManager.beginTransaction().replace(R.id.ll_vcrf, vcrf).addToBackStack(null).commit()
                }
            }
        })*/

        return rowView
    }
}