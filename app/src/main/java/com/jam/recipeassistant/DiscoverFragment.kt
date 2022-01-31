package com.jam.recipeassistant

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jam.recipeassistant.api.SuggestionsAPI
import com.jam.recipeassistant.databinding.FragmentDiscoverBinding
import android.widget.FrameLayout

import android.widget.TextView
import com.google.cloud.dialogflow.v2.QueryInput
import com.google.cloud.dialogflow.v2.TextInput



class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    lateinit var manager: DialogflowManager
    private val LANGUAGE_CODE = "en"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(this.layoutInflater, container, false)
        manager = DialogflowManager()
        manager.initAssistant(this.requireContext(), this)
        binding.sendMessageBtn.setOnClickListener {
            sendMessage(it)
        }

        binding.userMessageEdt.setOnKeyListener OnKeyListener@{ view, keyCode, keyEvent ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN){
                sendMessage(view)
                return@OnKeyListener true
            }
            false
        }

        return binding.root
    }

    fun sendMessage(v: View){
        val message = binding.userMessageEdt.text.toString()
        if(message.trim().isEmpty()){
            Toast.makeText(this.requireContext(), "Please enter a message", Toast.LENGTH_LONG).show()
        }
        else {
            showTextView(message, 1001)
            binding.userMessageEdt.setText("")
            // Android client
            val queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode(LANGUAGE_CODE)).build()
            manager.execute(queryInput)
        }

    }

    fun showTextView(message: String, type: Int) {
        val layout: FrameLayout = when (type) {
            1001 -> {
                getUserLayout()
            }
            1002 -> {
                getBotLayout()
            }
            else -> {
                getBotLayout()
            }
        }
        layout.isFocusableInTouchMode = true
        binding.assistantChatLayout.addView(layout)
        val chatMessageTv = layout.findViewById<TextView>(R.id.messageTv)
        chatMessageTv.text = message
        layout.requestFocus()
        binding.userMessageEdt.requestFocus() // change focus back to edit text to continue typing
    }

    fun getUserLayout(): FrameLayout {
        val inflater = LayoutInflater.from(this.requireContext())
        return inflater.inflate(R.layout.user_message_layout,null, false) as FrameLayout
    }

    fun getBotLayout(): FrameLayout {
        val inflater = LayoutInflater.from(this.requireContext())
        return inflater.inflate(R.layout.bot_message_layout,null, false) as FrameLayout
    }
}