package com.linkstart.api.service;

import com.linkstart.api.configuration.JwtService;
import com.linkstart.api.exception.AuthenticationException;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.AuthResponseDto;
import com.linkstart.api.model.dto.ClientDto;
import com.linkstart.api.model.dto.RegisterDto;
import com.linkstart.api.model.entity.Client;
import com.linkstart.api.model.entity.Role;
import com.linkstart.api.repo.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepo clientRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.registerPassword}")
    private String registerPassword;

    public AuthResponseDto register(RegisterDto registerDto) {
        if (!Objects.equals(registerDto.getRegisterPassword(), registerPassword))
            throw new AuthenticationException("Wrong register password");
        if (clientRepo.existsByClientName(registerDto.getClientName()))
            throw new AuthenticationException("Username is already taken");

        Client client = Client.builder()
                .clientName(registerDto.getClientName())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();

        Client savedClient = clientRepo.save(client);

        this.authenticate(registerDto.getClientName(), registerDto.getPassword());
        String token = jwtService.generateToken(savedClient);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(ClientDto clientDto) {
        this.authenticate(clientDto.getClientName(), clientDto.getPassword());

        Client client = clientRepo.findByClientName(clientDto.getClientName())
                .orElseThrow(() -> new NotFoundException(clientDto.getClientName(), Client.class));

        String token = jwtService.generateToken(client);
        return new AuthResponseDto(token);
    }

    private void authenticate(String user, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, password)
            );

        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
}
