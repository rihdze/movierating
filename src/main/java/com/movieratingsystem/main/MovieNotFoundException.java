package com.movieratingsystem.main;




public class MovieNotFoundException extends RuntimeException {

    MovieNotFoundException(String name){

        super("Could not find movie with name: " + name);


    }

}
