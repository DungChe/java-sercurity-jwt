package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {
    Optional<BlacklistToken> findByToken(String token);
    boolean existsByToken(String token);
}
