package com.movieratingsystem.main.web.controller;

import com.movieratingsystem.main.business.service.MovieRatingService;
import com.movieratingsystem.main.model.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@Log4j2
@RestController
@RequestMapping("/Movies")
public class MovieController {

    @Autowired
    MovieRatingService movieRatingService;


    @GetMapping
    public ResponseEntity<List<Movie>> findAllMovies() {

        log.info("Retrieve list of movies");
        List<Movie> movieList = movieRatingService.findAllMovies();

        log.debug("Movies list is found. Size: {}", movieList::size);

        return ResponseEntity.ok(movieList);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> saveInternStaffing(@RequestBody Movie movie,
                                                             BindingResult bindingResult) throws Exception {

        if(movie.getId() != null){
            return ResponseEntity.badRequest().build();
        }

        log.info("Create new Movie staffing by passing : {}", movie);

        if (bindingResult.hasErrors()) {
            log.error("New movie was not created: error {}", bindingResult);
            return ResponseEntity.badRequest().build();
        }
        Movie movieSaved = movieRatingService.saveMovie(movie);
        log.debug("New movie is created: {}", movieSaved);
        return new ResponseEntity<>(movieSaved, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@NonNull @PathVariable Integer id) {
        log.info("Find movie by passing ID of movie, where movie ID is :{} ", id);
        Optional<Movie> movie = (movieRatingService.findMovieById(id));
        if (!movie.isPresent()) {
            log.warn("Movie {} is not found.", id);
            return ResponseEntity.notFound().build();
        }

        log.debug("Movie with id {} is found: {}", id, movie.get());
        return ResponseEntity.ok(movie.get());
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteInternStaffingById(@NonNull @PathVariable Integer id) {

        log.info("Delete movie by passing ID, where ID is {}", id);
        Optional<Movie> movie = movieRatingService.findMovieById(id);
        if (!movie.isPresent()) {
            log.warn("Movie with id {} for delete was not found", id);
            return ResponseEntity.notFound().build();
        }
        movieRatingService.deleteMovie(id);
        log.debug("Movie with id {} was deleted {}", id, movie.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> updateInternStaffingById(@PathVariable Integer id, @RequestBody Movie movie) throws Exception {
        log.info("Update existing Movie with ID: {} and new body: {}", id, movie);
        Optional<Movie> savedMovie = movieRatingService.findMovieById(id);
        if (savedMovie.isPresent()) {
            movie.setId(id);
            movieRatingService.saveMovie(movie);
            log.debug("Movie record with id {} is updated successfully: {}", id, movie);
            return new ResponseEntity<>(movie, HttpStatus.CREATED);
        } else
            log.warn("Movie with id {} for update is not found", id);
        return ResponseEntity.notFound().build();
    }




    @PostMapping("/vote/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void voteForMovie(@PathVariable Integer id, @RequestParam Integer rating){
        Optional<Movie> savedMovie = movieRatingService.findMovieById(id);
        if (savedMovie.isPresent()) {
            movieRatingService.voteForMovie(id, rating);
        } else
            log.warn("Movie with id {} for voting is not found", id);
    }

}