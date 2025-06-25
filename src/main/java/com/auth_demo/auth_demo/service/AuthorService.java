package com.auth_demo.auth_demo.service;


import com.auth_demo.auth_demo.entity.Author;
import com.auth_demo.auth_demo.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(Author author) {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele autorului nu poate fi gol.");
        }
        // Verificăm dacă există deja (folosind metoda din AuthorRepository)
        return authorRepository.findByNameIgnoreCase(author.getName().trim())
                .orElseGet(() -> {
                    // Dacă nu există, îl salvăm
                    return authorRepository.save(author);
                });
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        existingAuthor.setName(updatedAuthor.getName());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Автор не найден");
        }
        authorRepository.deleteById(id);
    }
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}