package com.jam.recipeassistant

import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.jam.recipeassistant.model.Suggestions.RecipeCard
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.decode
import android.widget.Button
import com.jam.recipeassistant.api.RecipeManagementAPI
import com.squareup.picasso.Picasso
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2021-11-26
 */
class UserRecipeAdapter(private val context: Activity, private val img: MutableList<String>, private val imgType: MutableList<String>, private val name: MutableList<String>, private val author: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.single_user_recipe_row, name) {

    lateinit var adapterContext: UserRecipeAdapter

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        adapterContext = this
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_user_recipe_row, null, true)

        val imageView = rowView.findViewById(R.id.ivRecipe) as ImageView
        val nameText = rowView.findViewById(R.id.tvRecipe) as TextView
        val authorText = rowView.findViewById(R.id.tvAuthor) as TextView

        if(imgType[position] == "WEB") {
            Picasso.with(context).load(img[position]).resize(250, 250).into(imageView);
        } else if (imgType[position] == "SVG") {
            var imageData: ByteArray = Base64.decode(img[position].substring(img[position].indexOf(",") + 1), Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(imageData)
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
        } else if(imgType[position] == "URI") {
            imageView.setImageURI(Uri.parse(img[position]))
        }
        nameText.text = name[position]
        authorText.text = author[position]

        val deleteButton = rowView.findViewById(R.id.deleteButton) as Button
        deleteButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                RecipeManagementAPI().UnListRecipe(name[position])
                img.removeAt(position)
                name.removeAt(position)
                author.removeAt(position)
                adapterContext.notifyDataSetChanged()
            }
        })
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

    fun update() {
        notifyDataSetChanged()
    }
}