package br.com.yanvelasco.screemmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.yanvelasco.screemmatch.principal.Principal;

@SpringBootApplication
public class ScreemmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreemmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}