package br.com.springdata.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.springdata.modelo.Funcionario;
import br.com.springdata.modelo.FuncionarioProjection;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>,
	JpaSpecificationExecutor<Funcionario> {
	
	@Query(value = "SELECT id, nome, salario FROM tb_funcionario", 
			nativeQuery = true)
	List<FuncionarioProjection> exibeSomenteIdNomeESalario();

}
