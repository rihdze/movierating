package com.movieratingsystem.main.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieratingsystem.main.business.service.MovieRatingService;
import com.movieratingsystem.main.model.Movie;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    private Integer id = nextInt();

    private String name = RandomStringUtils.random(5, true, false);

    private String genre = RandomStringUtils.random(5, true, false);

    private Integer year = nextInt();



    public static String URL = "/Movies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieController controller;

    @MockBean
    private MovieRatingService service;

    @Test
    void testFindAllMovies() throws Exception {

        List<Movie> movieList = createMovieList();
        when(service.findAllMovies()).thenReturn(movieList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(this.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(this.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value(this.genre))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(this.year))
                .andExpect(status().isOk());

    }
    @Test
    void testFindAllMoviesEmpty() throws Exception {

        List<Movie> movieList = createMovieList();
        movieList.clear();

        when(service.findAllMovies()).thenReturn(movieList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .content(asJsonString(movieList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindMovieById() throws Exception {

        Optional<Movie> movie = Optional.of(createMovie());
        when(service.findMovieById(anyInt())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/" + this.id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(this.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(this.genre))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(this.year))
                .andExpect(status().isOk());

    }

    @Test
    void testFindMovieByIdInvalid() throws Exception{
        Optional<Movie> movie = Optional.of(createMovie());
        movie.get().setId(2);

        when(service.findMovieById(2)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1")
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveMovie() throws Exception {

        Movie movie = createMovie();
        movie.setId(null);
        when(service.saveMovie(movie)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void testSaveMovieInvalidWithIdNotNull() throws Exception {

        Movie movie = createMovie();


        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void testUpdateMovieById() throws Exception {
        Movie movie = createMovie();

        when(service.findMovieById(movie.getId())).thenReturn(Optional.of(movie));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/"+ this.id)
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.id))
                .andExpect(status().isCreated());

    }

    @Test
    void testUpdateMovieByIdInvalid() throws Exception {
        Movie movie = createMovie();
        movie.setId(null);
        when(service.findMovieById(2)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test
    void testDeleteMovieById() throws Exception{
        Optional<Movie> movie = Optional.of(createMovie());
        when(service.findMovieById(anyInt())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/"+this.id)
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void testDeleteMovieByIdInvalid() throws Exception{
        Optional<Movie> movie = Optional.of(createMovie());
        movie.get().setId(2);

        when(service.findMovieById(2)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1")
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    private Movie createMovie() {

        return Movie.builder()
                .id(this.id)
                .name(name)
                .genre(genre)
                .year(year)
                .build();

    }

    private List<Movie> createMovieList() {

        List<Movie> list = new ArrayList<>();
        Movie isOne = createMovie();
        Movie isTwo = createMovie();
        list.add(isOne);
        list.add(isTwo);
        return list;

    }

    public static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}