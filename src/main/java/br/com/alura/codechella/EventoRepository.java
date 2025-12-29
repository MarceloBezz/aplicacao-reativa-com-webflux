package br.com.alura.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends ReactiveCrudRepository<Evento, Long>{
    
}
