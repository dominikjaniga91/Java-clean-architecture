package io.github.mat3e.task;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.Repository;

import io.github.mat3e.task.dto.TaskWithChangesDto;

/**
 * @author Dominik_Janiga
 */
public interface TaskQueryRepository extends Repository<Task, Integer> {

    int count();

    boolean existsByDoneIsFalseAndProject_Id(int id);

    List<Task> findAll();

    List<TaskWithChangesDto> findWithChangesBy();
    /**
     * Dynamic Spring projection method that
     * return the list of elements with type defined by parameter.
     * WARNING: doest not work with List, it must be Set.
     *
     * @param clazz defines the type of elements.
     * @param <T> type of the elements.
     * @return list of elements of specific type.
     * @see <a href="https://www.baeldung.com/spring-data-jpa-projections">Baeldung</a>
     */
    <T> Set<T> findBy(Class<T> clazz);
}
