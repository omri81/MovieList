package com.example.bar.movielist;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDescription extends AppCompatActivity {
    private Intent intent;
    private TextView titletv;
    private TextView releaseYeartv;
    private TextView ratingTv;
    private TextView genreTV;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        intent = getIntent();
        Movie m = (Movie) intent.getSerializableExtra("movie1");
        titletv = (TextView) findViewById(R.id.Movie_Description_title);
        releaseYeartv = (TextView) findViewById(R.id.Movie_Description_year);
        ratingTv = (TextView) findViewById(R.id.Movie_Description_ratings);
        genreTV = (TextView) findViewById(R.id.Movie_Description_genere);
        iv = (ImageView) findViewById(R.id.Movie_Description_image);
        //Log.d("image",m.getImageUrl());

        titletv.setText(m.getTitle());
        releaseYeartv.setText(m.getReleaseYear()+"");
        ratingTv.setText(m.getRating()+"");
        genreTV.setText(m.getGenre());
        Picasso.get().load(m.getImageUrl()).into(iv);

    }
}
