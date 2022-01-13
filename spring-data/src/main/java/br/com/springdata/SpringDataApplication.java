package br.com.springdata;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.springdata.servico.CargoService;
import br.com.springdata.servico.FuncionarioService;
import br.com.springdata.servico.UnidadeDeTrabalhoService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String CARGO = "1";
	private static final String FUNCIONARIO = "2";
	private static final String UNIDADE_DE_TRABALHO = "3";
	
	private Boolean ficaNoSistema = true;
	
	private final CargoService cargoService;
	private final FuncionarioService funcionarioService;
	private final UnidadeDeTrabalhoService unidadeDeTrabalhoService;
	
	public SpringDataApplication(CargoService cargoService,
			FuncionarioService funcionarioService,
			UnidadeDeTrabalhoService unidadeDeTrabalhoService) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeDeTrabalhoService = unidadeDeTrabalhoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner entradaDeDados = new Scanner(System.in);
		
		while(this.ficaNoSistema) {
			System.out.println("Escolha qual informacao voce deseja saber:");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionario");
			System.out.println("3 - Unidade de Trabalho");
			System.out.print("R: ");
			String opcaoEscolhida = entradaDeDados.nextLine();
			
			switch (opcaoEscolhida) {
			case SAIR_DO_SISTEMA:
				this.ficaNoSistema = false;
				System.err.println("Muito obrigado!");
				break;
			case CARGO:
				this.cargoService.inicia(entradaDeDados);
				break;
			case FUNCIONARIO:
				this.funcionarioService.inicia(entradaDeDados);
				break;
			case UNIDADE_DE_TRABALHO:
				this.unidadeDeTrabalhoService.inicia(entradaDeDados);
				break;
			default:
				System.err.println("Opcao inexistente");
				break;
			}
		}
		
		
	}

}
