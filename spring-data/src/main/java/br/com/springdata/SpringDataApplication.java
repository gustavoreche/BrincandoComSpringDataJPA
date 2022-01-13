package br.com.springdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.springdata.servico.CargoService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {
	
	private final CargoService cargoService;
	
	public SpringDataApplication(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.cargoService.inicia();
	}

}
