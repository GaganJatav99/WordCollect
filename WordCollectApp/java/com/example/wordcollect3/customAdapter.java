package com.example.wordcollect3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.MyViewHolder>{

    Context context;
    Activity activity;
    private ArrayList word_id,word_title,word_meaning,word_example,word_synonyms,word_antonyms,word_note;

    customAdapter(Activity activity,Context context,
                  ArrayList word_id,
                  ArrayList word_title,
                  ArrayList word_meaning,
                  ArrayList word_example,
                  ArrayList word_synonyms,
                  ArrayList word_antonyms,
                  ArrayList word_note){
        this.activity = activity;
        this.context = context;
        this.word_id = word_id;
        this.word_title = word_title;
        this.word_meaning = word_meaning;
        this.word_example = word_example;
        this.word_synonyms = word_synonyms;
        this.word_antonyms = word_antonyms;
        this.word_note = word_note;

    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String title = (String)word_title.get(position);
        holder.word_title_txt.setText("\u25CF  "+title);
        String meaning = (String)word_meaning.get(position);
        holder.word_meaning_txt.append(": "+meaning);


        String example = (String) word_example.get(position);
        if(!example.isEmpty()){
            holder.word_meaning_txt.append("\n");
            holder.word_meaning_txt.append("Example: "+example);
        }
        String Synonyms = (String)word_synonyms.get(position);
        if(!Synonyms.isEmpty()){
            holder.word_meaning_txt.append("\n");
            holder.word_meaning_txt.append("Synonyms: "+Synonyms);
        }
        String Antonyms = (String)word_antonyms.get(position);
        if(!Antonyms.isEmpty()){
            holder.word_meaning_txt.append("\n");
            holder.word_meaning_txt.append("Antonyms: "+Antonyms);
        }
        String note = (String)word_note.get(position);
        if(!note.isEmpty()){
            holder.word_meaning_txt.append("\n");
            holder.word_meaning_txt.append("Note: "+note);
        }


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(word_id.get(position)));
                intent.putExtra("title",String.valueOf(word_title.get(position)));
                intent.putExtra("meaning",String.valueOf(word_meaning.get(position)));
                intent.putExtra("example",String.valueOf(word_example.get(position)));
                intent.putExtra("synonyms",String.valueOf(word_synonyms.get(position)));
                intent.putExtra("antonyms",String.valueOf(word_antonyms.get(position)));
                intent.putExtra("note",String.valueOf(word_note.get(position)));


                activity.startActivityForResult(intent,1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return word_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView word_title_txt,word_meaning_txt;

        ConstraintLayout expandableLayout;
        LinearLayout mainLayout;






        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            word_title_txt = itemView.findViewById(R.id.word_title_txt);

            word_meaning_txt = itemView.findViewById(R.id.word_meaning_txt);

            


            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mainLayout = itemView.findViewById(R.id.mainLayout);


        }
    }
}
