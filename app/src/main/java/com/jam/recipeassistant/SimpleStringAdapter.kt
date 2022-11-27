package com.jam.recipeassistant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleStringAdapter(ctx: Context, private val simpleStringArrayList: MutableList<String>) :
    RecyclerView.Adapter<SimpleStringAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater

    init {

        inflater = LayoutInflater.from(ctx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleStringAdapter.MyViewHolder {

        val view = inflater.inflate(R.layout.simple_string_list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleStringAdapter.MyViewHolder, position: Int) {
        holder.simpleString.setText(simpleStringArrayList[position])
        //holder.stepText.setText(stepArrayList[position])
    }

    override fun getItemCount(): Int {
        return simpleStringArrayList.size
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun getAsJson(): String {
        var json = "["
        for (i in 0..simpleStringArrayList.count()-1) {
            json += ("\"" + simpleStringArrayList[i] + "\"" + (if (i == simpleStringArrayList.count()-1) "" else ","))
        }
        return json + "]";
    }

    fun deleteItem(index: Int) {
        simpleStringArrayList.removeAt(index)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var simpleString: TextView

        init {

            simpleString = itemView.findViewById<View>(R.id.text_string) as TextView
        }

    }
}