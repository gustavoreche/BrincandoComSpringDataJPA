package br.com.springdata.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.springdata.modelo.Funcionario;

public class FuncionarioSpecification {
	
	public static Specification<Funcionario> nome(String nome) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}
	
	public static Specification<Funcionario> salario(BigDecimal salario) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.greaterThanOrEqualTo(root.get("salario"), salario);
	}
	
	public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.greaterThanOrEqualTo(root.get("dataContratacao"), dataContratacao);
	}

}
