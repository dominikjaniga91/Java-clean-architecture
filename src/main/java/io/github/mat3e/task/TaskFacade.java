package io.github.mat3e.task;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

import javax.annotation.PostConstruct;

@Service
public class TaskFacade {
    private final TaskRepository taskRepository;

    TaskFacade(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    void init() {
        if (this.taskRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            this.taskRepository.save(task);
        }
    }

    TaskDto save(TaskDto toSave) {
        return new TaskDto(
                taskRepository.save(
                        taskRepository.findById(toSave.getId())
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
                        })
                )
        );
    }

    List<TaskDto> list() {
        return taskRepository.findAll().stream()
                .map(TaskDto::new)
                .collect(toList());
    }

    List<TaskWithChangesDto> listWithChanges() {
        return taskRepository.findAll().stream()
                .map(TaskWithChangesDto::new)
                .collect(toList());
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(TaskDto::new);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }
}