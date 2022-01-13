package br.com.springdata.servico;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.springdata.modelo.Cargo;
import br.com.springdata.repositorio.CargoRepository;

@Service
public class CargoService {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String SALVA_CARGO = "1";

	private Boolean ficaNoSistema = true;
	
	private final CargoRepository cargoRepository;
	
	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicia() {
		Scanner entradaDeDados = new Scanner(System.in);
		
		while(this.ficaNoSistema) {
			System.out.println("Escolha qual acao deseja realizar:");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar um cargo");
			System.out.print("R: ");
			String opcaoEscolhida = entradaDeDados.nextLine();
			
			switch (opcaoEscolhida) {
			case SAIR_DO_SISTEMA:
				this.ficaNoSistema = false;
				System.err.println("Muito obrigado!");
				break;
			case SALVA_CARGO:
				salva(entradaDeDados);
				break;
			default:
				break;
			}
		}
	}
	
	private void salva(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		System.out.print("Digite o nome do cargo: ");
		String nomeCargo = entradaDeDados.nextLine();
		Cargo cargo = new Cargo(nomeCargo);
		this.cargoRepository.save(cargo);
		System.out.println("Cargo SALVO!");
		System.out.println("------------------------------------");
	}

}
