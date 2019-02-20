package com.example.bar.movielist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String url = " http://api.androidhive.info/json/movies.json";
    private TextView titletv;
    private SharedPreferences sharedPreferences;
    DatabaseHelper helper;
    private RecyclerView recyclerView;
    private SQLiteDatabase db;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("omriPrefs",MODE_PRIVATE);
            boolean firstTime = sharedPreferences.getBoolean("firstTime",false);
            helper = new DatabaseHelper(this);
            db = helper.getReadableDatabase();
            if(!firstTime){
                GetMovies getMovies = new GetMovies(this);
                try {
                    getMovies.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getMovies.start();
            recyclerView = findViewById(R.id.movieList_recyclerView);
            adapter = new RecyclerViewAdapter(this,getAllMovies());
            manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
       }else{
            recyclerView = findViewById(R.id.movieList_recyclerView);
            adapter = new RecyclerViewAdapter(this,getAllMovies());
            manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Activity_Main_Plus_Button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QRScanner.class);
                startActivity(intent);
            }
        });



    }
    public int isEmpty(String TableName){
        SQLiteDatabase database = helper.getReadableDatabase();
        String query = "SELECT * FROM "+DatabaseHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public ArrayList<Movie> getAllMovies(){
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COL_RELEASE_YEAR+ " DESC",
                null
        );

        ArrayList<Movie>movies = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_MOVIE_TITLE));
                String imageUrl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_IMAGE_URL));
                String genre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENRE));
                int releaseYear = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_RELEASE_YEAR));
                double rating = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COL_RATING));
                Movie m = new Movie(title,releaseYear,imageUrl,rating,genre);
                movies.add(m);

            }while (cursor.moveToNext());
        }
        return movies;
    }

    public void deleteAllMovies(View view) {
        db.execSQL("delete from "+ DatabaseHelper.TABLE_NAME);
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this,new ArrayList<Movie>());
        recyclerView.setAdapter(adapter1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstTime",false);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter = new RecyclerViewAdapter(this, getAllMovies());
        recyclerView.setAdapter(adapter);

    }

}
