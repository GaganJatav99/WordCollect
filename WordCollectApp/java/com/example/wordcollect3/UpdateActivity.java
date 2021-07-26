package com.example.wordcollect3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {

    TextInputLayout title_input,meaning_input,example_input,synonyms_input,antonyms_input,note_input;
    Button update_button,delete_button;

    String id,title,meaning,example,synonyms,antonyms,note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.text_input_word2);
        meaning_input = findViewById(R.id.text_input_meaning2);
        example_input = findViewById(R.id.text_input_Example2);
        synonyms_input = findViewById(R.id.text_input_Synonyms2);
        antonyms_input = findViewById(R.id.text_input_Antonyms2);
        note_input = findViewById(R.id.text_input_Note2);

        update_button = findViewById(R.id.update_button);

        delete_button = findViewById(R.id.delete_button);

        //first we call this
        getAndSetIntentData();
        update_button.setOnClickListener(v -> {
            if(!validateWord() | !validateMeaning()){
                return;
            }

            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);

            title = Objects.requireNonNull(title_input.getEditText()).getText().toString().trim();
            meaning = Objects.requireNonNull(meaning_input.getEditText()).getText().toString().trim();

            example = Objects.requireNonNull(example_input.getEditText()).getText().toString().trim();
            synonyms = Objects.requireNonNull(synonyms_input.getEditText()).getText().toString().trim();
            antonyms = Objects.requireNonNull(antonyms_input.getEditText()).getText().toString().trim();
            note = Objects.requireNonNull(note_input.getEditText()).getText().toString().trim();

            myDB.updateData(id,title,meaning,example,synonyms,antonyms,note);

            finish();

        });

        delete_button.setOnClickListener(v -> confirmDialog());





    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")&& getIntent().hasExtra("title") && getIntent().hasExtra("meaning")){
            //Getting data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            meaning = getIntent().getStringExtra("meaning");
            example = getIntent().getStringExtra("example");
            synonyms = getIntent().getStringExtra("synonyms");
            antonyms = getIntent().getStringExtra("antonyms");
            note = getIntent().getStringExtra("note");



            //Setting data from Intent
            Objects.requireNonNull(title_input.getEditText()).setText(title);
            Objects.requireNonNull(meaning_input.getEditText()).setText(meaning);
            Objects.requireNonNull(example_input.getEditText()).setText(example);
            Objects.requireNonNull(synonyms_input.getEditText()).setText(synonyms);
            Objects.requireNonNull(antonyms_input.getEditText()).setText(antonyms);
            Objects.requireNonNull(note_input.getEditText()).setText(note);



        }
        else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+title+" ?");
        builder.setMessage("Are you sure you want to delete "+title +" ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
    public void cancelInput(View view) {
        finish();
    }

    private boolean validateWord(){
        String wordInput = Objects.requireNonNull(title_input.getEditText()).getText().toString().trim();
        if(wordInput.isEmpty()){
            title_input.setError("Word can't be empty");
            return false;
        }
        else{
            title_input.setError(null);
            return  true;
        }
    }

    private boolean validateMeaning(){
        String meaningInput = Objects.requireNonNull(meaning_input.getEditText()).getText().toString();
        if(meaningInput.isEmpty()){
            meaning_input.setError("Meaning can't be empty");
            return false;
        }
        else{
            meaning_input.setError(null);
            return  true;
        }
    }

}