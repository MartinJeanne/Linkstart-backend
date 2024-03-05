package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MemberDto {
    private String id;
    private String tag;
    private List<String> guildsId;
    private String avatar;
    private LocalDate birthday;
}
