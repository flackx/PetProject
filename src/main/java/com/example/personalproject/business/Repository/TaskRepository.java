package com.example.personalproject.business.Repository;

import com.example.personalproject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

   // Task findByCommentId(Long commentId);

}