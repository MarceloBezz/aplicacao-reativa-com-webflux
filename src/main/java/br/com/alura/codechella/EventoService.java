package br.com.alura.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Flux<EventoDTO> obterTodos() {
        return repository
                .findAll()
                .map(EventoDTO::toDto);
    }

}
