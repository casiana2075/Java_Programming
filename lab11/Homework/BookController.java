package com.example.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookDAO bookDAO;

    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookDAO.findAll();
            return ResponseEntity.ok(books);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            bookDAO.create(book);
            return ResponseEntity.ok(book);
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
//    json body
//    {
//    "title": "Book Title",
//    "authors": ["Author1", "Author2"],
//    "lang": "English",
//    "genres": ["Genre1", "Genre2"],
//    "publication_date": "2022-01-01",
//    "number_of_pages": 200,
//    "publisher_id": 3,
//    "year": 2001
//}

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBookTitle(@PathVariable int id, @RequestBody Map<String, String> body) {
        String newTitle = body.get("title");
        try {
            bookDAO.updateTitle(id, newTitle);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        try {
            Book book = bookDAO.findById(id);
            if (book != null) {
                bookDAO.delete(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}