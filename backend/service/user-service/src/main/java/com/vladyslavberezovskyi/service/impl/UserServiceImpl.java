package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.InvitationEntity;
import com.vladyslavberezovskyi.dao.entity.UserEntity;
import com.vladyslavberezovskyi.dao.repository.InvitationRepository;
import com.vladyslavberezovskyi.dao.repository.UserRepository;
import com.vladyslavberezovskyi.error.ForbiddenException;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.error.UserAlreadyExistsException;
import com.vladyslavberezovskyi.mapper.UserMapper;
import com.vladyslavberezovskyi.model.User;
import com.vladyslavberezovskyi.security.TokenResponse;
import com.vladyslavberezovskyi.security.UserDetails;
import com.vladyslavberezovskyi.security.enums.Role;
import com.vladyslavberezovskyi.service.InvitationService;
import com.vladyslavberezovskyi.service.UserService;
import com.vladyslavberezovskyi.util.EmailSender;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${constants.secretKey}")
    private String secretKey;
    @Value("${constants.tokenExpirationTime}")
    private Long tokenExpirationTime;
    
    private final UserRepository repository;
    private final UserMapper mapper;
    private final InvitationService invitationService;
    private final InvitationRepository invitationRepository;
    private final PasswordEncoder encoder;
    private final EmailSender emailSender;

    @Override
    public User getUserById() {
        UUID id = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return mapper.entityToModel(userEntity);
    }

    @Override
    public User createUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserEntity newUser = mapper.modelToEntity(user);
        newUser.setRole(Role.USER);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        UserEntity savedUser = repository.saveAndFlush(newUser);

        invitationService.sendInvitation(savedUser);

        return mapper.entityToModel(savedUser);
    }

    @Override
    public TokenResponse auth(User user) {
        UserEntity userEntity = repository.findByEmail(user.getEmail())
                .orElseThrow(ForbiddenException::new);

        if (!userEntity.isEnabled()) {
            throw new ForbiddenException();
        }

        if (!encoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new ForbiddenException();
        }
        
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(generateJWT(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getRole(), userEntity.isEnabled()));
        tokenResponse.setExpiresIn(tokenExpirationTime);
        return tokenResponse;
    }

    @Override
    public User updateUser(User user) {
        UUID id = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        if (user.getEmail() == null) {
            user.setEmail(userEntity.getEmail());
        }

        if (!user.getEmail().equals(userEntity.getEmail())) {
            InvitationEntity invitationEntity = invitationRepository.findByEmail(userEntity.getEmail());
            invitationEntity.setEmail(user.getEmail());
            invitationEntity.setEmailConfirmed(false);
            invitationRepository.saveAndFlush(invitationEntity);

            emailSender.sendInvitation(user.getEmail(), invitationEntity.getId());
            userEntity.setEnabled(false);
        }

        mapper.update(userEntity, user);
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userEntity.setPassword(encoder.encode(user.getPassword()));
        }

        userEntity = repository.saveAndFlush(userEntity);

        return mapper.entityToModel(userEntity);
    }

    private String generateJWT(UUID id, String email, String password, Role role, boolean enabled) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpirationTime);

        return Jwts.builder()
                .claim("id", id.toString())
                .claim("email", email)
                .claim("password", password)
                .claim("role", role.toString())
                .claim("enabled", enabled)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
