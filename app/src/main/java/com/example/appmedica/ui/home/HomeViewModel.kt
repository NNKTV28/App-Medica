package com.example.appmedica.ui.home

import AdviceResponse
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun loadDailyAdvice(context: Context) {
        try {
            val jsonString = context.assets.open("advice.json").bufferedReader().use { it.readText() }
            val adviceResponse = Gson().fromJson(jsonString, AdviceResponse::class.java)
            val randomAdvice = adviceResponse.advices.random()
            _text.value = "Advice of the day:\n${randomAdvice.text}"
        } catch (e: Exception) {
            _text.value = "Stay healthy!"
        }
    }
}