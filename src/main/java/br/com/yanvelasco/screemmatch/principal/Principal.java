package br.com.yanvelasco.screemmatch.principal;

import java.util.Scanner;

import br.com.yanvelasco.screemmatch.model.DTODadosSerie;
import br.com.yanvelasco.screemmatch.service.ConsumoApi;
import br.com.yanvelasco.screemmatch.service.ConverterDados;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e45f66b9";

    public void exibeMenu() {
        System.out.println("Digite o nome da série:");
        var nomeSerie = sc.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DTODadosSerie dtoDadosSerie = conversor.obterDados(json, DTODadosSerie.class);
        System.out.println(dtoDadosSerie);
        // List<DTODadosTemporadas>temporadas = new ArrayList<>();

		// for (int i = 1; i < dtoDadosSerie.totalDeTemporadas(); i++) {
		// 	json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=e45f66b9");
		// 	DTODadosTemporadas dtoDadosTemporadas = conversor.obterDados(json, DTODadosTemporadas.class);
		// 	temporadas.add(dtoDadosTemporadas);
		// }
		// temporadas.forEach(System.out::println);
    }
}