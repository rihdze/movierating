package com.movieratingsystem.main.business.mappers;




import javax.annotation.Generated;

import com.movieratingsystem.main.business.repository.model.MovieDAO;
import com.movieratingsystem.main.model.Movie;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-07-25T13:17:51+0300",
        comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.3.jar, environment: Java 1.8.0_321 (Oracle Corporation)"
)
@Component
public class MovieMapStructMapperImpl implements MovieMapStructMapper {

    @Override
    public MovieDAO movieToMovieDAO(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDAO movieDao = new MovieDAO();

        movieDao.setId( movie.getId() );
        movieDao.setName( movie.getName() );
        movieDao.setGenre( movie.getGenre() );
        movieDao.setRating( movie.getRating());
        movieDao.setVotes( movie.getVotes() );
        movieDao.setYear( movie.getYear() );

        return movieDao;
    }

    @Override
    public Movie movieDaoToMovie(MovieDAO movieDAO) {
        if ( movieDAO == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setId( movieDAO.getId() );
        movie.setName( movieDAO.getName() );
        movie.setGenre( movieDAO.getGenre() );
        movie.setRating( movieDAO.getRating() );
        movie.setVotes( movieDAO.getVotes() );
        movie.setYear( movieDAO.getYear() );

        return movie;
    }
}
