package com.example.quoteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String[][] quotes = {
            {"Believe you can and you're halfway there.", "Theodore Roosevelt"},
            {"Success is not final, failure is not fatal: it is the courage to continue that counts.", "Winston Churchill"},
            {"The best way to predict the future is to invent it.", "Alan Kay"},
            {"Don’t watch the clock; do what it does. Keep going.", "Sam Levenson"},
            {"Everything you can imagine is real.", "Pablo Picasso"},
            {"Do what you can, with what you have, where you are.", "Theodore Roosevelt"}
    };

    TextView quoteText, authorText;
    Button newQuoteBtn;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quoteText);
        authorText = findViewById(R.id.authorText);
        newQuoteBtn = findViewById(R.id.newQuoteBtn);

        // Initialize Text-to-Speech
        // Initialize Text-to-Speech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);  // Just set language, no voice loop
            }
        });


        showRandomQuote();

        newQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRandomQuote();
            }
        });
    }

    private void showRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.length);
        String quote = quotes[index][0];
        String author = quotes[index][1];

        quoteText.setText("\"" + quote + "\"");
        authorText.setText("– " + author);

        speakQuote(quote, author);
    }

    private void speakQuote(String quote, String author) {
        String fullQuote = quote + " by " + author;
        tts.speak(fullQuote, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
