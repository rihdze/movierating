package com.movieratingsystem.main;

import javassist.NotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {

            return EntityModel.of(movie,
                    linkTo(methodOn(MovieController.class).one(movie.getName())).withSelfRel(),
                    linkTo(methodOn(MovieController.class).all()).withRel("movies"));

    }
}
