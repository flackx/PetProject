package com.example.personalproject.business.Service;

import com.example.personalproject.model.Comment;
import com.example.personalproject.model.CommentRequest;
import org.springframework.stereotype.Service;

@Service

public interface CommentService {
    Comment createComment(Long taskId, CommentRequest commentRequest);

}
