package br.com.springdata.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario {
	
	public Funcionario() {
	}
	
	public Funcionario(Integer id, String nome, String cpf, BigDecimal salario, LocalDate dataContratacao,
			Cargo cargo, List<UnidadeDeTrabalho> unidadesDeTrabalho) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
		this.cargo = cargo;
		this.unidadesDeTrabalho = unidadesDeTrabalho;
	}
	
	public Funcionario(String nome, String cpf, BigDecimal salario, LocalDate dataContratacao,
			Cargo cargo, List<UnidadeDeTrabalho> unidadesDeTrabalho) {
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
		this.cargo = cargo;
		this.unidadesDeTrabalho = unidadesDeTrabalho;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private String cpf;
	
	private BigDecimal salario;
	
	private LocalDate dataContratacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cargo cargo;
	
	@ManyToMany
	@JoinTable
	private List<UnidadeDeTrabalho> unidadesDeTrabalho;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public LocalDate getDataContratacao() {
		return dataContratacao;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public List<UnidadeDeTrabalho> getUnidadesDeTrabalho() {
		return unidadesDeTrabalho;
	}

	@Override
	public String toString() {
		return "- ID do Funcionario: " + this.id + " || Nome: " + this.nome
				+ " || CPF: " + this.cpf + " || Salario: R$" + this.salario
				+ " || Contratado em: " + this.dataContratacao
				+ " || Cargo: " + this.cargo + " || Unidades de trabalho: " + this.unidadesDeTrabalho;
	}

}
