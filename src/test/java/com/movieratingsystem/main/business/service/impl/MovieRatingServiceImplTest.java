package com.movieratingsystem.main.business.service.impl;

import com.movieratingsystem.main.business.repository.MovieRepository;
import com.movieratingsystem.main.business.repository.model.MovieDAO;
import com.movieratingsystem.main.model.Movie;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieRatingServiceImplTest {
    private Integer id = nextInt();

    private String name = RandomStringUtils.random(5, true, false);

    private String genre = RandomStringUtils.random(5, true, false);

    private Integer year = nextInt();

    @Mock
    private MovieRepository repository;
    @InjectMocks
    private MovieRatingServiceImpl service;

    private Movie movie;
    private MovieDAO movieDAO;
    private List<MovieDAO> movieDAOS;


    @BeforeEach
    public void init() {
        movie = createMovie();
        movieDAO = createMovieDAO();
        movieDAOS = createMovieDAOList(movieDAO);

    }
    @Test
    void testFindAllMovies() throws Exception {
        when(repository.findAll()).thenReturn(movieDAOS);

        List<Movie> internStaffingList = service.findAllMovies();
        assertEquals(2, internStaffingList.size());
    }

    @Test
    void testFindAllMoviesInvalid() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertTrue(service.findAllMovies().isEmpty());
    }

    @Test
    void testFindMovieById() throws Exception {
        when(repository.findById(anyInt())).thenReturn(Optional.of(movieDAO));

        Optional<Movie> returnedMovie = service.findMovieById(movie.getId());
        assertEquals(movie.getId(), returnedMovie.get().getId());
        assertEquals(movie.getName(), returnedMovie.get().getName());
        assertEquals(movie.getGenre(), returnedMovie.get().getGenre());
        assertEquals(movie.getYear(), returnedMovie.get().getYear());

    }

    @Test
    void testFindMovieByIdInvalid() throws Exception {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertFalse(service.findMovieById(anyInt()).isPresent());
    }

    @Test
    void testSaveMovie() throws Exception {
        when(repository.save(movieDAO)).thenReturn(movieDAO);

        Movie movieSaved = service.saveMovie(movie);
        assertEquals(movie, movieSaved);
    }

    @Test
    void testSaveMovieInvalid() throws Exception {
        when(repository.save(movieDAO)).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveMovie(movie));
    }

    @Test
    void testDeleteMovieById() {
        service.deleteMovie(anyInt());

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteMovieByIdInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(repository).deleteById(anyInt());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.deleteMovie(5));

    }


    private Movie createMovie() {

        return Movie.builder()
                .id(this.id)
                .name(name)
                .genre(genre)
                .year(year)
                .votes(0)
                .build();

    }

    private MovieDAO createMovieDAO() {

        return MovieDAO.builder()
                .id(this.id)
                .name(name)
                .genre(genre)
                .year(year)
                .votes(0)
                .build();

    }


    private List<MovieDAO> createMovieDAOList(MovieDAO movieDAO) {
        List<MovieDAO> list = new ArrayList<>();
        list.add(movieDAO);
        list.add(movieDAO);
        return list;
    }

}