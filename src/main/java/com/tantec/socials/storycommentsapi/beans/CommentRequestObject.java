package com.tantec.socials.storycommentsapi.beans;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class CommentRequestObject {

    @Getter
    @Setter
    @NotNull
    private String content;

    @Getter
    @Setter
    @NotNull
    private BigInteger userId;

    @Getter
    @Setter
    @NotNull
    private BigInteger storyId;

    public CommentRequestObject() {
        this.content = "";
    }

    public boolean validateComment(CommentRequestObject obj) {
        if ((obj.getContent() != null && !obj.getContent().isBlank())
                && obj.getUserId() != null && obj.getStoryId() != null) {
            return true;
        } else {
            return false;
        }
    }
}