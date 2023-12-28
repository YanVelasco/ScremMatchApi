package br.com.yanvelasco.screemmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        for (DTODadosTemporadas temporada : temporadas) {
            System.out.println("Temporada " + temporada.numeroTemporadas());
            List<DTODadosEpisodio> episodiosTemporada = temporada.episodios();
            for (DTODadosEpisodio episodio : episodiosTemporada) {
                System.out.println(episodio.titulo());
            }
        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DTODadosEpisodio> dtoDadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        dtoDadosEpisodios.stream()
        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
        // Debugando o código
        .peek(e -> System.out.println("Primeiro filtro N/A " + e))
        .sorted(Comparator.comparing(DTODadosEpisodio::avaliacao)
        .reversed())
        // Debugando o código
        .peek(e -> System.out.println("Ordenação " + e))
        .limit(5)
        // Debugando o código
        .peek(e -> System.out.println("Limite " + e))
        .map(e-> e.titulo().toUpperCase())
        // Debugando o código
        .peek(e -> System.out.println("Mapeamento " + e))
        .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporadas(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // filtrando buscas
        System.out.println("Digite um trecho do título do episódio:");
        var trechoDoTitulo = sc.nextLine().toUpperCase();
        Optional<Episodio> episodioBuscado = episodios.stream()
        .filter(e -> e.getTitulo().toUpperCase().contains(trechoDoTitulo))
        .findFirst();
        if (episodioBuscado.isPresent()) {
        System.out.println("Episódio encontrado");
        System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        }else{
        System.out.println("Episódio não encontrado");
        }

        // pesquisa por ano informado
        System.out.println("A partir de que ano você deseja ver os episodios?");
        var ano = sc.nextInt();
        sc.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
        .filter(e -> e.getDataLancamento() != null &&
        e.getDataLancamento().isAfter(dataBusca))
        .forEach(e -> System.out.println(
        "Temporada " + e.getTemporada() +
        " Episódio " + e.getTitulo() +
        " Data de lançamento " + e.getDataLancamento().format(formatador)));

        // Utilizando chave e valor
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                // pegando a média das avalições por temporada
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        // Utilizando uma classe de estatisticas que retorna o
        // DoubleSummaryStatistics{count=29, sum=251,100000, min=8,000000,
        // average=8,658621, max=9,500000}
        DoubleSummaryStatistics statistics = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + statistics.getAverage());
        System.out.println("Pior episódio: " + statistics.getMin());
        System.out.println("Melhor episódio: " + statistics.getMax());
        System.out.println("Total de episódios avaliados: " + statistics.getCount());
    }
}