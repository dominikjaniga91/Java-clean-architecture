package io.github.mat3e.task;

import org.springframework.stereotype.Service;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import io.github.mat3e.task.dto.TaskDto;

/**
 * @author Dominik_Janiga
 */
@Service
class TaskFactory {

    Task from(TaskDto taskDto, SimpleProjectQueryDto project) {
        Task task = new Task(taskDto.getDescription(), taskDto.getDeadline(), project);
        task.setId(taskDto.getId());
        task.setDone(taskDto.isDone());
        task.setAdditionalComment(taskDto.getAdditionalComment());
        return task;
    }
}
