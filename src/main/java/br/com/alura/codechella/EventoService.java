package br.com.alura.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Flux<EventoDTO> obterTodos() {
        return repository
                .findAll()
                .map(EventoDTO::toDto);
    }

    public Mono<EventoDTO> obterPorId(Long id) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDTO::toDto);
    }

    public Mono<EventoDTO> cadastrar(EventoDTO dto) {
        return repository
                .save(dto.toEntity())
                .map(EventoDTO::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repository.findById(id).flatMap(repository::delete);
    }

    public Mono<EventoDTO> atualizar(Long id, EventoDTO dto) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado!")))
                .flatMap(evento -> {
                    evento.setData(dto.data());
                    evento.setDescricao(dto.descricao());
                    evento.setNome(dto.nome());
                    evento.setTipo(dto.tipo());
                    return repository.save(evento);
                })
                .map(EventoDTO::toDto);
    }

    public Flux<EventoDTO> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return repository
                .findByTipo(tipoEvento)
                .map(EventoDTO::toDto);
    }

}
