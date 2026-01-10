package br.com.alura.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface EventoRepository extends ReactiveCrudRepository<Evento, Long>{
    Flux<Evento> findByTipo(TipoEvento tipo);
}
