package com.efedemoapp.SecurityApp.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.efedemoapp.SecurityApp.users.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
