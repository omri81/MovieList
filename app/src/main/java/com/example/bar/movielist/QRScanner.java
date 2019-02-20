package com.example.bar.movielist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;



public class QRScanner extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        startQRScanner();

    }
    private void startQRScanner() {
        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
                finish();
            } else {
                try {
                    JSONObject j = new JSONObject(result.getContents());
                    String title = j.getString("title");
                    String imageUrl = j.getString("image");
                    double rating = j.getDouble("rating");
                    int releaseYear = j.getInt("releaseYear");
                    JSONArray genres = j.getJSONArray("genre");
                    String genre = "";
                    for (int k = 0; k < genres.length(); k++) {
                        genre += genres.getString(k);
                        if (k < genres.length() - 1) {
                            genre += ", ";
                        }
                    }

                    if(Exists(j.getString("title"))){
                        Toast.makeText(this, "movie already on list", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(DatabaseHelper.COL_MOVIE_TITLE, title);
                        cv.put(DatabaseHelper.COL_GENRE, genre);
                        cv.put(DatabaseHelper.COL_IMAGE_URL, imageUrl);
                        //Log.d("omri",imageUrl);
                        cv.put(DatabaseHelper.COL_RATING, rating);
                        cv.put(DatabaseHelper.COL_RELEASE_YEAR, releaseYear);
                        db.insert(DatabaseHelper.TABLE_NAME, null, cv);
                        finish();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


        }

    public boolean Exists(String searchItem) {

        String[] columns = { DatabaseHelper.COL_MOVIE_TITLE };
        String selection = DatabaseHelper.COL_MOVIE_TITLE + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }




}
