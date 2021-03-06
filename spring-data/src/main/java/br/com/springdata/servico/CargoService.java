package br.com.springdata.servico;

import java.util.Scanner;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.springdata.modelo.Cargo;
import br.com.springdata.repositorio.CargoRepository;
import br.com.springdata.util.Operacoes;

@Service
public class CargoService {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String SALVA_CARGO = "1";
	private static final String BUSCA_CARGOS = "2";
	private static final String ATUALIZA_CARGO = "3";
	private static final String DELETA_CARGO = "4";
	private static final String BUSCA_CARGO_POR_LETRAS = "12";

	private Boolean ficaNoSistema = true;
	
	private final CargoRepository cargoRepository;
	private final Operacoes operacoes;
	
	public CargoService(CargoRepository cargoRepository, Operacoes operacoes) {
		this.cargoRepository = cargoRepository;
		this.operacoes = operacoes;
	}

	public void inicia(Scanner entradaDeDados) {
		this.ficaNoSistema = true;
		while(this.ficaNoSistema) {
			String opcaoEscolhida = this.operacoes.escolheOpcao(entradaDeDados, "CARGO");
			
			switch (opcaoEscolhida) {
			case SAIR_DO_SISTEMA:
				this.ficaNoSistema = false;
				System.err.println("Voltando ao inicio!");
				System.out.println("");
				break;
			case SALVA_CARGO:
				salva(entradaDeDados);
				break;
			case BUSCA_CARGOS:
				System.out.println("Deseja buscar cargos por: ");
				System.out.println("11 - Todos os cargos");
				System.out.println("12 - Digite letras e verifique se contem o cargo");
				System.out.print("R: ");
				String buscaCargo = entradaDeDados.nextLine();
				if(BUSCA_CARGO_POR_LETRAS.equalsIgnoreCase(buscaCargo)) {
					System.out.print("Digite as letras: ");
					String letras = entradaDeDados.nextLine();
					this.cargoRepository.findByDescricaoContaining(letras)
						.forEach(cargo -> System.out.println(cargo));
					break;
				}
				exibeTodosCargos();
				break;
			case ATUALIZA_CARGO:
				atualiza(entradaDeDados);
				break;
			case DELETA_CARGO:
				this.operacoes.deleta(this.cargoRepository, entradaDeDados, "cargo");
				break;
			default:
				System.err.println("Opcao inexistente");
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

	private void atualiza(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		Integer idCargo = this.operacoes.pegaSomenteNumero(entradaDeDados, "Digite o ID do cargo: ");
		System.out.print("Digite o novo nome do cargo: ");
		String nomeCargo = entradaDeDados.nextLine();
		Cargo cargo = new Cargo(idCargo, nomeCargo);
		this.cargoRepository.save(cargo);
		System.out.println("Cargo ATUALIZADO!");
		System.out.println("------------------------------------");
	}
	
	public void exibeTodosCargos() {
		Pageable paginacao = PageRequest.of(0, 10, Sort.by(Direction.ASC, "descricao"));
		this.operacoes.buscaTodosPaginados(this.cargoRepository, paginacao);
	}
	
	public Cargo buscaCargoPorId(Integer id) {
		return this.cargoRepository.findById(id).orElse(null);
	}
	
}
