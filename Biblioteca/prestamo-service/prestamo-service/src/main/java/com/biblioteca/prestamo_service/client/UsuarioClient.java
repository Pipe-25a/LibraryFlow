package com.biblioteca.prestamo_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP para validar la existencia de un usuario en el usuario-service.
 */
@Component
@Slf4j
public class UsuarioClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuario-service.url}")
    private String usuarioServiceUrl;

    /**
     * Valida que el usuario con el ID proporcionado exista en el usuario-service.
     *
     * @param id Long - ID del usuario a validar
     * @throws RuntimeException si el usuario no existe o hay error de comunicación
     */
    public void validarUsuario(Long id) {
        log.info("Client: Validando existencia del usuario con ID: {}", id);
        try {
            restTemplate.getForObject(usuarioServiceUrl + "/usuarios/" + id, String.class);
            log.info("Client: Usuario con ID {} validado correctamente", id);
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Client: Usuario con ID {} no encontrado", id);
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        } catch (Exception e) {
            log.error("Client: Error al comunicarse con usuario-service para ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al comunicarse con usuario-service para ID: " + id);
        }
    }
}
