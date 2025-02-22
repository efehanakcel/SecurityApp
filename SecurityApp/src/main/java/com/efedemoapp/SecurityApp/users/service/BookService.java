package com.efedemoapp.SecurityApp.users.service;

import com.efedemoapp.SecurityApp.users.model.Books;
import com.efedemoapp.SecurityApp.users.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Books> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    public Optional<Books> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Books saveBook(Books books) {
        return bookRepository.save(books);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


}
