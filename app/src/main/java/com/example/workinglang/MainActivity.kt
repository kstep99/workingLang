package com.example.workinglang

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        translateText("Hello World! Good to meet you, will you sit for tea?")
    }

    private fun translateText(text: String) {
        val request = TranslateRequest(q = text, source = "en", target = "es")

        ApiClient.instance.translateText(request).enqueue(object : Callback<TranslateResponse> {
            override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                if (response.isSuccessful) {
                    val translatedText = response.body()?.translatedText
                    updateUIWithTranslatedText(translatedText)
                } else {
                    // Handle the error, e.g., show a Toast or a Snackbar with an error message.
                }
            }

            override fun onFailure(call: Call<TranslateResponse>, t: Throwable) {
                // Handle failure, e.g., no internet connection. Again, consider using Toast or Snackbar.
            }
        })
    }

    private fun updateUIWithTranslatedText(translatedText: String?) {
        val textView: TextView = findViewById(R.id.translated_text_view)
        textView.text = translatedText ?: "Translation failed"
    }
}
