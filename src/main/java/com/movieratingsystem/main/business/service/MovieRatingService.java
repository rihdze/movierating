package com.movieratingsystem.main.business.service;

import com.movieratingsystem.main.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRatingService {

    Optional<Movie> findMovieById(int id);

    List<Movie> findAllMovies();

    Movie saveMovie(Movie movie) throws Exception;

    void deleteMovie(int id);

    void voteForMovie(Integer id, Integer rating);



}
