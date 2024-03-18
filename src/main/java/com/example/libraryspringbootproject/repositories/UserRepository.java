package com.example.libraryspringbootproject.repositories;

import com.example.libraryspringbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
