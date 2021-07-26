package com.example.wordcollect3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private Button button;
    private long backPressedTime;
    private Toast backToast;
    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView No_data;

    MyDatabaseHelper myDB;
    ArrayList<String> word_id;
    static ArrayList<String> word_title;
    static ArrayList<String> word_meaning;
    ArrayList<String> word_example;
    ArrayList<String> word_synonyms;
    ArrayList<String> word_antonyms;
    ArrayList<String> word_note;
    customAdapter customAdapter;

    public static ArrayList<String> getWord_title(){
        return word_title;
    }

    public static ArrayList<String> getWord_meaning() {
        return word_meaning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //for light mode only
        setContentView(R.layout.activity_main);







        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //setting main activity as home
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button = (Button)findViewById(R.id.add);

        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.imageView2);
        No_data = findViewById(R.id.nodata);


        NotificationChannel();   //notification

        Calendar calendar = Calendar.getInstance();  // make alarm fro 1200 everyday
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        Intent intent = new Intent(MainActivity.this,NotiReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        }







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openAddActivity();
            }
        });


        myDB = new MyDatabaseHelper(MainActivity.this);
        word_id = new ArrayList<>();
        word_title = new ArrayList<>();
        word_meaning = new ArrayList<>();
        word_example = new ArrayList<>();
        word_synonyms = new ArrayList<>();
        word_antonyms= new ArrayList<>();
        word_note = new ArrayList<>();


        storeDataInArrays();
        customAdapter = new customAdapter(MainActivity.this,MainActivity.this,word_id,word_title,word_meaning,word_example,word_synonyms,word_antonyms,word_note);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));






    }





    @Override
    public void onBackPressed() {

        if(backPressedTime+ 2000> System.currentTimeMillis())
        {
            backToast.cancel();
            super.onBackPressed();
            return;

        }else{
            backToast = Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }





    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"No data.",Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            No_data.setVisibility(View.VISIBLE);
        }
        else{
            while(cursor.moveToNext()){
                word_id.add(cursor.getString(0));
                word_title.add(cursor.getString(1));
                word_meaning.add(cursor.getString(2));
                word_example.add(cursor.getString(3));
                word_synonyms.add(cursor.getString(4));
                word_antonyms.add(cursor.getString(5));
                word_note.add(cursor.getString(6));
            }
            empty_imageview.setVisibility(View.GONE);
            No_data.setVisibility(View.GONE);

        }

    }

    private void NotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notify";
            String description = "Notify Word";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification",name,importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel);
        }

    }


    public void  openAddActivity(){
        Intent intent = new Intent(this,addActivity.class);
        startActivity(intent);

        finish();
    }
}