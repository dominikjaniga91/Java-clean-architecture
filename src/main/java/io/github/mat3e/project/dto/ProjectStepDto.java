package io.github.mat3e.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Dominik_Janiga
 */
@JsonDeserialize(builder = ProjectStepDto.Builder.class)
public class ProjectStepDto {

    private final int id;
    private final String description;
    private final int daysToProjectDeadline;
    private final ProjectDto project;

    public ProjectStepDto(final int id, final String description, final int daysToProjectDeadline, final ProjectDto project) {
        this.id = id;
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
        this.project = project;
    }

    static class Builder {

        private int id;
        private String description;
        private int daysToProjectDeadline;
        private ProjectDto project;

        Builder id(int id) {
            this.id = id;
            return this;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder daysToProjectDeadline(int daysToProjectDeadline) {
            this.daysToProjectDeadline = daysToProjectDeadline;
            return this;
        }

        Builder project(ProjectDto project) {
            this.project = project;
            return this;
        }
    }
}
