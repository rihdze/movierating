package com.movieratingsystem.main.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;


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

        if(this.votes == 0){
            return 0;
        } else {
            return this.rating/votes;
        }

    }



}


