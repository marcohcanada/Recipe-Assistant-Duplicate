package com.jam.recipeassistant

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jam.recipeassistant.model.Suggestions.RecipeDetailsSteps
import com.squareup.picasso.Picasso
import java.util.ArrayList

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-31
 */
class StepAdapter(ctx: Context, private val stepNumberArrayList: MutableList<String>, private val stepArrayList: MutableList<String>) :
    RecyclerView.Adapter<StepAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater

    init {

        inflater = LayoutInflater.from(ctx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.recipe_list_step, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepAdapter.MyViewHolder, position: Int) {

        //Picasso.get().load(stepArrayList[position].StepNumber.toString())
        holder.stepNumber.setText(stepArrayList[position])
        holder.stepText.setText(stepArrayList[position])
    }

    override fun getItemCount(): Int {
        return stepArrayList.size
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun getAsJson(): String {
        var json = ""
        for (i in 0..stepNumberArrayList.count()-1) {
            json += "    {\n" +
                    "      \"stepNumber\": "+stepNumberArrayList[i]+",\n" +
                    "      \"stepText\": \""+stepArrayList[i]+"\"\n" +
                    "    },"
        }
        return json;
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var stepNumber: TextView
        var stepText: TextView

        init {

            stepNumber = itemView.findViewById<View>(R.id.step_number) as TextView
            stepText = itemView.findViewById<View>(R.id.step) as TextView
        }

    }
}

/*class StepAdapter(private val context: Activity, private val stepNumber: MutableList<String> ,private val step: MutableList<String>)
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

    fun getAsJson(): String {
        var json = ""
        for (i in 0..stepNumber.count()-1) {
            json += "    {\n" +
                    "      \"stepNumber\": "+stepNumber[i]+",\n" +
                    "      \"stepText\": \""+step[i]+"\"\n" +
                    "    },"
        }
        return json;
    }
}*/