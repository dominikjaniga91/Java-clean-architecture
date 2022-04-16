package io.github.mat3e.task.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = TaskDto.Builder.class)
public class TaskDto {
    private int id;
    @NotNull
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    public TaskDto(final int id, @NotNull final String description, final boolean done, final ZonedDateTime deadline,
            final String additionalComment) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .description(this.description)
                .done(this.done)
                .deadline(this.deadline)
                .additionalComment(this.additionalComment);
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskDto taskDto = (TaskDto) o;
        return id == taskDto.id && done == taskDto.done && Objects.equals(description, taskDto.description)
                && Objects.equals(deadline, taskDto.deadline) && Objects
                .equals(additionalComment, taskDto.additionalComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done, deadline, additionalComment);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private int id;
        @NotNull
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        private Builder() {}

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder done(boolean done) {
            this.done = done;
            return this;
        }

        public Builder deadline(ZonedDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder additionalComment(String additionalComment) {
            this.additionalComment = additionalComment;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this.id, this.description, this.done, this.deadline, this.additionalComment);
        }
    }
}
