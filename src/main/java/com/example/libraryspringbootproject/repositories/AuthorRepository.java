package com.example.libraryspringbootproject.repositories;

import com.example.libraryspringbootproject.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
