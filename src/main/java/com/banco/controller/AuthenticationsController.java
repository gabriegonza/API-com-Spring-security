package com.banco.controller;

import com.banco.domain.user.AuthenticationDTO;
import com.banco.domain.user.LoginResponseDTO;
import com.banco.domain.user.RegisterDTO;
import com.banco.domain.user.Users;
import com.banco.repository.UserRepository;
import com.banco.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/auth")
public class AuthenticationsController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;
    @Operation(summary = "Fazer login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faz o login com usuario valido e retorna um token"),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca com login ou senha errada"),

    })
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @Operation(summary = "Criar um usuario", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cria um usuario"),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar criar o mesmo usuario"),
            @ApiResponse(responseCode = "403", description = "Erro ao tentar criar um usuario com role errada"),
    })
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users newUser = new Users(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}

