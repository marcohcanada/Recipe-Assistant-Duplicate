package com.jam.recipeassistant

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import coil.load
import coil.transform.CircleCropTransformation
import com.jam.recipeassistant.api.RecipeManagementAPI
import com.jam.recipeassistant.databinding.FragmentCreateRecipeBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import android.R.attr.bitmap
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.drawable.BitmapDrawable
import android.nfc.Tag
import androidx.core.graphics.drawable.toBitmap


class CreateRecipeFragment : Fragment() {

    lateinit var binding: FragmentCreateRecipeBinding
    lateinit var adapter1: IngredientAdapter
    lateinit var adapter2: StepAdapter
    var ingredientItems :MutableList<String> = ArrayList()
    var stepNumberItems :MutableList<String> = ArrayList()
    var tagItems :MutableList<String> = ArrayList()
    var stepItems :MutableList<String> = ArrayList()
    //val pickImage = 100
    //var imageUri: Uri? = null
    //val pickImage = 100
    val cameraRequestCode = 1
    val galleryRequestCode = 2
    var imageByteArrayString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateRecipeBinding.inflate(this.layoutInflater, container, false)

        val ingredientAdapter = IngredientAdapter(requireActivity(), ingredientItems)

        val lvIngredients = binding.recyclerViewIngredients
        adapter1 = ingredientAdapter
        lvIngredients.adapter = adapter1

        val stepAdapter = StepAdapter(requireActivity(), stepNumberItems ,stepItems)

        val lvSteps = binding.recyclerViewSteps
        adapter2 = stepAdapter
        lvSteps.adapter = adapter2

        binding.addIngredient.setOnClickListener {
            ingredientItems.add((binding.editTextTextQuantity.text.toString() + " " + binding.editTextTextMetric.text + " " + binding.editTextTextIngredient.text));
            binding.editTextTextQuantity.setText("")
            binding.editTextTextMetric.setText("")
            binding.editTextTextIngredient.setText("")
            adapter1.notifyDataSetChanged()
        }

        /*binding.btnAddImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }*/

        binding.btnAddImageFromCamera.setOnClickListener {
            cameraCheckPermission()
        }

        binding.btnAddImageFromGallery.setOnClickListener {
            galleryCheckPermission()
        }

        binding.ivAddImage.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this.requireActivity())
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf("Select photo from Gallery",
                "Capture photo from Camera")
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }

            pictureDialog.show()
        }

        binding.addSteps.setOnClickListener {
            stepNumberItems.add((stepNumberItems.count() +1).toString())
            stepItems.add(binding.editTextTextStep.text.toString())
            binding.editTextTextStep.setText("")
            adapter2.notifyDataSetChanged()
        }

        binding.removeSteps.setOnClickListener {
            stepNumberItems.remove((stepNumberItems.count()).toString())
            adapter2.notifyDataSetChanged()
        }

        binding.CreateRecipe.setOnClickListener {

            val bitmap = binding.ivAddImage.getDrawable().toBitmap()
            val baos = ByteArrayOutputStream()
            val tempbitmap = bitmap
            tempbitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b: ByteArray = baos.toByteArray()

            var json = "{\n" +
                    "  \"recipeId\": 0,\n" +
                    "  \"recipeName\": \""+binding.editTextTextRecipeName.text+"\",\n" +
                    "  \"recipeImage\": \""+b.toString()+"\",\n" +
                    "  \"recipeImageType\": \"BYTEARRAY\",\n" +
                    "  \"recipeDescription\": \""+binding.editTextTextDescription.text+"\",\n" +
                    "  \"createUserName\": \"Adrian\",\n" +
                    "  \"likes\": 0,\n" +
                    "  \"dislikes\": 0,\n" +
                    "  \"views\": 0,\n" +
                    "  \"severity\": 0,\n" +
                    "  \"recipeDetailsTags\": [\"test\"],\n" +
                    "  \"monetaryScale\": "+binding.ratingBarMonetaryScale.progress+",\n" +
                    "  \"recipeIngredients\": [\n";
            json += (adapter1.getAsJson().substring(0, adapter1.getAsJson().length-1) + "\n],\n")


            json += "  \"recipeSteps\": [\n" + (adapter2.getAsJson().substring(0, adapter2.getAsJson().length-1) + "\n]\n}")
            RecipeManagementAPI().CreateNewRecipe(json)
        }

        return binding.root
    }

    fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, cameraRequestCode)
    }

    fun cameraCheckPermission() {
        Dexter.withContext(this.requireActivity())
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }

    fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, galleryRequestCode)
    }

    fun galleryCheckPermission() {
        Dexter.withContext(this.requireActivity()).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@CreateRecipeFragment.requireActivity(),
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this.requireActivity())
            .setMessage(
                "It looks like you have turned off permissions"
                        + "required for this feature. It can be enable under App settings!!!"
            )

            .setPositiveButton("Go TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            when (requestCode) {

                cameraRequestCode -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                    binding.ivAddImage.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }

                galleryRequestCode -> {

                    binding.ivAddImage.load(data?.data) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }

                }
            }

        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.ivAddImage.setImageURI(imageUri)
        }
    }*/
}