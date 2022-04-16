package io.github.mat3e.project.query;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.PersistenceConstructor;

/**
 * Simplify version of Project entity
 * used to communicate with entities that
 * hold project as foreign key in database.
 *
 * @author Dominik_Janiga
 */
@Entity
@Table(name = "projects")
public class SimpleProjectQueryDto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;

    @PersistenceConstructor
    protected SimpleProjectQueryDto() { }

    public SimpleProjectQueryDto(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
