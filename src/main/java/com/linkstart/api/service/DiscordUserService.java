package com.linkstart.api.service;

import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.repo.DiscordUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DiscordUserService {
    private final DiscordUserRepo discordUserRepo;
    private final PlaylistService playlistService;
    private final ModelMapper modelMapper;

    public DiscordUserService(
            DiscordUserRepo discordUserRepo,
            PlaylistService playlistService,
            ModelMapper modelMapper) {
        this.discordUserRepo = discordUserRepo;
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
    }

    public List<DiscordUserDto> getDiscordUsers() {
        List<DiscordUser> discordUsers = discordUserRepo.findAll();
        return discordUsers
                .stream()
                .map(user -> modelMapper.map(user, DiscordUserDto.class))
                .toList();
    }

    public List<DiscordUserDto> getDiscordUsersBirthdayIsTomorrow() {
        List<DiscordUserDto> discordUsers = getDiscordUsers();
        LocalDate now = LocalDate.now();
        int nowMonth = now.getMonthValue() + 1; // +1 because we check for tomorrow
        int tomorrowDay = now.getDayOfMonth();

        List<DiscordUserDto> discordUsersBirthdayIsNow = new ArrayList<>();
        for (DiscordUserDto user : discordUsers) {
            if (user.getBirthday() == null) continue;

            int birthdayMonth = user.getBirthday().getMonthValue();
            int birthdayDay = user.getBirthday().getDayOfMonth();

            if (birthdayMonth == nowMonth && birthdayDay == tomorrowDay) {
                discordUsersBirthdayIsNow.add(user);
            }
        }
        return discordUsersBirthdayIsNow;
    }

    public DiscordUserDto getDiscordUserByDiscordId(String id) {
        DiscordUser discordUser = discordUserRepo.findByDiscordId(id).orElseThrow(NoContentException::new);
        return modelMapper.map(discordUser, DiscordUserDto.class);
    }

    public DiscordUserDto createDiscordUser(DiscordUserDto discordUserDto) {
        DiscordUser discordUser = modelMapper.map(discordUserDto, DiscordUser.class);

        discordUserRepo.save(discordUser);
        return modelMapper.map(discordUser, DiscordUserDto.class);
    }

    public DiscordUserDto updateDiscordUser(Long id, DiscordUserDto discordUserDto) {
        discordUserRepo.findById(id).orElseThrow(NoContentException::new);
        discordUserDto.setId(id);
        DiscordUser updatedDiscordUser = discordUserRepo.save(modelMapper.map(discordUserDto, DiscordUser.class));
        //updatedDiscordUser.setBirthday(LocalDate.of(2023, 11, 23));
        return modelMapper.map(updatedDiscordUser, DiscordUserDto.class);
    }

    public ResponseEntity<HttpStatus> deleteDiscordUser(Long id) {
        DiscordUser discordUser = discordUserRepo.findById(id).orElseThrow(NoContentException::new);
        discordUserRepo.delete(discordUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<PlaylistDto> getPlaylistsByDiscordUser(String id) {
        DiscordUserDto discordUserDto = this.getDiscordUserByDiscordId(id);
        DiscordUser discordUser = modelMapper.map(discordUserDto, DiscordUser.class);
        return playlistService.getPlaylistsByDiscordUser(discordUser);
    }
}
