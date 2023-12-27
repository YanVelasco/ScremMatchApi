package br.com.yanvelasco.screemmatch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.yanvelasco.screemmatch.model.DTODadosEpisodio;
import br.com.yanvelasco.screemmatch.model.DTODadosSerie;
import br.com.yanvelasco.screemmatch.model.DTODadosTemporadas;
import br.com.yanvelasco.screemmatch.service.ConsumoApi;
import br.com.yanvelasco.screemmatch.service.ConverterDados;

@SpringBootApplication
public class ScreemmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreemmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=e45f66b9");
		System.out.println(json);
		ConverterDados conversor = new ConverterDados();
		DTODadosSerie dtoDadosSerie = conversor.obterDados(json, DTODadosSerie.class);
		System.out.println(dtoDadosSerie);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=e45f66b9");
		DTODadosEpisodio dtoDadosEpisodio = conversor.obterDados(json, DTODadosEpisodio.class);
		System.out.println(dtoDadosEpisodio);

		List<DTODadosTemporadas>temporadas = new ArrayList<>();

		for (int i = 1; i < dtoDadosSerie.totalDeTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=e45f66b9");
			DTODadosTemporadas dtoDadosTemporadas = conversor.obterDados(json, DTODadosTemporadas.class);
			temporadas.add(dtoDadosTemporadas);
		}
		temporadas.forEach(System.out::println);
	}
}