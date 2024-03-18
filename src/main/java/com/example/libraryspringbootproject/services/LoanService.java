package com.example.libraryspringbootproject.services;

import com.example.libraryspringbootproject.entities.Book;
import com.example.libraryspringbootproject.entities.Loan;
import com.example.libraryspringbootproject.entities.User;
import com.example.libraryspringbootproject.repositories.BookRepository;
import com.example.libraryspringbootproject.repositories.LoanRepository;
import com.example.libraryspringbootproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    public void createLoan(Book book, User user) {
        List<Loan> loans = loanRepository.findAll();
        Long userId = user.getId();
        int counter = 0;
        for (Loan loan : loans) {
            if (loan.getUser().getId().equals(userId)) {
                counter++;
            }
        }
        if (counter < 5) {
            Loan loan = new Loan();
            loan.setBook(book);
            loan.setUser(user);
            loan.setLoanDate(LocalDate.now());
            loan.setReturnDate(LocalDate.now().plusDays(14)); //Return date 14 days after loan date
            loanRepository.save(loan);

            book.setAvailable(false);
            bookRepository.save(book);
        }
    }

    public void completeLoan(Long bookId) {
        List<Loan> loans = loanRepository.findAll();
        for (Loan loan : loans) {
            if (loan.getBook().getId().equals(bookId)) {
                Book book = loan.getBook();
                book.setAvailable(true);
                bookRepository.save(book);

                loanRepository.delete(loan);
                break;
            }
        }
    }

    public List<Book> findBooksOwedByUser(Long userId) {
        List<Book> booksOwedByUser = new ArrayList<>();
        List<Loan> loans = loanRepository.findAll();
        for (Loan loan : loans) {
            if (loan.getUser().getId().equals(userId)) {
                booksOwedByUser.add(loan.getBook());
            }
        }
        return booksOwedByUser;
    }
}
