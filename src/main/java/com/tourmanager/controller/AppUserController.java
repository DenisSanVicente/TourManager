package com.tourmanager.controller;

import com.tourmanager.dto.AppUserRequest;
import com.tourmanager.dto.AppUserResponse;
import com.tourmanager.entity.AppUser;
import com.tourmanager.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUserResponse> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUserResponse getUserById(@PathVariable Long id) {
        return appUserService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserResponse createUser(@Valid @RequestBody AppUserRequest request) {
        return appUserService.createUser(request);
    }



}
