package br.com.springdata.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.springdata.modelo.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
	
	List<Cargo> findByDescricaoContaining(String letras);

}
