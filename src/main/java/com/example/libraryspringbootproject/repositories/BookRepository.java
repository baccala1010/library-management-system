package com.example.libraryspringbootproject.repositories;

import com.example.libraryspringbootproject.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
