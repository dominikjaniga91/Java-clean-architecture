package io.github.mat3e.project.dto;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import io.github.mat3e.task.dto.TaskDto.Builder;


/**
 * @author Dominik_Janiga
 */
@JsonDeserialize(builder = Builder.class)
public class ProjectDto {

    private final int id;
    private final String name;
    private Set<ProjectStepDto> steps;

    private ProjectDto(final int id, final String name, final Set<ProjectStepDto> steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .name(this.name)
                .steps(this.steps);
    }
    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Set<ProjectStepDto> getSteps() {
        return steps;
    }

    static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    static class Builder {

        private int id;
        private String name;
        private Set<ProjectStepDto> steps;

        Builder id(int id) {
            this.id = id;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder steps(Set<ProjectStepDto> steps) {
            this.steps = steps;
            return this;
        }

        ProjectDto build() {
            return new ProjectDto(this.id, this.name, this.steps);
        }
    }
}
