package com.tourmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppUserResponse {

    private Long id;
    private String email;
    private LocalDateTime creationDate;
}