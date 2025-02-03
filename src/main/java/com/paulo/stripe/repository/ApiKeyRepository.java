package com.paulo.stripe.repository;

import com.paulo.stripe.domain.ApikeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApikeyEntity, UUID> {

    boolean existsByIdAndIsEnabledTrue(UUID id);

    Optional<ApikeyEntity> findByEmail(String email);
}
