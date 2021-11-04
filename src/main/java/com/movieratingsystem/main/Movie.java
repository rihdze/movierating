package com.movieratingsystem.main;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {


//    @GeneratedValue

    Long id;
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
        this.votes = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(genre, movie.genre) && Objects.equals(year, movie.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, year);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                '}';
    }
}
