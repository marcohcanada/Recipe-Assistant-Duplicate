package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import com.jam.recipeassistant.databinding.FragmentRecipesBinding
import android.R
import android.widget.Button
import android.widget.ImageView

/**
 * A simple [Fragment] subclass.
 * Use the [ViewCreatedRecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewCreatedRecipesFragment : Fragment() {

    //lateinit var binding: FragmentRecipesBinding
    lateinit var viewOfLayout: View

    val recipeItems = arrayOf<String>("Beef Wellington")
    val dateItems = arrayOf<String>("2021-12-14")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //binding = FragmentRecipesBinding.inflate(this.layoutInflater, container, false)
        viewOfLayout = inflater.inflate(com.jam.recipeassistant.R.layout.fragment_view_created_recipes, container, false)

        /*binding.screen3btn.setOnClickListener {
            viewScreen3()
        }
        binding.screen1btn.setOnClickListener {
            viewScreen1()
        }*/

        /*val items:Array<String> = arrayOf("Beef Strogenof","Beef Wellington","Oven Baked Salmon","Ceaser Salad","Over Easy Egg","Eggs Benedict","Chicken Karage","Kung-Pow Chicken","Chicken Noodle Soup")*/

        /*val recipeAdapter : ArrayAdapter<String> = activity?.let {ArrayAdapter(it,android.R.layout.simple_list_item_1,
            recipeItems*/
        //val createdRecipeAdapter = CreatedRecipeAdapter(requireActivity(), recipeItems, dateItems)
        //val lv = binding.searchList
        //lv.adapter = createdRecipeAdapter
        //)}!!

        //binding.searchList.adapter = recipeAdapter;

        //return binding.root
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createdRecipeAdapter = CreatedRecipeAdapter(requireActivity(), recipeItems, dateItems)
        val createdRecipeList: ListView = view.findViewById(com.jam.recipeassistant.R.id.created_recipes_list)
        createdRecipeList.adapter = createdRecipeAdapter

        val plusBtn: Button = view.findViewById(com.jam.recipeassistant.R.id.plusbtn)
        plusBtn.setOnClickListener { plusBtnPressed() }
    }

    private fun plusBtnPressed() {
        //
    }
}