package com.movieratingsystem.main.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {


    @NotNull
    private Integer id;

    private String name;

    private String genre;

    private Integer year;
    private double rating;
    private int votes;
    public void rateMovie(double rating){
        this.rating += rating;
        this.votes++;
    }

    public double getRating(){

        return this.rating;

    }



}


