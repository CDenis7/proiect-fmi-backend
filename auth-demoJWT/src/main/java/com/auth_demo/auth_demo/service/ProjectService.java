package com.auth_demo.auth_demo.service;

import com.auth_demo.auth_demo.entity.*;
import com.auth_demo.auth_demo.repository.AuthorRepository;
import com.auth_demo.auth_demo.repository.PhotoRepository;
import com.auth_demo.auth_demo.repository.ProjectRepository;
import com.auth_demo.auth_demo.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PhotoRepository photoRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    public ProjectService(ProjectRepository projectRepository,
                          PhotoRepository photoRepository,
                          AuthorRepository authorRepository,
                          TagRepository tagRepository) {
        this.projectRepository = projectRepository;
        this.photoRepository = photoRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    public void savePhoto(Long projectId, String base64Image) throws IOException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Проект не найден"));
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        Photo photo = new Photo();
        photo.setProject(project);
        photo.setImage(imageBytes);

        photoRepository.save(photo);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Boolean existsProject(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Transactional
    public Project createProject(Project project) {
        List<Author> authors = project.getAuthors().stream()
                .map(a -> authorRepository.findById(a.getId()).orElseThrow())
                .collect(Collectors.toList());
        project.setAuthors(authors);

        if (project.getTags() != null) {
            List<Tag> tags = project.getTags().stream()
                    .map(tag -> tagRepository.findById(tag.getId()).orElseThrow())
                    .collect(Collectors.toList());
            project.setTags(tags);
        }

        if (project.getLinks() != null) {
            List<Link> newLinks = project.getLinks().stream().map(link -> {
                Link newLink = new Link();
                newLink.setUrl(link.getUrl());
                newLink.setProject(project);
                return newLink;
            }).collect(Collectors.toList());
            project.setLinks(newLinks);
        }

        if (project.getPhotos() != null) {
            for (Photo photo : project.getPhotos()) {
                photo.setProject(project);
            }
        }

        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long projectId,Project updatedProject) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Проект не найден"));

        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setVideoUrl(updatedProject.getVideoUrl());

        List<Author> authors = updatedProject.getAuthors().stream()
                .map(a -> authorRepository.findById(a.getId()).orElseThrow())
                .collect(Collectors.toList());
        existingProject.setAuthors(authors);

        existingProject.getLinks().clear();

        if (updatedProject.getLinks() != null) {
            for (Link link : updatedProject.getLinks()) {
                Link newLink = new Link();
                newLink.setUrl(link.getUrl());  // только поля без id
                newLink.setProject(existingProject);
                existingProject.getLinks().add(newLink);
            }
        }

        if (updatedProject.getTags() != null) {
            List<Tag> tags = updatedProject.getTags().stream()
                    .map(t -> tagRepository.findById(t.getId()).orElseThrow())
                    .collect(Collectors.toList());
            existingProject.setTags(tags);
        }

        if (updatedProject.getPhotos() != null && !updatedProject.getPhotos().isEmpty()) {
            existingProject.getPhotos().clear();
            for (Photo photo : updatedProject.getPhotos()) {
                photo.setProject(existingProject);
                existingProject.getPhotos().add(photo);
            }
        }

        return projectRepository.save(existingProject);
    }


    public void delete(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public Page<Project> getAllProjectsByPage(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
}