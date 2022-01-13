package br.com.springdata.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class Operacoes {
	
	public void buscaTodos(CrudRepository<?, Integer> repository) {
		System.out.println("------------------------------------");
		Iterable<?> objetos = repository.findAll();
		objetos.forEach(objeto -> System.out.println(objeto));
		System.out.println("------------------------------------");
	}
	
	public void deleta(CrudRepository<?, Integer> repository, Scanner entradaDeDados,
			String objetoEscolhido) {
		System.out.println("------------------------------------");
		Integer id = pegaSomenteNumero(entradaDeDados, "Digite o ID "
				+ objetoEscolhido + ": ");
		repository.deleteById(id);
		System.out.println(objetoEscolhido + " DELETADO!");
		System.out.println("------------------------------------");
	}
	
	public String escolheOpcao(Scanner entradaDeDados, String objetoEscolhido) {
		System.out.println("Escolha qual acao deseja realizar com " + objetoEscolhido + ":");
		System.out.println("0 - Voltar ao inicio");
		System.out.println("1 - Salvar " + objetoEscolhido);
		System.out.println("2 - Buscar " + objetoEscolhido);
		System.out.println("3 - Atualizar " + objetoEscolhido);
		System.out.println("4 - Deletar " + objetoEscolhido);
		System.out.print("R: ");
		return entradaDeDados.nextLine();
	}
	
	public Integer pegaSomenteNumero(Scanner entradaDeDados, String mensagem) {
		Boolean ehNumeroErrado = true;
		while(ehNumeroErrado) {
			System.out.print(mensagem);
			String valor = entradaDeDados.nextLine();
			try {
				Integer valorInteiro = Integer.parseInt(valor);
				ehNumeroErrado = false;
				return valorInteiro;
			} catch (Exception e) {
				System.err.println("----------------------------------------------------------------");
				System.err.println("ATENCAO: Nao foi digitado um valor numerico, digite corretamente!");
				System.err.println("----------------------------------------------------------------");
			}			
		}
		return 0;
	}
	
	public BigDecimal pegaSomenteValorMonetario(Scanner entradaDeDados, String mensagem) {
		Boolean ehNumeroErrado = true;
		while(ehNumeroErrado) {
			System.out.print(mensagem);
			String valor = entradaDeDados.nextLine();
			try {
				BigDecimal valorMonetario = new BigDecimal(valor);
				ehNumeroErrado = false;
				return valorMonetario;
			} catch (Exception e) {
				System.err.println("----------------------------------------------------------------");
				System.err.println("ATENCAO: Nao foi digitado um valor monetario, digite corretamente!\n"
						+ "Exemplo: 25.30");
				System.err.println("----------------------------------------------------------------");
			}			
		}
		return BigDecimal.ZERO;
	}
	
	public LocalDate pegaSomenteDataSemHora(Scanner entradaDeDados, String mensagem) {
		Boolean ehValorErrado = true;
		while(ehValorErrado) {
			System.out.print(mensagem);
			String valor = entradaDeDados.nextLine();
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate data = LocalDate.parse(valor, formatter);
				ehValorErrado = false;
				return data;
			} catch (Exception e) {
				System.err.println("----------------------------------------------------------------");
				System.err.println("ATENCAO: Nao foi digitado uma data correta, digite corretamente!\n"
						+ "Exemplo: 25/12/2020");
				System.err.println("----------------------------------------------------------------");
			}			
		}
		return LocalDate.of(0000, 1, 1);
	}
	
	public List<Integer> pegaSomenteListaDeNumero(Scanner entradaDeDados, String mensagem) {
		Boolean ehNumeroErrado = true;
		while(ehNumeroErrado) {
			System.out.print(mensagem);
			String valor = entradaDeDados.nextLine();
			try {
				List<Integer> numerosRetornados = new ArrayList<Integer>();
				String[] numeros = valor.split(",");
				for (String numero : numeros) {
					Integer valorInteiro = Integer.parseInt(numero);
					numerosRetornados.add(valorInteiro);
				}
				ehNumeroErrado = false;
				return numerosRetornados;
			} catch (Exception e) {
				System.err.println("----------------------------------------------------------------");
				System.err.println("ATENCAO: Nao foi digitado um valor numerico, digite corretamente!");
				System.err.println("----------------------------------------------------------------");
			}			
		}
		return List.of();
	}

}
