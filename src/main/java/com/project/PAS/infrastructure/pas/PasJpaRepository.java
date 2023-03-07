package com.project.PAS.infrastructure.pas;

import com.project.PAS.infrastructure.pas.entity.PasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasJpaRepository extends JpaRepository<PasEntity, Long> {
    Optional<PasEntity> findByAccountNumber(Long accountNumber);
}
