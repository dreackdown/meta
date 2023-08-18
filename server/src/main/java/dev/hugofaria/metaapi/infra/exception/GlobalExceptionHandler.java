package dev.hugofaria.metaapi.infra.exception;

import dev.hugofaria.metaapi.controller.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentials() {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Credenciais inválidas", "401");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException() {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Falha na autenticação", "401");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDenied() {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Acesso negado", "403");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementException() {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Vendedor não encontrado", "400");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);
    }
}