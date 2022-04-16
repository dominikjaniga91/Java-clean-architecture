package io.github.mat3e.task;

import static java.util.stream.Collectors.toList;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import io.github.mat3e.task.dto.TaskWithChangesDto;

@Service
public class TaskFacade {
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository,
            final TaskQueryRepository taskQueryRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }

    @PostConstruct
    void init() {
        if (this.taskQueryRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            this.taskRepository.save(task);
        }
    }

    public List<TaskDto> saveAll(final List<TaskDto> taskDtos, SimpleProjectQueryDto project) {

        List<Task> tasks = taskDtos.stream().map(taskDto -> taskFactory.from(taskDto, project)).collect(toList());
        List<Task> persistedTasks = taskRepository.saveAll(tasks);

        return persistedTasks.stream().map(Task::convertToDto).collect(toList());
    }

    public boolean areUndoneTasksWithProjectId(int projectId) {
        return this.taskQueryRepository.existsByDoneIsFalseAndProject_Id(projectId);
    }

    TaskDto save(TaskDto toSave) {

        Task foundTask = taskRepository.findById(toSave.getId())
                .map(existingTask -> {
                    if (existingTask.isDone() != toSave.isDone()) {
                        existingTask.setChangesCount(existingTask.getChangesCount() + 1);
                        existingTask.setDone(toSave.isDone());
                    }
                    existingTask.setAdditionalComment(toSave.getAdditionalComment());
                    existingTask.setDeadline(toSave.getDeadline());
                    existingTask.setDescription(toSave.getDescription());
                    return existingTask;
                }).orElseGet(() -> {
                    var result = new Task(toSave.getDescription(), toSave.getDeadline(), null);
                    result.setAdditionalComment(toSave.getAdditionalComment());
                    return result;
                });
        Task savedTask = taskRepository.save(foundTask);
        return savedTask.convertToDto();
    }

    List<TaskDto> list() {
        return taskQueryRepository.findAll().stream()
                .map(Task::convertToDto)
                .collect(toList());
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(Task::convertToDto);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }
}
