package com.clickedin.features.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clickedin.features.authentication.model.AuthenticationUser;
import java.util.List;


@Repository
public interface AuthenticationUserRepository extends JpaRepository<AuthenticationUser,Long>{
    Optional<AuthenticationUser> findByEmail(String email);
}
