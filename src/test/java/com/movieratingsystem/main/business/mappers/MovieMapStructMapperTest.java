package com.movieratingsystem.main.business.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;




class MovieMapStructMapperTest {
    MovieMapStructMapper mapper = Mappers.getMapper(MovieMapStructMapper.class);
    @Test
    void movieToMovieDAO() {
    }

    @Test
    void movieDaoToMovie() {
    }
}