package io.github.mat3e.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByDoneIsFalseANdProject_Id(int id);
}
