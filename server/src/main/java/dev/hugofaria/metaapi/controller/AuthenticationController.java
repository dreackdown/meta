package dev.hugofaria.metaapi.controller;

import dev.hugofaria.metaapi.controller.request.AuthenticationRequestDTO;
import dev.hugofaria.metaapi.controller.request.RegisterRequestDTO;
import dev.hugofaria.metaapi.controller.response.LoginResponseDTO;
import dev.hugofaria.metaapi.domain.model.User;
import dev.hugofaria.metaapi.domain.repository.UserRepository;
import dev.hugofaria.metaapi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request) {
        if (this.userRepository.findByLogin(request.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.login(), encryptedPassword, request.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}