package br.com.yanvelasco.screemmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.yanvelasco.screemmatch.model.DTODadosSerie;
import br.com.yanvelasco.screemmatch.service.ConsumoApi;
import br.com.yanvelasco.screemmatch.service.ConverterDados;

@SpringBootApplication
public class ScreemmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreemmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?i=tt3896198&apikey=e45f66b9");
		System.out.println(json);
		ConverterDados conversor = new ConverterDados();
		DTODadosSerie dados = conversor.obterDados(json, DTODadosSerie.class);
		System.out.println(dados);
	}
}