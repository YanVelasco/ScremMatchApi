package br.com.yanvelasco.screemmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DTODadosSerie(
        @JsonAlias("title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String avaliacao) {
}