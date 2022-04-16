package io.github.mat3e.task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

interface TaskRepository extends Repository<Task, Integer> {

    Optional<Task> findById(Integer id);

    Task save(Task task);

    List<Task> saveAll(Iterable<Task> task);
//    <S extends Task> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}
