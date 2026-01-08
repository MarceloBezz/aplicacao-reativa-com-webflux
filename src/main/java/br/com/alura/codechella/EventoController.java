package br.com.alura.codechella;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService service;
    
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDTO> obterTodos() {
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public Mono<EventoDTO> obterPorId(@PathVariable Long id) {
        return service.obterPorId(id);
    }
    
}
