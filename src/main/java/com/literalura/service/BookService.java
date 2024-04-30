package com.literalura.service;

import com.literalura.entity.Book;
import com.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book saveBook(Book book) {
        Book bookToSave = bookRepository.save(book);

        return bookToSave;
    }
}
