package com.tantec.socials.storycommentsapi.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tantec.socials.storycommentsapi.beans.Comment;
import com.tantec.socials.storycommentsapi.beans.CommentCreateResponse;
import com.tantec.socials.storycommentsapi.beans.CommentHistory;
import com.tantec.socials.storycommentsapi.beans.CommentHistoryResponse;
import com.tantec.socials.storycommentsapi.beans.CommentHistoryResponseObject;
import com.tantec.socials.storycommentsapi.beans.CommentRequestObject;
import com.tantec.socials.storycommentsapi.constants.CommonConstants;
import com.tantec.socials.storycommentsapi.services.CommentHistoryService;
import com.tantec.socials.storycommentsapi.services.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping(path = "/api/stories/comments")
@CrossOrigin(allowedHeaders = CommonConstants.SYMBOLS_ASTERISK, methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
public class CommentsController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentHistoryService commentHistoryService;

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<CommentCreateResponse> saveComment(
            @RequestHeader(name = "ACTIVE-USER-ID", required = true) BigInteger userId,
            @RequestBody CommentRequestObject requestBody) {

        if (!requestBody.validateComment(requestBody)) {
            return new ResponseEntity<CommentCreateResponse>(HttpStatus.BAD_REQUEST);
        } else {
            Date currentTime = new Date();
            Comment comment = new Comment();
            comment.setContent(requestBody.getContent());
            comment.setStoryId(requestBody.getStoryId());
            comment.setUserId(userId);
            comment.setEdited(false);
            comment.setLastUpdatedTimestamp(currentTime);

            comment = commentService.saveComment(comment);
            if (comment == null) {
                return new ResponseEntity<CommentCreateResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                CommentCreateResponse response = new CommentCreateResponse(comment.getId(), CommonConstants.SUCCESS);
                return new ResponseEntity<CommentCreateResponse>(response, HttpStatus.CREATED);
            }
        }
    }

    @PostMapping(path = "/search", produces = "application/json")
    public ResponseEntity<List<Comment>> searchComment(@RequestBody Map<String, BigInteger> requestBody) {

        if (requestBody.containsKey("userId")) {
            List<Comment> response = commentService.findCommentsByUserId(requestBody.get("userId"));
            if (response == null || response.size() < 1) {
                return new ResponseEntity<List<Comment>>(new ArrayList<Comment>(), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Comment>>(response, HttpStatus.OK);
            }
        } else if (requestBody.containsKey("storyId")) {
            List<Comment> response = commentService.findCommentsByStoryId(requestBody.get("storyId"));
            if (response == null || response.size() < 1) {
                return new ResponseEntity<List<Comment>>(new ArrayList<Comment>(), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Comment>>(response, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Comment> getComment(@PathVariable BigInteger commentId) {
        Comment comment = commentService.findCommentById(commentId);

        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Comment>(comment, HttpStatus.OK);
        }
    }

    @PutMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Comment> editComment(
            @RequestHeader(name = "ACTIVE-USER-ID", required = true) BigInteger userId,
            @RequestBody CommentRequestObject requestBody,
            @PathVariable BigInteger id) {
        if (requestBody.validateComment(requestBody)) {
            Date currentTime = new Date();

            Comment comment = commentService.findCommentById(id);
            if (comment == null)
                return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
            else {
                commentService.updateComment(id, requestBody.getContent(), currentTime);

                CommentHistory oldComment = new CommentHistory();
                oldComment.setCommentId(comment.getId());
                oldComment.setContent(comment.getContent());
                oldComment.setCreatedTimestamp(comment.getLastUpdatedTimestamp());

                if (commentHistoryService.addCommentHistoryById(oldComment) == null) {
                    commentService.updateComment(id, comment.getContent(), comment.getLastUpdatedTimestamp());
                    return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
                } else
                    return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Comment> deleteComment(
            @RequestHeader(name = "ACTIVE-USER-ID", required = true) BigInteger userId,
            @PathVariable BigInteger id) {
        commentService.deleteComment(id);
        commentHistoryService.deleteHistoryByCommentId(id);
        return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{id}/edit-history", produces = "application/json")
    public ResponseEntity<CommentHistoryResponse> getEditHistoryForComment(
            @RequestHeader(name = "ACTIVE-USER-ID", required = true) BigInteger userId,
            @PathVariable BigInteger commentId) {

        CommentHistoryResponse response = new CommentHistoryResponse();
        List<CommentHistory> historyList = commentHistoryService.searchHistoryByCommentId(commentId);

        if (historyList == null)
            return new ResponseEntity<CommentHistoryResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        else {
            response.setCommentId(commentId);

            for (CommentHistory historyObj : historyList) {
                CommentHistoryResponseObject responseObj = new CommentHistoryResponseObject();
                responseObj.setContent(historyObj.getContent());
                responseObj.setCreatedTimestamp(historyObj.getCreatedTimestamp());
                response.addHistory(responseObj);
            }
            return new ResponseEntity<CommentHistoryResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}