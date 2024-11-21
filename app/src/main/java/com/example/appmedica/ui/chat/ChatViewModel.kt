package com.example.appmedica.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmedica.BuildConfig
import com.example.appmedica.model.ChatMessage
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    init {
        _messages.value = mutableListOf()
        addWelcomeMessage()
    }

    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "API KEY" // Replace with your actual Gemini API key
        )
    }

    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            "Hello! I'm your medical assistant. How can I help you today?",
            isBot = true
        )
        _messages.value = listOf(welcomeMessage)
    }

    fun sendMessage(userMessage: String) {
        viewModelScope.launch {
            val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()

            currentMessages.add(ChatMessage(userMessage, isBot = false))
            _messages.value = currentMessages

            try {
                Log.d("ChatViewModel", "Sending message: $userMessage")
                val prompt = "You are a medical assistant. User asks: $userMessage"

                val response = generativeModel.generateContent(prompt)
                val aiResponse = response.text ?: "I understand your query about $userMessage. Let me help you with that."

                currentMessages.add(ChatMessage(aiResponse, isBot = true))
                _messages.value = currentMessages

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error details: ${e.message}")
                val fallbackResponse = "I understand your question about $userMessage. Let me provide some general guidance..."
                currentMessages.add(ChatMessage(fallbackResponse, isBot = true))
                _messages.value = currentMessages
            }
        }
    }
}
