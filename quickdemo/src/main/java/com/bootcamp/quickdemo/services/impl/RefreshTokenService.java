package com.bootcamp.quickdemo.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.quickdemo.dao.RefreshTokenDao;
import com.bootcamp.quickdemo.dao.UserDao;
import com.bootcamp.quickdemo.model.RefreshToken;
import com.bootcamp.quickdemo.model.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenDao refreshTokenRepository;
    private final UserDao userDao;

    @Value("${app.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(String username) {
        Users user = userDao.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return createRefreshToken(user);
    }

    public RefreshToken createRefreshToken(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    @Transactional
    public void deleteByUser(Users user) {
        refreshTokenRepository.deleteByUser(user);
    }
}