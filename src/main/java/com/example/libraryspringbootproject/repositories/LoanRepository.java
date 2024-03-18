package com.example.libraryspringbootproject.repositories;

import com.example.libraryspringbootproject.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
