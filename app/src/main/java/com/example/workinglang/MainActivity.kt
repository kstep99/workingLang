package com.example.workinglang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var translateButton: Button
    private lateinit var translatedTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.input_text)
        translateButton = findViewById(R.id.translate_button)
        translatedTextView = findViewById(R.id.translated_text_view)

        // Set the button click listener
        translateButton.setOnClickListener {
            val textToTranslate = inputText.text.toString()
            translateText(textToTranslate)
        }
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
        translatedTextView.text = translatedText ?: "Translation failed"
    }
}
