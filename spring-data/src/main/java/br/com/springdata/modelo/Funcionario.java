package br.com.springdata.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario {
	
	public Funcionario() {
	}
	
	public Funcionario(Integer id, String nome, String cpf, BigDecimal salario, LocalDate dataContratacao,
			Cargo cargo) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
		this.cargo = cargo;
	}
	
	public Funcionario(String nome, String cpf, BigDecimal salario, LocalDate dataContratacao,
			Cargo cargo) {
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.dataContratacao = dataContratacao;
		this.cargo = cargo;
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

	@Override
	public String toString() {
		return "- ID do Funcionario: " + this.id + " || Nome: " + this.nome
				+ " || CPF: " + this.cpf + " || Salario: R$" + this.salario
				+ " || Contratado em: " + this.dataContratacao
				+ " || Cargo: " + this.cargo;
	}

}
