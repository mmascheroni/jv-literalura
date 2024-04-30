package com.literalura.principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.entity.Author;
import com.literalura.entity.Book;
import com.literalura.entity.DataAuthor;
import com.literalura.entity.DataBook;
import com.literalura.service.AuthorService;
import com.literalura.service.BookService;
import com.literalura.utils.APIConsume;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProcessBook {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @PostConstruct
    public void processBook() throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        APIConsume apiConsume = new APIConsume();

        String url = "https://gutendex.com/books/84/";
        String jsonRes = apiConsume.getRequest(url);
        System.out.println(jsonRes);


        DataBook dataBook = objectMapper.readValue(jsonRes, DataBook.class);
        Book book = objectMapper.convertValue(dataBook, Book.class);

        for (Author author : book.getAuthors()) {
            Author authorSave = authorService.saveAuthor(author);
        }


        Book bookSave = bookService.saveBook(book);

        System.out.println("Book to save: " + bookSave);

    }
}
