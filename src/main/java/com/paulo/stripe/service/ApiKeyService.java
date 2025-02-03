package com.paulo.stripe.service;

import com.paulo.stripe.domain.ApikeyEntity;
import com.paulo.stripe.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
public class ApiKeyService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String generateApiKey(String customerEmail) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String key = base64Encoder.encodeToString(randomBytes);

        ApikeyEntity apikeyEntity = ApikeyEntity.builder()
                .email(customerEmail)
                .isEnabled(true)
                .key(key)
                .dateOfExpiry(Instant.now().plus(365L, ChronoUnit.DAYS))
                .build();

        apiKeyRepository.save(apikeyEntity);

        return key;
    }

    public boolean checkApiKeyIsEnabled(UUID id) {
        return apiKeyRepository.existsByIdAndIsEnabledTrue(id);
    }

    public void disableApiKey(String email) {
        ApikeyEntity apikeyEntity = apiKeyRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());

        apikeyEntity.setEnabled(false);

        apiKeyRepository.save(apikeyEntity);
    }

}
