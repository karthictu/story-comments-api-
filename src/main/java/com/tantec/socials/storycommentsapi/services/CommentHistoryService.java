package com.tantec.socials.storycommentsapi.services;

import java.math.BigInteger;
import java.util.List;

import com.tantec.socials.storycommentsapi.beans.CommentHistory;
import com.tantec.socials.storycommentsapi.repositories.CommentHistoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentHistoryService.class);

    @Autowired
    CommentHistoryRepository historyRepository;

    public List<CommentHistory> searchHistoryByCommentId(BigInteger commentId) {
        var response = historyRepository.findByCommentId(commentId);
        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }

    public CommentHistory getCommentHistoryById(BigInteger id) {
        var response = historyRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }

    public CommentHistory addCommentHistoryById(CommentHistory obj) {
        try {
            return historyRepository.save(obj);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }        
    }

    public void deleteHistoryByCommentId(BigInteger commentId) {
        try {
            historyRepository.deleteAllByCommentId(commentId);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }        
    }

}