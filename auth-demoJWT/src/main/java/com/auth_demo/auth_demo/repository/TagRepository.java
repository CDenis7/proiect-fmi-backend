package com.auth_demo.auth_demo.repository;

import com.auth_demo.auth_demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}