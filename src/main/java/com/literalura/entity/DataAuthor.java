package com.literalura.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataAuthor(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") int birthYear,
        @JsonAlias("death_year") int deathYear
) {
}
