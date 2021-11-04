package com.movieratingsystem.main;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {


//    @GeneratedValue


    @Id
    private String name;
    private String genre;
    private int year;
    private int rating;
    private int votes;

    Movie(){}

    Movie(String name, String genre, int year){
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.rating = 0;
        this.votes = 0;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return (double)rating/votes;
    }

    public void setRating(int rating) {
        this.rating += rating;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes(){
        this.votes++;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year && rating == movie.rating && votes == movie.votes && Objects.equals(name, movie.name) && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, year, rating, votes);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", votes=" + votes +
                '}';
    }
}
