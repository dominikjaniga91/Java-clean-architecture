package io.github.mat3e.task;

import java.time.ZonedDateTime;

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

    private TaskDto(final Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.done = builder.done;
        this.deadline = builder.deadline;
        this.additionalComment = builder.additionalComment;
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
            return new TaskDto(this);
        }
    }
}
