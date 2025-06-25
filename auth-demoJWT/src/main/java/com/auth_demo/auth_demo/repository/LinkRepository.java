package com.auth_demo.auth_demo.repository;

import com.auth_demo.auth_demo.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
