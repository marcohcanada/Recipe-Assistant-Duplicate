package com.jam.recipeassistant

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jam.recipeassistant.model.Suggestions.RecipeDetailsIngredients
import com.squareup.picasso.Picasso
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.ArrayList

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-31
 */
class IngredientAdapter(ctx: Context, private val ingredientArrayList: MutableList<String>) :
    RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater

    init {

        inflater = LayoutInflater.from(ctx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.recipe_list_ingredient, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientAdapter.MyViewHolder, position: Int) {

        holder.ingredientName.setText(ingredientArrayList[position])
    }

    override fun getItemCount(): Int {
        return ingredientArrayList.size
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun getAsJson(): String {
        var json = ""
        for (i in ingredientArrayList) {
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

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ingredientName: TextView

        init {

            ingredientName = itemView.findViewById<View>(R.id.ingredient) as TextView
        }

    }
}

/*class IngredientAdapter(private val context: Activity, private val ingredient: MutableList<String>)
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
}*/