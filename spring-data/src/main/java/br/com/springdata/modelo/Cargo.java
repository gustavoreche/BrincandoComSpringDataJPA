package br.com.springdata.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cargo")
public class Cargo {
	
	public Cargo() {
	}
	
	public Cargo(Integer idCargo, String nomeCargo) {
		this.id = idCargo;
		this.descricao = nomeCargo;
	}
	
	public Cargo(String descricao) {
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descricao;

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return "- ID do Cargo: " + this.id + ", Cargo: " + this.descricao;
	}

}
