package io.github.mat3e.project;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.TaskFacade;
import io.github.mat3e.task.TaskQueryRepository;

@Service
class ProjectFacade {
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;
    private final ProjectQueryRepository projectQueryRepository;

    ProjectFacade(final ProjectRepository projectRepository, final ProjectStepRepository projectStepRepository,
            final TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository,
            final ProjectQueryRepository projectQueryRepository) {
        this.projectRepository = projectRepository;
        this.projectStepRepository = projectStepRepository;
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
        this.projectQueryRepository = projectQueryRepository;
    }

    @PostConstruct
    void init() {
        if (this.projectQueryRepository.count() == 0) {
            var project = new Project();
            project.setName("Example project");
            project.addStep(new ProjectStep("First", -3, project));
            project.addStep(new ProjectStep("Second", -2, project));
            project.addStep(new ProjectStep("Third", 0, project));
            projectRepository.save(project);
        }
    }

    Project save(Project toSave) {
        if (toSave.getId() != 0) {
            return saveWithId(toSave);
        }
        if (toSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        toSave.getSteps().forEach(step -> {
            if (step.getProject() == null) {
                Project project = new Project();
                project.setId(toSave.getId());
                project.setName(toSave.getName());
                toSave.getSteps().forEach(project::addStep);
                step.setProject(project);
            }
        });
        return projectRepository.save(toSave);
    }

    private Project saveWithId(Project toSave) {
        return projectRepository.findById(toSave.getId())
                .map(existingProject -> {
                    Set<ProjectStep> stepsToRemove = new HashSet<>();
                    existingProject.setName(toSave.getName());
                    existingProject.getSteps()
                            .forEach(existingStep -> toSave.getSteps().stream()
                                    .filter(potentialOverride -> existingStep.getId() == potentialOverride.getId())
                                    .findFirst()
                                    .ifPresentOrElse(
                                            overridingStep -> {
                                                existingStep.setDescription(overridingStep.getDescription());
                                                existingStep.setDaysToProjectDeadline(overridingStep.getDaysToProjectDeadline());
                                            },
                                            () -> stepsToRemove.add(existingStep)
                                    )
                            );
                    stepsToRemove.forEach(toRemove -> {
                        existingProject.removeStep(toRemove);
                        projectStepRepository.delete(toRemove);
                    });
                    toSave.getSteps().stream()
                            .filter(newStep -> existingProject.getSteps().stream()
                                    .noneMatch(existingStep -> existingStep.getId() == newStep.getId())
                            ).collect(toSet())
                            // collecting first to allow multiple id=0
                            .forEach(existingProject::addStep);
                    projectRepository.save(existingProject);
                    return existingProject;
                }).orElseGet(() -> {
                    toSave.getSteps().forEach(step -> {
                        if (step.getProject() == null) {
                            step.setProject(toSave);
                        }
                    });
                    return projectRepository.save(toSave);
                });
    }


    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        if (taskQueryRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        return projectRepository.findById(projectId).map(project -> {
                    List<TaskDto> collect = project.getSteps().stream()
                                    .map(step -> TaskDto.builder()
                                            .description(step.getDescription())
                                            .deadline(projectDeadline.plusDays(step.getDaysToProjectDeadline()))
                                            .build()).collect(toList());
                            return taskFacade.saveAll(collect, new SimpleProjectQueryDto(projectId, project.getName()));
                        }
                ).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }
}
