package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.ServerDto;
import com.linkstart.api.model.entity.*;
import com.linkstart.api.repo.ServerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {

    private final ServerRepo serverRepo;
    private final ModelMapper modelMapper;

    public ServerService(ServerRepo serverRepo, ModelMapper modelMapper) {
        this.serverRepo = serverRepo;
        this.modelMapper = modelMapper;
    }

    public List<ServerDto> getServers() {
        List<Server> servers = serverRepo.findAll();
        return servers
                .stream()
                .map(server -> modelMapper.map(server, ServerDto.class))
                .toList();
    }

    public ServerDto getServerById(Long id) {
        Optional<Server> discordServer = serverRepo.findById(id);
        if(discordServer.isEmpty())
            throw new NotFoundException(id + " Server");

        return modelMapper.map(discordServer.get(), ServerDto.class);
    }

    public ServerDto createServer(ServerDto serverDto) {
        Server server = modelMapper.map(serverDto, Server.class);
        Server savedServer = serverRepo.save(server);
        return modelMapper.map(savedServer, ServerDto.class);
    }
}
