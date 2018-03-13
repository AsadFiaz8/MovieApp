package com.example.asadfiaz.movieapp;

/**
 * Created by Asad Fiaz on 2/14/2018.
 */

public class MovieModel {

    String poster;
    String title;
    String directedBy;
    String genre;
    String metaScore;
    String imbdRating;
    String year;

    public MovieModel(){}

    public MovieModel(String poster, String title, String directedBy, String genre, String metaScore, String imbdRating, String year) {
        this.poster = poster;
        this.title = title;
        this.directedBy = directedBy;
        this.genre = genre;
        this.metaScore = metaScore;
        this.imbdRating = imbdRating;
        this.year = year;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }

    public void setImbdRating(String imbdRating) {
        this.imbdRating = imbdRating;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public String getGenre() {
        return genre;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public String getImbdRating() {
        return imbdRating;
    }

    public String getYear() {
        return year;
    }
}
