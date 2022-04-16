package io.github.mat3e.project;

import java.util.Optional;

import org.springframework.data.repository.Repository;

interface ProjectRepository extends Repository<Project, Integer> {

    Project save(Project project);

    Optional<Project> findById(int id);
}
