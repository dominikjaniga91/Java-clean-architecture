package io.github.mat3e.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import io.github.mat3e.project.dto.ProjectDto;

public interface ProjectQueryRepository extends Repository<Project, Integer> {

    List<ProjectDto> findAllBy();

    long count();

    Optional<ProjectDto> findById(Integer id);
}
