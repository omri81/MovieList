package com.example.bar.movielist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.LocaleData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GetMovies extends Thread {

    private static final String url = " https://api.androidhive.info/json/movies.json";
    private Context context;
    private SharedPreferences preferences;

    public GetMovies(Context context){
        this.context = context;
    }

    public void run(){


                HttpHelper hp = new HttpHelper();
                String jsonstr = hp.makeServiceCall(url);

                //Log.d("omri1", jsonstr);

                try {
                    if (jsonstr != null) {
                        preferences = context.getSharedPreferences("omriPrefs",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("firstTime",true);
                        editor.commit();
                        //Log.d("omri2","working");
                        JSONArray movies = new JSONArray(jsonstr);
                        for (int i = 0; i < movies.length(); i++) {
                            JSONObject j = movies.getJSONObject(i);
                            String title = j.getString("title");
                            String imageUrl = j.getString("image");
                            Double rating = j.getDouble("rating");
                            int releaseYear = j.getInt("releaseYear");
                            JSONArray genres = j.getJSONArray("genre");
                            String genre = "";
                            for (int k = 0; k < genres.length(); k++) {
                                genre += genres.getString(k);
                                if (k < genres.length() - 1) {
                                    genre += ", ";
                                }
                            }
                            DatabaseHelper dbHelper = new DatabaseHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COL_MOVIE_TITLE, title);
                            cv.put(DatabaseHelper.COL_GENRE, genre);
                            cv.put(DatabaseHelper.COL_IMAGE_URL, imageUrl);
                            cv.put(DatabaseHelper.COL_RATING, rating);
                            cv.put(DatabaseHelper.COL_RELEASE_YEAR, releaseYear);
                            db.insert(DatabaseHelper.TABLE_NAME, null, cv);
                            context.startActivity(new Intent(context,MainActivity.class));

                        }

                    }
                    } catch(JSONException e){
                        e.printStackTrace();

                    }
            }
        }



