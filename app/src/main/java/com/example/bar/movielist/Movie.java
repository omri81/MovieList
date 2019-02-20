package com.example.bar.movielist;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private int releaseYear;
    private String imageUrl;
    private Double rating;
    private String genre;

    public Movie(String title, int releaseYear, String imageUrl, Double rating, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }
}
