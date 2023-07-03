package com.example.personalproject.business.Service.impl;

import com.example.personalproject.business.Repository.CommentRepository;
import com.example.personalproject.business.Repository.TaskRepository;
import com.example.personalproject.business.Service.CommentService;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.CommentRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CommentImpl implements CommentService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TaskServiceImpl.class);
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    public CommentImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Comment createComment(Long taskId, CommentRequest commentRequest) {
        log.info("Creating comment for task with id: {}", taskId);
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUsername(commentRequest.getUsername());
        return commentRepository.save(comment);
    }
}
