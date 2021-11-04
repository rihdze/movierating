package com.movieratingsystem.main;

import org.springframework.data.jpa.repository.JpaRepository;



public interface MovieRepository extends JpaRepository<Movie, String> {

}
