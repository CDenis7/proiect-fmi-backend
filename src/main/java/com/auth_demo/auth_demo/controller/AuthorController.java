package com.auth_demo.auth_demo.controller;

import com.auth_demo.auth_demo.entity.Author;
import com.auth_demo.auth_demo.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController( AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
    @GetMapping("/{authorId}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable("authorId") Long authorId) {
        Optional<Author> authors = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(authors);
    }

    @PostMapping("")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author saved = authorService.createAuthor(author);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author updatedAuthor) {
        try {
            return ResponseEntity.ok(authorService.updateAuthor(authorId, updatedAuthor));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return ResponseEntity.ok("Автор успешно удалён");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

