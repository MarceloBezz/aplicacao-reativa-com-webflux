package br.com.alura.codechella;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository repository;
    
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Evento> obterTodos() {
        return repository.findAll();
    }
    
}
