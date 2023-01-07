package com.linkstart.api.mapper;

import com.linkstart.api.controller.DiscordUserController;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.dto.DiscordUserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DiscordUserModelAssembler extends RepresentationModelAssemblerSupport<DiscordUser, DiscordUserDto> {

    public DiscordUserModelAssembler() {
        super(DiscordUserController.class, DiscordUserDto.class);
    }

    // to model (Dto)
    @Override
    public DiscordUserDto toModel(DiscordUser discordUser) {
        DiscordUserDto discordUserDto = new DiscordUserDto();
        discordUserDto.setId(discordUser.getId());
        discordUserDto.setDiscordId(discordUser.getDiscordId());
        discordUserDto.setTag(discordUser.getTag());

        discordUserDto.add(linkTo(methodOn(DiscordUserController.class).getDiscordUserByIdPlaylists(discordUserDto.getId()))
                .withRel("discordUser playlist"));
        discordUserDto.add(linkTo(DiscordUserController.class).withRel("discordUsers"));
        discordUserDto.add(linkTo(methodOn(DiscordUserController.class).getDiscordUserById(discordUserDto.getId())).withSelfRel());

        return discordUserDto;
    }

    //to model (Dto) as list
    @Override
    public CollectionModel<DiscordUserDto> toCollectionModel(Iterable<? extends DiscordUser> discordUsers) {
        List<DiscordUserDto> membersList = new ArrayList<>();

        for (DiscordUser discordUser: discordUsers) {
            DiscordUserDto discordUserDto = new DiscordUserDto();
            discordUserDto.setId(discordUser.getId());
            discordUserDto.setDiscordId(discordUser.getDiscordId());
            discordUserDto.setTag(discordUser.getTag());

            discordUserDto.add(linkTo(methodOn(DiscordUserController.class).getDiscordUserByIdPlaylists(discordUserDto.getId()))
                    .withRel("discordUser playlists"));
            discordUserDto.add(linkTo(methodOn(DiscordUserController.class).getDiscordUserById(discordUserDto.getId())).withSelfRel());
            membersList.add(discordUserDto);
        }

        CollectionModel<DiscordUserDto> membersDto = CollectionModel.of(membersList);
        membersDto.add(linkTo(DiscordUserController.class).withSelfRel());
        return membersDto;
    }

    // to entity
    public DiscordUser toEntity(DiscordUserDto discordUserDto) {
        if (discordUserDto == null) return null;

        DiscordUser discordUser = new DiscordUser();
        discordUser.setId(discordUserDto.getId());
        discordUser.setDiscordId(discordUserDto.getDiscordId());
        discordUser.setTag(discordUserDto.getTag());
        return  discordUser;
    }
}
