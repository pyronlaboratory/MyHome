package com.myhome.repositories;

import com.myhome.domain.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * extends JpaRepository and provides a standard way to interact with SecurityToken
 * entities in a Spring Data JPA environment.
 */
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, Long> {
}
