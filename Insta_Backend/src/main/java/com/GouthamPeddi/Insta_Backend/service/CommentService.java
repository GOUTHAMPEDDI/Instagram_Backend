package com.GouthamPeddi.Insta_Backend.service;

import com.GouthamPeddi.Insta_Backend.model.Comment;
import com.GouthamPeddi.Insta_Backend.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    ICommentRepo commentRepo;


    public String addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepo.save(comment);
        return "comment added";
    }

    public Comment findComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElse(null);
        return comment;
    }

    public String removeComment(Comment comment) {
        commentRepo.delete(comment);
        return "comment deleted successfully.";
    }


}
