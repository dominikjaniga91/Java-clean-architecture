package io.github.mat3e.task;

import org.springframework.stereotype.Service;

import io.github.mat3e.project.Project;

/**
 * @author Dominik_Janiga
 */
@Service
class TaskFactory {

    Task from(TaskDto taskDto, Project project) {
        Task task = new Task(taskDto.getDescription(), taskDto.getDeadline(), project);
        task.setId(taskDto.getId());
        task.setDone(taskDto.isDone());
        task.setAdditionalComment(taskDto.getAdditionalComment());
        return task;
    }
}
