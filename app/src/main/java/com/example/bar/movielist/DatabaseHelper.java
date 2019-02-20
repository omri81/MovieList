package com.example.bar.movielist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "moviesDB";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "movies";
    public static final String COL_ID = "id";
    public static final String COL_MOVIE_TITLE = "moviesTitle";
    public static final String COL_IMAGE_URL = "imageURL";
    public static final String COL_RATING = "rating";
    public static final String COL_RELEASE_YEAR = "releaseYear";
    public static final String COL_GENRE = "genre";



    private static final String CREATE_COMMAND = "CREATE TABLE "+TABLE_NAME+" ("+
            COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_MOVIE_TITLE+" TEXT, "+
            COL_IMAGE_URL+" TEXT, "+
            COL_RATING+" REAL, "+
            COL_RELEASE_YEAR+ " INTEGER, "+
            COL_GENRE+ " TEXT"+
            ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME );
        onCreate(db);
    }
}
