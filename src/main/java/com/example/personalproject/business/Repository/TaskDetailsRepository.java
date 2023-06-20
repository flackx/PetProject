package com.example.personalproject.business.Repository;

import com.example.personalproject.model.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {

}
