package br.com.springdata.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.springdata.modelo.Cargo;
import br.com.springdata.modelo.Funcionario;
import br.com.springdata.repositorio.FuncionarioRepository;
import br.com.springdata.util.Operacoes;

@Service
public class FuncionarioService {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String SALVA_FUNCIONARIO = "1";
	private static final String BUSCA_FUNCIONARIOS = "2";
	private static final String ATUALIZA_FUNCIONARIO = "3";
	private static final String DELETA_FUNCIONARIO = "4";

	private Boolean ficaNoSistema = true;
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoService cargoService;
	private final Operacoes operacoes;
	
	public FuncionarioService(FuncionarioRepository funcionarioRepository, Operacoes operacoes,
			CargoService cargoService) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoService = cargoService;
		this.operacoes = operacoes;
	}

	public void inicia(Scanner entradaDeDados) {
		while(this.ficaNoSistema) {
			String opcaoEscolhida = this.operacoes.escolheOpcao(entradaDeDados, "FUNCIONARIO");
			
			switch (opcaoEscolhida) {
			case SAIR_DO_SISTEMA:
				this.ficaNoSistema = false;
				System.err.println("Voltando ao inicio!");
				System.out.println("");
				break;
			case SALVA_FUNCIONARIO:
				salva(entradaDeDados);
				break;
			case BUSCA_FUNCIONARIOS:
				this.operacoes.buscaTodos(this.funcionarioRepository);
				break;
			case ATUALIZA_FUNCIONARIO:
				atualiza(entradaDeDados);
				break;
			case DELETA_FUNCIONARIO:
				this.operacoes.deleta(this.funcionarioRepository, entradaDeDados, "funcionario");
				break;
			default:
				System.err.println("Opcao inexistente");
				break;
			}
		}
	}
	
	private void salva(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		System.out.print("Digite o nome do funcionario: ");
		String nome = entradaDeDados.nextLine();
		System.out.print("Digite o CPF do funcionario: ");
		String cpf = entradaDeDados.nextLine();
		BigDecimal salario = this.operacoes.pegaSomenteValorMonetario(entradaDeDados, 
				"Digite o salario do funcionario(Exemplo: 25.30): R$");
		LocalDate dataContratacao = this.operacoes.pegaSomenteDataSemHora(entradaDeDados, 
				"Digite a data em que o funcionario foi contratado(Exemplo: 25/12/2020): ");
		this.cargoService.exibeTodosCargos();
		Integer id = this.operacoes.pegaSomenteNumero(entradaDeDados, "Digite o ID do cargo do funcionario: ");
		Cargo cargo = this.cargoService.buscaCargoPorId(id);
		Funcionario funcionario = new Funcionario(nome, cpf, salario, dataContratacao, cargo);
		this.funcionarioRepository.save(funcionario);
		System.out.println("Funcionario SALVO!");
		System.out.println("------------------------------------");
	}
	
	private void atualiza(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		Integer id = this.operacoes.pegaSomenteNumero(entradaDeDados, "Digite o ID do funcionario: ");
		System.out.print("Digite o novo nome do funcionario: ");
		String nome = entradaDeDados.nextLine();
		System.out.print("Digite o novo CPF do funcionario: ");
		String cpf = entradaDeDados.nextLine();
		BigDecimal salario = this.operacoes.pegaSomenteValorMonetario(entradaDeDados, 
				"Digite o novo salario do funcionario(Exemplo: 25.30): R$");
		LocalDate dataContratacao = this.operacoes.pegaSomenteDataSemHora(entradaDeDados, 
				"Digite a nova data em que o funcionario foi contratado(Exemplo: 25/12/2020): ");
		this.cargoService.exibeTodosCargos();
		Integer idCargo = this.operacoes.pegaSomenteNumero(entradaDeDados, "Digite o novo ID do cargo do funcionario: ");
		Cargo cargo = this.cargoService.buscaCargoPorId(idCargo);
		Funcionario funcionario = new Funcionario(id, nome, cpf, salario, dataContratacao, cargo);
		this.funcionarioRepository.save(funcionario);
		System.out.println("Funcionario ATUALIZADO!");
		System.out.println("------------------------------------");
	}
	
}
