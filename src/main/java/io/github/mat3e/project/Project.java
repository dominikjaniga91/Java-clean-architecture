package io.github.mat3e.project;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private final Set<ProjectStep> steps = new HashSet<>();

    @PersistenceConstructor
    protected Project() {
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Set<ProjectStep> getSteps() {
        return steps;
    }

    void addStep(ProjectStep step) {
        if (steps.contains(step)) {
            return;
        }
        steps.add(step);
        step.setProject(this);
    }

    void removeStep(ProjectStep step) {
        if (!steps.contains(step)) {
            return;
        }
        steps.remove(step);
        step.setProject(null);
    }
}
