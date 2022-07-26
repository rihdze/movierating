package com.movieratingsystem.main.business.service.impl;

import com.movieratingsystem.main.business.mappers.MovieMapStructMapper;
import com.movieratingsystem.main.business.repository.MovieRepository;
import com.movieratingsystem.main.business.repository.model.MovieDAO;
import com.movieratingsystem.main.business.service.MovieRatingService;
import com.movieratingsystem.main.model.Movie;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MovieRatingServiceImpl implements MovieRatingService {



    @Autowired
    MovieRepository movieRepository;

    MovieMapStructMapper mapper = Mappers.getMapper(MovieMapStructMapper.class);

    @Override
    public Optional<Movie> findMovieById(int id) {

        log.debug("Find movie with id {}", id);
        Optional<Movie> moviesFoundById = movieRepository.findById(id)
                .flatMap(internStaffing -> Optional.ofNullable(mapper.movieDaoToMovie(internStaffing)));

        moviesFoundById.ifPresent(internStaffing -> log.debug("Movie with id {} is {}: ", id, internStaffing));
        return moviesFoundById;

    }


    @Override
    public List<Movie> findAllMovies() {

        log.debug("Requesting list of all movies");
        List<MovieDAO> movieList = movieRepository.findAll();
        log.debug("Get movie list. Size is: {}", movieList::size);
        return movieList.stream().map(mapper::movieDaoToMovie).collect(Collectors.toList());

    }


    @Override
    public Movie saveMovie(Movie movie) {

        log.debug("Saving movie {}", movie);

        MovieDAO movieSaved = movieRepository.save(mapper.movieToMovieDAO(movie));
        log.debug("New movie saved: {}", () -> movie);
        return mapper.movieDaoToMovie(movieSaved);
    }

    @Override
    public void deleteMovie(int id) {
        log.debug("Deleting movie with id {}", id);
        movieRepository.deleteById(id);
        log.debug("Movie with id {} was deleted", id);

    }

    @Override
    public void voteForMovie(Integer id, Integer rating) {

        Movie movie = mapper.movieDaoToMovie(movieRepository.getById(id));

        movie.rateMovie(rating);
        saveMovie(movie);

    }


}
