package com.auth_demo.auth_demo.repository;

import com.auth_demo.auth_demo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
