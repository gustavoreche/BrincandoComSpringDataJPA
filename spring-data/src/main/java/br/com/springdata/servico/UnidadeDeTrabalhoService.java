package br.com.springdata.servico;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.springdata.modelo.UnidadeDeTrabalho;
import br.com.springdata.repositorio.UnidadeDeTrabalhoRepository;
import br.com.springdata.util.Operacoes;

@Service
public class UnidadeDeTrabalhoService {
	
	private static final String SAIR_DO_SISTEMA = "0";
	private static final String SALVA_UNIDADE_DE_TRABALHO = "1";
	private static final String BUSCA_UNIDADES_DE_TRABALHO = "2";
	private static final String ATUALIZA_UNIDADE_DE_TRABALHO = "3";
	private static final String DELETA_UNIDADE_DE_TRABALHO = "4";
	private static final String BUSCA_UNIDADE_DE_TRABALHO_POR_NOME_POR_LETRAS = "12";
	private static final String BUSCA_UNIDADE_DE_TRABALHO_POR_ENDERECO_POR_LETRAS = "13";

	private Boolean ficaNoSistema = true;
	
	private final UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository;
	private final Operacoes operacoes;
	
	public UnidadeDeTrabalhoService(UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository,
			Operacoes operacoes) {
		this.unidadeDeTrabalhoRepository = unidadeDeTrabalhoRepository;
		this.operacoes = operacoes;
	}

	public void inicia(Scanner entradaDeDados) {
		this.ficaNoSistema = true;
		while(this.ficaNoSistema) {
			String opcaoEscolhida = this.operacoes.escolheOpcao(entradaDeDados, "UNIDADE DE TRABALHO");
			
			switch (opcaoEscolhida) {
			case SAIR_DO_SISTEMA:
				this.ficaNoSistema = false;
				System.err.println("Voltando ao inicio!");
				System.out.println("");
				break;
			case SALVA_UNIDADE_DE_TRABALHO:
				salva(entradaDeDados);
				break;
			case BUSCA_UNIDADES_DE_TRABALHO:
				System.out.println("Deseja buscar unidades de trabalho por: ");
				System.out.println("11 - Todas as unidades de trabalho");
				System.out.println("12 - Digite letras e verifique se contem a unidade de trabalho por NOME");
				System.out.println("13 - Digite letras e verifique se contem a unidade de trabalho por ENDERECO");
				System.out.print("R: ");
				String buscaUnidade = entradaDeDados.nextLine();
				if(BUSCA_UNIDADE_DE_TRABALHO_POR_NOME_POR_LETRAS.equalsIgnoreCase(buscaUnidade) ||
						BUSCA_UNIDADE_DE_TRABALHO_POR_ENDERECO_POR_LETRAS.equalsIgnoreCase(buscaUnidade)) {
					System.out.print("Digite as letras: ");
					String letras = entradaDeDados.nextLine();
					if(BUSCA_UNIDADE_DE_TRABALHO_POR_NOME_POR_LETRAS.equalsIgnoreCase(buscaUnidade)) {
						this.unidadeDeTrabalhoRepository.procuraUnidadeDeTrabalhoPorNomeSeContemLetras(letras)
						.forEach(unidadeDeTrabalho -> System.out.println(unidadeDeTrabalho));						
					} else {
						this.unidadeDeTrabalhoRepository.procuraUnidadeDeTrabalhoPorEndercoSeContemLetras(letras)
						.forEach(unidadeDeTrabalho -> System.out.println(unidadeDeTrabalho));
					}
					break;
				}
				exibeTodasUnidadesDeTrabalho();
				break;
			case ATUALIZA_UNIDADE_DE_TRABALHO:
				atualiza(entradaDeDados);
				break;
			case DELETA_UNIDADE_DE_TRABALHO:
				this.operacoes.deleta(this.unidadeDeTrabalhoRepository, entradaDeDados, "unidade de trabalho");
				break;
			default:
				System.err.println("Opcao inexistente");
				break;
			}
		}
	}
	
	private void salva(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		System.out.print("Digite o nome da unidade de trabalho: ");
		String nome = entradaDeDados.nextLine();
		System.out.print("Digite o endereco da unidade de trabalho: ");
		String endereco = entradaDeDados.nextLine();
		UnidadeDeTrabalho unidadeDeTrabalho = new UnidadeDeTrabalho(nome, endereco);
		this.unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);
		System.out.println("Unidade de trabalho SALVA!");
		System.out.println("------------------------------------");
	}

	private void atualiza(Scanner entradaDeDados) {
		System.out.println("------------------------------------");
		Integer id = this.operacoes.pegaSomenteNumero(entradaDeDados, "Digite o ID da unidade de trabalho: ");
		System.out.print("Digite o novo nome da unidade de trabalho: ");
		String nome = entradaDeDados.nextLine();
		System.out.print("Digite o novo endereco da unidade de trabalho: ");
		String endereco = entradaDeDados.nextLine();
		UnidadeDeTrabalho unidadeDeTrabalho = new UnidadeDeTrabalho(id, nome, endereco);
		this.unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);
		System.out.println("Unidade de trabalho ATUALIZADA!");
		System.out.println("------------------------------------");
	}
	
	public void exibeTodasUnidadesDeTrabalho() {
		this.operacoes.buscaTodos(this.unidadeDeTrabalhoRepository);
	}
	
	public UnidadeDeTrabalho buscaPorId(Integer id) {
		return this.unidadeDeTrabalhoRepository.findById(id).orElse(null);
	}
	
}
