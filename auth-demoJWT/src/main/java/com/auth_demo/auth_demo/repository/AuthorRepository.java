package com.auth_demo.auth_demo.repository;

import com.auth_demo.auth_demo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameIgnoreCase(String trim);

}

