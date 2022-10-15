package com.manulife.task.repository;

import com.manulife.task.dto.TaskDTO;
import com.manulife.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    @Query(value = "SELECT name FROM Task where name = :name", nativeQuery = true)
    List<TaskDTO> getDataByName(String name);

}
