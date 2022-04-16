package io.github.mat3e.task;

import static io.github.mat3e.task.TaskDto.builder;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.PersistenceConstructor;

import io.github.mat3e.project.query.SimpleProjectQueryDto;

@Entity
@Table(name = "tasks")
class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private SimpleProjectQueryDto project;

    @PersistenceConstructor
    public Task() {
    }

    Task(@NotNull String description, ZonedDateTime deadline, SimpleProjectQueryDto project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    TaskDto convertToDto() {
        return builder().id(this.id)
                .description(this.description)
                .done(this.done)
                .deadline(this.deadline)
                .additionalComment(this.additionalComment)
                .build();
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    int getChangesCount() {
        return changesCount;
    }

    void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }

    String getAdditionalComment() {
        return additionalComment;
    }

    void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    SimpleProjectQueryDto getProject() {
        return project;
    }

    void setProject(SimpleProjectQueryDto project) {
        this.project = project;
    }
}
