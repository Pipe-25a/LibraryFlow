package com.biblioteca.prestamo_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP para validar la existencia de un libro en el libro-service.
 */
@Component
@Slf4j
public class LibroClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${libro-service.url}")
    private String libroServiceUrl;

    /**
     * Valida que el libro con el ID proporcionado exista en el libro-service.
     *
     * @param id Long - ID del libro a validar
     * @throws RuntimeException si el libro no existe o hay error de comunicación
     */
    public void validarLibro(Long id) {
        log.info("Client: Validando existencia del libro con ID: {}", id);
        try {
            restTemplate.getForObject(libroServiceUrl + "/libros/" + id, String.class);
            log.info("Client: Libro con ID {} validado correctamente", id);
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Client: Libro con ID {} no encontrado", id);
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        } catch (Exception e) {
            log.error("Client: Error al comunicarse con libro-service para ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al comunicarse con libro-service para ID: " + id);
        }
    }
}
