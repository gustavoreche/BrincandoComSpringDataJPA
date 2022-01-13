package br.com.springdata.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_de_trabalho")
public class UnidadeDeTrabalho {
	
	public UnidadeDeTrabalho() {
	}
	
	public UnidadeDeTrabalho(Integer id, String descricao, String endereco) {
		this.id = id;
		this.descricao = descricao;
		this.endereco = endereco;
	}
	
	public UnidadeDeTrabalho(String descricao, String endereco) {
		this.descricao = descricao;
		this.endereco = endereco;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descricao;
	
	private String endereco;
	
	public Integer getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	@Override
	public String toString() {
		return "- ID da Unidade de trabalho: " + this.id + ", Nome: " + this.descricao
				+ ", Endereco: " + this.endereco;
	}

}
