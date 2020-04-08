package com.tantec.socials.storycommentsapi.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.tantec.socials.storycommentsapi.beans.Comment;
import com.tantec.socials.storycommentsapi.repositories.CommentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    @Autowired CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            return null;
        }
    }

    public Comment findCommentById(BigInteger id) {
        var response = commentRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }

    public List<Comment> findCommentsByStoryId(BigInteger storyId) {
        var response = commentRepository.findByStoryId(storyId);
        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }

    public List<Comment> findCommentsByUserId(BigInteger userId) {
        var response = commentRepository.findByUserId(userId);
        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }

    public void updateComment(BigInteger id, String comment, Date lastUpdatedTimestamp) {
        try {
            commentRepository.updateComment(id, comment, lastUpdatedTimestamp);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    public void deleteComment(BigInteger id) {
        commentRepository.deleteById(id);
    }

}