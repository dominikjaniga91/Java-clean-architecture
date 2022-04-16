package io.github.mat3e.task.dto;

import java.time.ZonedDateTime;

/**
 * Spring projection interface.
 *
 * @author Dominik_Janiga
 */
public interface TaskWithChangesDto {

    int getId();

    String getDescription();

    boolean isDone();

    ZonedDateTime getDeadline();

    int getChangesCount();
}
