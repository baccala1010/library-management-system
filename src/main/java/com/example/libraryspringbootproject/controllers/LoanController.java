package com.example.libraryspringbootproject.controllers;

import com.example.libraryspringbootproject.entities.Book;
import com.example.libraryspringbootproject.entities.Loan;
import com.example.libraryspringbootproject.repositories.BookRepository;
import com.example.libraryspringbootproject.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.findAll();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> createLoan(@RequestBody Loan loan) {
        Book book = bookRepository.findById(loan.getBook().getId()).orElse(null);
        if (book != null) {
            try {
                loanService.createLoan(book, loan.getUser());
                return new ResponseEntity<>("Loan has been created successfully", HttpStatus.CREATED);
            } catch (RuntimeException e) {
                return new ResponseEntity<>("Failed to create loan: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        loan.setId(id);
        return loanService.save(loan);
    }

    @PostMapping("/{bookId}/complete")
    public ResponseEntity<String> completeLoan(@PathVariable Long bookId) {
        loanService.completeLoan(bookId);
        return ResponseEntity.ok("Loan for book ID " + bookId + " completed successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksOwedByUser(@PathVariable Long userId) {
        List<Book> booksOwedByUser = loanService.findBooksOwedByUser(userId);
        return ResponseEntity.ok(booksOwedByUser);
    }
}
