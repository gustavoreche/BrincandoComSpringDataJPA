package br.com.springdata.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.springdata.modelo.UnidadeDeTrabalho;

@Repository
public interface UnidadeDeTrabalhoRepository extends CrudRepository<UnidadeDeTrabalho, Integer> {
	
	@Query("SELECT udt FROM UnidadeDeTrabalho udt WHERE udt.descricao LIKE %:letras%")
	List<UnidadeDeTrabalho> procuraUnidadeDeTrabalhoPorNomeSeContemLetras(@Param("letras") String letras);
	
	@Query(value = "SELECT * FROM tb_unidade_de_trabalho WHERE endereco LIKE %:letras%", 
			nativeQuery = true)
	List<UnidadeDeTrabalho> procuraUnidadeDeTrabalhoPorEndercoSeContemLetras(@Param("letras") String letras);

}
