package com.example.jakwywiozebackend.controller;

import com.example.jakwywiozebackend.dto.comment.CommentBasic;
import com.example.jakwywiozebackend.dto.comment.CommentDto;
import com.example.jakwywiozebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentBasic>> getCommentsForPoint(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getCommentsForPoint(id), HttpStatus.OK);
    }
}
