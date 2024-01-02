package com.example.onlineshop.user.repository;

import com.example.onlineshop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
