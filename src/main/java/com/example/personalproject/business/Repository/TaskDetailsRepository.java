package com.example.personalproject.business.Repository;

import com.example.personalproject.model.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {
    @Query("SELECT COUNT(td) > 0 FROM TaskDetails td WHERE td.task.id = ?1")
    boolean existsByTaskId(Long taskId);
}
