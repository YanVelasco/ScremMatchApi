package br.com.yanvelasco.screemmatch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.yanvelasco.screemmatch.model.DTODadosEpisodio;
import br.com.yanvelasco.screemmatch.model.DTODadosSerie;
import br.com.yanvelasco.screemmatch.model.DTODadosTemporadas;
import br.com.yanvelasco.screemmatch.model.Episodio;
import br.com.yanvelasco.screemmatch.service.ConsumoApi;
import br.com.yanvelasco.screemmatch.service.ConverterDados;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e45f66b9";

    public void exibeMenu() {
        System.out.println("Digite o nome da série:");
        var nomeSerie = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DTODadosSerie dtoDadosSerie = conversor.obterDados(json, DTODadosSerie.class);
        System.out.println(dtoDadosSerie);

        List<DTODadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i < dtoDadosSerie.totalDeTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DTODadosTemporadas dtoDadosTemporadas = conversor.obterDados(json, DTODadosTemporadas.class);
            temporadas.add(dtoDadosTemporadas);
        }
        temporadas.forEach(System.out::println);

        // for (DTODadosTemporadas temporada : temporadas) {
        // System.out.println("Temporada " + temporada.numeroTemporadas());
        // List<DTODadosEpisodio> episodiosTemporada = temporada.episodios();
        // for (DTODadosEpisodio episodio : episodiosTemporada) {
        // System.out.println(episodio.titulo());
        // }
        // }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DTODadosEpisodio> dtoDadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        dtoDadosEpisodios.stream()
        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
        .sorted(Comparator.comparing(DTODadosEpisodio::avaliacao)
        .reversed())
        .limit(5)
        .forEach(System.out::println);

        List<Episodio>episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numeroTemporadas(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}