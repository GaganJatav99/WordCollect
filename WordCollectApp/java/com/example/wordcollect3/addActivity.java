package com.example.wordcollect3;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class addActivity extends AppCompatActivity {


    private TextInputLayout textInputWord;
    private TextInputLayout textInputMeaning;
    private TextInputLayout textInputExample;
    private TextInputLayout textInputSynonyms;
    private TextInputLayout textInputAntonyms;
    private TextInputLayout textInputNote;


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        textInputWord = findViewById(R.id.text_input_word);
        textInputMeaning = findViewById(R.id.text_input_meaning);
        textInputSynonyms= findViewById(R.id.text_input_Synonyms);
        textInputExample = findViewById(R.id.text_input_Example);
        textInputAntonyms = findViewById(R.id.text_input_Antonyms);
        textInputNote = findViewById(R.id.text_input_Note);


        button = findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity(addActivity.this);
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent launchNextActivity;
        launchNextActivity = new Intent(this, MainActivity.class);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivity);
    }



    private boolean validateWord(){
        String wordInput = Objects.requireNonNull(textInputWord.getEditText()).getText().toString().trim();
        if(wordInput.isEmpty()){
            textInputWord.setError("Word can't be empty");
            return false;
        }
        else{
            textInputWord.setError(null);
            return  true;
        }
    }

    private boolean validateMeaning(){
        String meaningInput = Objects.requireNonNull(textInputMeaning.getEditText()).getText().toString();
        if(meaningInput.isEmpty()){
            textInputMeaning.setError("Meaning can't be empty");
            return false;
        }
        else{
            textInputMeaning.setError(null);
            return  true;
        }
    }

    public void confirmInput(View v){
        if(!validateWord() | !validateMeaning()){
            return;
        }
        MyDatabaseHelper myDB = new MyDatabaseHelper(addActivity.this);
        String word = Objects.requireNonNull(textInputWord.getEditText()).getText().toString().trim();
        String meaning = Objects.requireNonNull(textInputMeaning.getEditText()).getText().toString().trim();
        String example,synonyms,antonyms,note;
        example = Objects.requireNonNull(textInputExample.getEditText()).getText().toString().trim();
        synonyms = Objects.requireNonNull(textInputSynonyms.getEditText().getText()).toString().trim();
        antonyms = Objects.requireNonNull(textInputAntonyms.getEditText()).getText().toString().trim();
        note = Objects.requireNonNull(textInputNote.getEditText()).getText().toString().trim();

        myDB.addWord(word,meaning,example,synonyms,antonyms,note);
        restartActivity(addActivity.this);
    }

    public static void restartActivity(Activity act){
        Intent intent=new Intent();
        intent.setClass(act, MainActivity.class);
        ((Activity)act).startActivity(intent);
        ((Activity)act).finish();
    }


}