package com.jam.recipeassistant

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.squareup.picasso.Picasso

import java.io.*
import java.lang.Exception
import java.net.URL
import java.net.URLConnection


/**
* Created by 991470628 : MARCO HIDALGO ROMERO
* on 2021-11-26
*/
class RecipeAdapter(private val context: Activity, private val img: MutableList<String>,
                    private val imgType: MutableList<String>, private val name: MutableList<String>,
                    private val author: MutableList<String>, private val likes: MutableList<String>,
                    private val dislikes: MutableList<String>, private val views: MutableList<String>,
                    private val rating: MutableList<String>, private val warnings: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.single_recipe_row_layout, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_recipe_row_layout, null, true)

        val imageView = rowView.findViewById(R.id.ivRecipe) as ImageView
        val nameText = rowView.findViewById(R.id.tvRecipe) as TextView
        val authorText = rowView.findViewById(R.id.tvAuthor) as TextView
        val likesText = rowView.findViewById(R.id.tvLikes) as TextView
        val dislikesText = rowView.findViewById(R.id.tvDislikes) as TextView
        val viewsText = rowView.findViewById(R.id.tvViews) as TextView
        val ratingText = rowView.findViewById(R.id.tvAvgRating) as TextView
        val warningsText = rowView.findViewById(R.id.tvWarnings) as TextView

        if(imgType[position] == "WEB") {
            Picasso.with(context).load(img[position]).resize(250, 250).into(imageView);
        } else if(imgType[position] == "SVG"){
            var imageData: ByteArray = Base64.decode(img[position].substring(img[position].indexOf(",") + 1), Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(imageData)
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
        } else if(imgType[position] == "BYTEARRAY") {
            val s = img[position].split(',')
            var byteArray:ByteArray = ByteArray(s.size)
            for (i in 0..s.size-1) {
                byteArray.set(i,Integer.parseInt(s[i]).toByte())
            }
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
        }


        nameText.text = name[position]
        authorText.text = author[position]
        likesText.text = likes[position]
        dislikesText.text = dislikes[position]
        viewsText.text = views[position]
        ratingText.text = rating[position]
        warningsText.text = warnings[position]


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