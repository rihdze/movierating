package com.movieratingsystem.main.business.mappers;


import com.movieratingsystem.main.business.repository.model.MovieDAO;
import com.movieratingsystem.main.model.Movie;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface MovieMapStructMapper {

    MovieDAO movieToMovieDAO(Movie movie);
    Movie movieDaoToMovie(MovieDAO movieDao);

}