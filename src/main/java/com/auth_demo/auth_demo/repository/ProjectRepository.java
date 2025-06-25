package com.auth_demo.auth_demo.repository;

import com.auth_demo.auth_demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
