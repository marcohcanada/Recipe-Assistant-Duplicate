package com.jam.recipeassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jam.recipeassistant.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    lateinit var binding: FragmentCreateBinding
    lateinit var adapter1: IngredientAdapter
    lateinit var adapter2: StepAdapter

    var ingredientItems = mutableListOf<String>("1 (2 lb.) center-cut beef tenderloin, trimmed",
        "kosher salt", "freshly ground black pepper", "extra-virgin olive oil, for greasing",
        "2 tbsp. Dijon mustard", "1 1/2 lb. mixed mushrooms, roughly chopped", "1 shallot, roughly chopped",
        "Leaves from 1 thyme sprig", "2 tbsp. unsalted butter", "12 thin slices prosciutto",
        "all-purpose flour, for dusting", "14 oz. frozen puff pastry, thawed", "1 large egg, beaten",
        "flaky salt, for sprinkling")

    var stepNumberItems = mutableListOf<String>("1. ", "2. ", "3. ", "4. ", "5. ", "6. ", "7. ", "8. ", "9. ", "10. ")
    var stepItems = mutableListOf<String>("Using kitchen twine, tie tenderloin in 4 places. Season generously with salt and pepper.",
        "Over high heat, coat bottom of a heavy skillet with olive oil. Once pan is nearly smoking, sear tenderloin until well-browned on all sides, including the ends, about 2 minutes per side (12 minutes total). Transfer to a plate. When cool enough to handle, snip off twine and coat all sides with mustard. Let cool in fridge.",
        "Meanwhile, make duxelles: In a food processor, pulse mushrooms, shallots, and thyme until finely chopped.",
        "To skillet, add butter and melt over medium heat. Add mushroom mixture and cook until liquid has evaporated, about 25 minutes. Season with salt and pepper, then let cool in fridge.",
        "Place plastic wrap down on a work surface, overlapping so that it’s twice the length and width of the tenderloin. Shingle the prosciutto on the plastic wrap into a rectangle that’s big enough to cover the whole tenderloin. Spread the duxelles evenly and thinly over the prosciutto.",
        "Season tenderloin, then place it at the bottom of the prosciutto. Roll meat into prosciutto-mushroom mixture, using plastic wrap to roll tightly. Tuck ends of prosciutto as you roll, then twist ends of plastic wrap tightly into a log and transfer to fridge to chill (this helps it maintain its shape).",
        "Heat oven to 425°. Lightly flour your work surface, then spread out puff pastry and roll it into a rectangle that will cover the tenderloin (just a little bigger than the prosciutto rectangle you just made!). Remove tenderloin from plastic wrap and place on bottom of puff pastry. Brush the other three edges of the pastry with egg wash, then tightly roll beef into pastry.",
        "Once the log is fully covered in puff pastry, trim any extra pastry, then crimp edges with a fork to seal well. Wrap roll in plastic wrap to get a really tight cylinder, then chill for 20 minutes.",
        "Remove plastic wrap, then transfer roll to a foil-lined baking sheet. Brush with egg wash and sprinkle with flaky salt.",
        "Bake until pastry is golden and the center registers 120°F for medium-rare, about 40 to 45 minutes. Let rest 10 minutes before carving and serving.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(this.layoutInflater, container, false)

        val imageID = binding.root.resources.getIdentifier("beefwellington", "drawable", binding.root.context.packageName)
        binding.image.setImageResource(imageID)

        binding.recipeTitle.text = "Beef Wellington"

        binding.description.text = "So, you've decided to make Beef Wellington. Congratulations! You are about to make your dinner guests extremely happy. While the origins of this famous dish are unknown, we do know it's a holiday showstopper that it is not for the faint of heart. Below, we break down all the elements of a classic Beef Wellington from the inside out, so you can fearlessly make the best Wellington you can. We believe in you!"

        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.ingredientsList
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1

        val stepAdapter = StepAdapter(requireActivity(), stepNumberItems ,stepItems)

        val lvSteps = binding.stepsList
        adapter2 = stepAdapter
        lvSteps.adapter = adapter2

        return binding.root
    }


    /*fun viewScreen1(){
        findNavController().navigate(R.id.action_testFragment3_to_discoverFragment)
        /*var conn = SQLConnection()
        var result = conn.executeQuery("SELECT * FROM Ingredient");
        if(result != null) {
            result.next()
            System.out.println(result.getString(2))
        }*/
        //findNavController().navigate(R.id.action_testFragment3_to_testFragment1)
    }*/
}

