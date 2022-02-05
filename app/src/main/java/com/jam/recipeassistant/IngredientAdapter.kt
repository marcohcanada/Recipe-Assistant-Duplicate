package com.jam.recipeassistant

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-31
 */
class IngredientAdapter(private val context: Activity, private val ingredient: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.single_recipe_row_layout, ingredient) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.recipe_list_ingredient, null, true)

        val ingredientText = rowView.findViewById(R.id.ingredient) as TextView

        ingredientText.text = ingredient[position]

        return rowView
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun getAsJson(): String {
        var json = ""
        for (i in ingredient) {
            var items = i.split(" ")
            var itemsName = items.subList(2, items.count())
            json += "    {\n" +
                    "      \"recipeIngredientAmount\": "+items[0]+",\n" +
                    "      \"recipeIngredientUnit\": \""+items[1]+"\",\n" +
                    "      \"ingredientName\": \""+itemsName.joinToString(" ")+"\"\n" +
                    "    },"
        }
        return json;
    }
}