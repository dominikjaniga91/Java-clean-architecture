package io.github.mat3e.project;

import java.time.ZonedDateTime;

class ProjectDeadlineDto {
    private ZonedDateTime deadline;

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }
}
