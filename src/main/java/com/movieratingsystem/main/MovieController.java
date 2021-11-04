package com.movieratingsystem.main;



import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MovieController {

    private final MovieModelAssembler assembler;
    private final MovieRepository repository;


    MovieController(MovieRepository repository, MovieModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/movies/top")
    CollectionModel<EntityModel<Movie>> topMovies(){
        List<EntityModel<Movie>> movies = repository.findAll(Sort.by(Sort.Direction.DESC, "rating"))
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(movies,
                linkTo(methodOn(MovieController.class).all()).withSelfRel());
    }

    @GetMapping("/movies")
    CollectionModel<EntityModel<Movie>> all(){
        List<EntityModel<Movie>> movies = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(movies,
                linkTo(methodOn(MovieController.class).all()).withSelfRel());

    }

    @RequestMapping(path = "/movies/vote/{name}/{rating}", method = RequestMethod.POST)
    EntityModel<Movie> vote(@PathVariable String name, @PathVariable int rating) {

        Movie movie = repository.findById(name)
                .orElseThrow(() -> new MovieNotFoundException(name));
        repository.findById(name)
                .get()
                .incrementVotes();
        repository.findById(name)
                .get()
                .setRating(rating);
        repository.save(movie);
        return assembler.toModel(movie);


    }

    @GetMapping("/movies/{name}")
    EntityModel<Movie> one(@PathVariable String name){
        Movie movie = repository.findById(name)
                .orElseThrow(() -> new MovieNotFoundException(name));

        return assembler.toModel(movie);
    }

    @PutMapping("/movies/{name}")
    ResponseEntity<?> replaceMovie(@RequestBody Movie newMovie, @PathVariable String name) {

        Movie updatedMovie = repository.findById(name)
                .map(movie -> {
                    movie.setName(name);
                    movie.setGenre(newMovie.getGenre());
                    movie.setYear(newMovie.getYear());
                    movie.setVotes(newMovie.getVotes());
                    movie.setRating((int)newMovie.getRating());

                    return repository.save(movie);
                })
                .orElseGet(() -> {
                    newMovie.setName(name);
                    return repository.save(newMovie);
                });

        EntityModel<Movie> entityModel = assembler.toModel(updatedMovie);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);


    }

    @PostMapping("/movies")
    ResponseEntity<?> newMovie(@RequestBody Movie newMovie) {

        EntityModel<Movie> entityModel = assembler.toModel(repository.save(newMovie));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


}
