package io.github.mat3e.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

import io.github.mat3e.task.dto.TaskWithChangesDto;

@RestController
@RequestMapping("/tasks")
class TaskController {
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    TaskController(TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository) {
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    @GetMapping
    List<TaskDto> list() {
        return taskFacade.list();
    }

    @GetMapping(params = "changes")
    Set<TaskWithChangesDto> listWithChanges() {
        return this.taskQueryRepository.findBy(TaskWithChangesDto.class);
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> get(@PathVariable int id) {
        return taskFacade.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<TaskDto> update(@PathVariable int id, @RequestBody TaskDto toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        TaskDto taskDto = toUpdate.toBuilder().id(id).build();
        taskFacade.save(taskDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<TaskDto> create(@RequestBody TaskDto toCreate) {
        TaskDto result = taskFacade.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TaskDto> delete(@PathVariable int id) {
        taskFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
