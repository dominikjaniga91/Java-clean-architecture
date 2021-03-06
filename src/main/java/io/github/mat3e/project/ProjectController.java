package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;
import io.github.mat3e.task.dto.TaskDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects")
class ProjectController {
    private final ProjectFacade projectFacade;
    private final ProjectQueryRepository projectQueryRepository;

    ProjectController(final ProjectFacade projectFacade, final ProjectQueryRepository projectQueryRepository) {
        this.projectFacade = projectFacade;
        this.projectQueryRepository = projectQueryRepository;
    }

    @GetMapping
    List<ProjectDto> list() {
        return this.projectQueryRepository.findAllBy();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProjectDto> get(@PathVariable int id) {
        return this.projectQueryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<ProjectDto> update(@PathVariable int id, @RequestBody Project toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        projectFacade.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<Project> create(@RequestBody Project toCreate) {
        Project result = projectFacade.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PostMapping("/{id}/tasks")
    List<TaskDto> createTasks(@PathVariable int id, @RequestBody ProjectDeadlineDto deadlineDto) {
        return projectFacade.createTasks(id, deadlineDto.getDeadline());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleClientError(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
