package br.com.springdata.modelo;

import java.math.BigDecimal;

public interface FuncionarioProjection {
	
	Integer getId();
	String getNome();
	BigDecimal getSalario();

}
