package br.com.alura.codechella;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void cadastraNovoEvento() {
		EventoDTO dto = new EventoDTO(null, TipoEvento.SHOW, "Kiss", LocalDate.parse("2026-01-01"),
				"Show top de uma banda top");

		webTestClient.post()
				.uri("/eventos")
				.bodyValue(dto)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(EventoDTO.class).value(response -> {
					assertNotNull(response.id());
					assertEquals(dto.tipo(), response.tipo());
					assertEquals(dto.nome(), response.nome());
					assertEquals(dto.data(), response.data());
					assertEquals(dto.descricao(), response.descricao());
				});
	}

	@Test
	void buscaEventos() {
		EventoDTO dto = new EventoDTO(13L, TipoEvento.SHOW, "The Weeknd", LocalDate.parse("2025-11-02"),
				"Um show eletrizante ao ar livre com muitos efeitos especiais.");

		webTestClient.get()
				.uri("/eventos")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(EventoDTO.class).value(response -> {
					EventoDTO eventoResponse = response.get(12);
					assertEquals(dto.id(), eventoResponse.id());
					assertEquals(dto.tipo(), eventoResponse.tipo());
					assertEquals(dto.nome(), eventoResponse.nome());
					assertEquals(dto.data(), eventoResponse.data());
					assertEquals(dto.descricao(), eventoResponse.descricao());
				});
	}

	@Test
	void excluirEvento() {
		EventoDTO dto = new EventoDTO(30L, TipoEvento.SHOW, "The Weeknd", LocalDate.parse("2025-11-02"),
				"Um show eletrizante ao ar livre com muitos efeitos especiais.");

		webTestClient.post()
				.uri("/eventos")
				.bodyValue(dto)
				.exchange();

		webTestClient.delete()
				.uri("/eventos/" + dto.id())
				.exchange()
				.expectStatus().isOk();

		webTestClient.get()
				.uri("/eventos/" + dto.id())
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void atualizaEvento() {
		EventoDTO dto = new EventoDTO(1L, TipoEvento.SHOW, "Novo Show!!!", LocalDate.parse("2025-11-02"),
				"Surge um novo show");

		webTestClient.put()
				.uri("/eventos/" + dto.id())
				.bodyValue(dto)
				.exchange()
				.expectStatus().isOk()
				.expectBody(EventoDTO.class).value(response -> {
					assertEquals(dto.id(), response.id());
					assertEquals(dto.tipo(), response.tipo());
					assertEquals(dto.nome(), response.nome());
					assertEquals(dto.data(), response.data());
					assertEquals(dto.descricao(), response.descricao());
				});
	}

}
