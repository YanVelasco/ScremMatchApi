package br.com.yanvelasco.screemmatch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DTODadosTemporadas(
        @JsonAlias("Season") Integer numeroTemporadas,
        @JsonAlias("Episodes") List<DTODadosEpisodio> episodios) {
}