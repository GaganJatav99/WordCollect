package com.example.wordcollect3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private  Context context;



    private static final String DATABASE_NAME = "wordLibrary.db";
    private static final int DATABASE_VERSION = 2;

    private   static final String TABLE_NAME = "my_library";
    private   static  final String COLUMN_ID = "_id";
    private   static  final String COLUMN_TITLE = "word_title";
    private   static  final String COLUMN_MEANING = "word_meaning";

    private   static  final String COLUMN_EXAMPLE = "word_example";
    private   static  final String COLUMN_SYNONYMS = "word_synonyms";
    private   static  final String COLUMN_ANTONYMS = "word_antonyms";
    private   static  final String COLUMN_NOTE = "word_note";




    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_MEANING + " TEXT, " +
                        COLUMN_EXAMPLE + " TEXT, " +
                        COLUMN_SYNONYMS + " TEXT, " +
                        COLUMN_ANTONYMS + " TEXT, " +
                        COLUMN_NOTE + " TEXT);";


        db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    void addWord(String title,String meaning,String example,String synonyms,String antonyms,String note){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_MEANING,meaning);
        cv.put(COLUMN_EXAMPLE, example);
        cv.put(COLUMN_SYNONYMS,synonyms);
        cv.put(COLUMN_ANTONYMS,antonyms);
        cv.put(COLUMN_NOTE,note);


        long result = db.insert(TABLE_NAME,null,cv);

        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show();

        }

    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor =db.rawQuery(query,null);
        }

        return cursor;

    }

    void updateData(String row_id, String title,String meaning,String example,String synonyms,String antonyms,String note){

        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put( COLUMN_TITLE,title);
        cv.put( COLUMN_MEANING,meaning);
        cv.put( COLUMN_EXAMPLE,example);
        cv.put( COLUMN_SYNONYMS,synonyms);
        cv.put( COLUMN_ANTONYMS,antonyms);
        cv.put( COLUMN_NOTE,note);




        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});

        if(result==-1){
            Toast.makeText(context,"Failed to Update",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context,"Update Successfully",Toast.LENGTH_SHORT).show();

        }
    }

    void  deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");




        if(result == -1){
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show();

        }
    }


}
