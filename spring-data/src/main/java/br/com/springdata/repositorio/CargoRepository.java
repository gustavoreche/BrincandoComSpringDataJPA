package br.com.springdata.repositorio;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.springdata.modelo.Cargo;

@Repository
public interface CargoRepository extends PagingAndSortingRepository<Cargo, Integer> {
	
	List<Cargo> findByDescricaoContaining(String letras);

}
