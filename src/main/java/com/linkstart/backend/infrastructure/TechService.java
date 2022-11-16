package com.linkstart.backend.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class TechService implements HelloInterface {

    @Override
    public String helloInfra() {
        return "Hello from infrastructure";
    }
}
