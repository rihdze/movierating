package com.movieratingsystem.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieReposi) {

        return args -> {
            movieReposi.save(new Movie("V for Vendetta", "Scary", 1993));
            movieReposi.save(new Movie("Interstellar", "Space", 2010));

            movieReposi.findAll().forEach(employee -> log.info("Preloaded "+ employee));

//            orderRepository.save(new Order("MacBook pro", Status.COMPLETED));
//            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
//
//            orderRepository.findAll().forEach(order -> {
//                log.info("Preloaded " + order);
//            });

        };
    }
}