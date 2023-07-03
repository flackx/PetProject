package com.example.personalproject.web.controller;

import com.example.personalproject.business.Service.CommentService;
import com.example.personalproject.model.Comment;
import com.example.personalproject.model.CommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class CommentController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @Operation(summary = "Create a new comment",
            tags = {"Comments", "Create"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TasksController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})

    @PostMapping("/{taskId}/create/comment")
    public ResponseEntity<Comment> createComment(@PathVariable Long taskId, @RequestBody CommentRequest commentRequest) {
        Comment createdComment = commentService.createComment(taskId, commentRequest);
        if (createdComment == null) {
            log.error("Comment not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info("Comment created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
}
