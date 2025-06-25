package com.auth_demo.auth_demo.service;

import com.auth_demo.auth_demo.entity.Tag;
import com.auth_demo.auth_demo.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag updatedTag) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тег не найден"));

        existingTag.setName(updatedTag.getName());

        return tagRepository.save(existingTag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}