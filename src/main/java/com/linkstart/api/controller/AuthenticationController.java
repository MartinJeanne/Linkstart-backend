package com.linkstart.api.controller;

import com.linkstart.api.model.dto.AuthResponseDto;
import com.linkstart.api.model.dto.ClientDto;
import com.linkstart.api.model.dto.RegisterDto;
import com.linkstart.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authenticationService.register(registerDto));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(authenticationService.login(clientDto));
    }
}
