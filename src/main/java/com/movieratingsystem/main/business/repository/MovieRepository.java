package com.movieratingsystem.main.business.repository;


import com.movieratingsystem.main.business.repository.model.MovieDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<MovieDAO, Integer> {

}
