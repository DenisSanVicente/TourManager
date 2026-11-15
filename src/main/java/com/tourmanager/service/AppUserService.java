package com.tourmanager.service;

import com.tourmanager.dto.AppUserRequest;
import com.tourmanager.dto.AppUserResponse;
import com.tourmanager.entity.AppUser;
import com.tourmanager.exception.AppUserNotFoundException;
import com.tourmanager.exception.EmailAlreadyExistsException;
import com.tourmanager.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUserResponse> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();

        return users.stream()
                .map(user -> toResponse(user))
                .toList();
    }

    public AppUserResponse getUserById(Long id) {
        AppUser user =  appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(
                        "L'utilisateur " + id + " n'existe pas")
                );

        return toResponse(user);
    }


    private AppUserResponse toResponse(AppUser user) {
        return new AppUserResponse(
                user.getId(),
                user.getEmail(),
                user.getCreationDate()
        );
    }

    public AppUserResponse createUser(AppUserRequest request) {

        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Cette adresse email est déjà utilisée"
            );
        }

        // 1. Hacher le mot de passe
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Créer l'entité AppUser
        AppUser user = new AppUser(request.getEmail(), hashedPassword);

        // 3. Enregistrer l'entité dans PostgreSQL
        AppUser savedUser = appUserRepository.save(user);

        // 4. Convertir l'entité enregistrée en DTO
        return toResponse(savedUser);
    }




}
