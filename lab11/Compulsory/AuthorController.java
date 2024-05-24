package com.example.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorDAO authorDAO;

    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        try {
            List<Author> authors = authorDAO.findAll();
            return ResponseEntity.ok(authors);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}