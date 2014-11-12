package com.example.texttospeechex;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;

public class TextToSpeechMainActivity extends Activity implements
TextToSpeech.OnInitListener {
	
	 private TextToSpeech tts;
	 private Button btnSpeak;
	 private EditText txtText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech_main);
        
        tts = new TextToSpeech(this, this);
        
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
 
        txtText = (EditText) findViewById(R.id.txtText);
 
        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                speakOut();
            }
 
        });
    }
    
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }
    
    private void speakOut() {
    	 
        String text = txtText.getText().toString();
 
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    
    //SetPitchRate(0.6)
    //setSpeechRate(2)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_text_to_speech_main, menu);
        return true;
    }

}
