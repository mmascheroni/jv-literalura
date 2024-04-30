package com.literalura.principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.literalura.entity.Author;
import com.literalura.entity.Book;
import com.literalura.entity.DataBook;
import com.literalura.service.AuthorService;
import com.literalura.service.BookService;
import com.literalura.utils.APIConsume;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessBook {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;


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

    @PostConstruct
    public void processBookAllBooks() throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        APIConsume apiConsume = new APIConsume();

        String url = "https://gutendex.com/books/";
        String jsonRes = apiConsume.getRequest(url);

        List<DataBook> dataBooks = new ArrayList<>();

        JsonElement jsonElement = JsonParser.parseString(jsonRes);
        JsonObject jsonObject = jsonElement.getAsJsonObject();


        List<JsonElement> results = new ArrayList<>();

        if (jsonObject.has("results")) {
            JsonElement resultsElement = jsonObject.get("results");
            if (resultsElement.isJsonArray()) {
                for (JsonElement resultElement : resultsElement.getAsJsonArray()) {
                    results.add(resultElement);
                }
            }
        }

        for (JsonElement element : results) {
            try {
                DataBook dataBook = objectMapper.readValue(element.toString(), DataBook.class);
                dataBooks.add(dataBook);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for ( DataBook dataBook : dataBooks ) {
            Book book = objectMapper.convertValue(dataBook, Book.class);

            for (Author author : book.getAuthors()) {
                Author authorSave = authorService.saveAuthor(author);
            }

            Book bookSave = bookService.saveBook(book);
        }

    }
}
