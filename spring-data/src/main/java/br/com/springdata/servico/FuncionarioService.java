package br.com.springdata.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.springdata.modelo.Cargo;
import br.com.springdata.modelo.Funcionario;
import br.com.springdata.modelo.UnidadeDeTrabalho;
import br.com.springdata.repositorio.FuncionarioRepository;
import br.com.springdata.specification.FuncionarioSpecification;
import br.com.springdata.util.Operacoes;

@Service
public class FuncionarioService {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String SALVA_FUNCIONARIO = "1";
	private static final String BUSCA_FUNCIONARIOS = "2";
	private static final String ATUALIZA_FUNCIONARIO = "3";
	private static final String DELETA_FUNCIONARIO = "4";
	private static final String EXIBE_SOMENTE_ID_NOME_SALARIO = "12";
	private static final String BUSCA_DINAMICA = "13";

	private Boolean ficaNoSistema = true;
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoService cargoService;
	private final UnidadeDeTrabalhoService unidadeDeTrabalhoService;
	private final Operacoes operacoes;
	
	public FuncionarioService(FuncionarioRepository funcionarioRepository, Operacoes operacoes,
			CargoService cargoService, UnidadeDeTrabalhoService unidadeDeTrabalhoService) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoService = cargoService;
		this.unidadeDeTrabalhoService = unidadeDeTrabalhoService;
		this.operacoes = operacoes;
	}

	public void inicia(Scanner entradaDeDados) {
		this.ficaNoSistema = true;
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
				System.out.println("Deseja buscar funcionarios por: ");
				System.out.println("11 - Todos os atributos");
				System.out.println("12 - Somente IDs, nomes e salarios dos funcionarios");
				System.out.println("13 - Busca por nome, salario ou data de contratacao");
				System.out.print("R: ");
				String exibeSomenteAlgunsDados = entradaDeDados.nextLine();
				if(EXIBE_SOMENTE_ID_NOME_SALARIO.equalsIgnoreCase(exibeSomenteAlgunsDados)) {
					this.funcionarioRepository.exibeSomenteIdNomeESalario()
						.forEach(cargo -> System.out.println("ID: " + cargo.getId()
								+ " || Nome: " + cargo.getNome()
								+ " || Salario: R$" + cargo.getSalario()));
					break;
				} else if(BUSCA_DINAMICA.equalsIgnoreCase(exibeSomenteAlgunsDados)) {
					String nome = null;
					BigDecimal salario = null;
					LocalDate dataContratacao = null;
					System.out.print("Digite o nome(ou aperte ENTER para pesquisar por outro parametro): ");
					String nomeDigitado = entradaDeDados.nextLine();
					if(Objects.nonNull(nomeDigitado) && !nomeDigitado.trim().isEmpty()) {
						nome = nomeDigitado;
					}
					System.out.print("Digite salario(ou aperte ENTER para pesquisar por outro parametro)(Exemplo: 25.30) R$: ");
					String salarioDigitado = entradaDeDados.nextLine();
					if(Objects.nonNull(salarioDigitado) && !salarioDigitado.trim().isEmpty()) {
						salario = new BigDecimal(salarioDigitado);
					}
					System.out.print("Digite a data de contratacao(ou aperte ENTER para pesquisar por outro parametro)(Exemplo: 25/12/2020): ");
					String dataDigitada = entradaDeDados.nextLine();
					if(Objects.nonNull(dataDigitada) && !dataDigitada.trim().isEmpty()) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						dataContratacao = LocalDate.parse(dataDigitada, formatter);
					}
					this.funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.nome(nome))
							.or(FuncionarioSpecification.salario(salario))
							.or(FuncionarioSpecification.dataContratacao(dataContratacao)))
					.forEach(funcionario -> System.out.println(funcionario));
					break;
				}
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
		this.unidadeDeTrabalhoService.exibeTodasUnidadesDeTrabalho();
		List<Integer> idsUnidadeDeTrabalho = this.operacoes.pegaSomenteListaDeNumero(entradaDeDados, 
				"Digite os ID da unidade de trabalho do funcionario, separados por virgula(Exemplo: 1, 2, 3): ");
		List<UnidadeDeTrabalho> unidadesDeTrabalho = new ArrayList<UnidadeDeTrabalho>();
		idsUnidadeDeTrabalho.forEach(idUnidade -> unidadesDeTrabalho.add(this.unidadeDeTrabalhoService
				.buscaPorId(idUnidade)));
		Funcionario funcionario = new Funcionario(nome, cpf, salario, dataContratacao, cargo, unidadesDeTrabalho);
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
		this.unidadeDeTrabalhoService.exibeTodasUnidadesDeTrabalho();
		List<Integer> idsUnidadeDeTrabalho = this.operacoes.pegaSomenteListaDeNumero(entradaDeDados, 
				"Digite os novos ID da unidade de trabalho do funcionario, separados por virgula(Exemplo: 1, 2, 3): ");
		List<UnidadeDeTrabalho> unidadesDeTrabalho = new ArrayList<UnidadeDeTrabalho>();
		idsUnidadeDeTrabalho.forEach(idUnidade -> unidadesDeTrabalho.add(this.unidadeDeTrabalhoService
				.buscaPorId(idUnidade)));
		Funcionario funcionario = new Funcionario(id, nome, cpf, salario, dataContratacao, cargo, unidadesDeTrabalho);
		this.funcionarioRepository.save(funcionario);
		System.out.println("Funcionario ATUALIZADO!");
		System.out.println("------------------------------------");
	}
	
}
