package com.literalura.service;

import com.literalura.entity.Author;
import com.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public Author saveAuthor(Author author) {
        Author authorSave = authorRepository.save(author);

        return authorSave;
    }
}
