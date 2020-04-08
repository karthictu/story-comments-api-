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

    public CommentRequestObject() {
        this.content = "";
    }

    public boolean validateComment(CommentRequestObject obj) {
        if ((obj.getContent() != null && !obj.getContent().isBlank())) {
            return true;
        } else {
            return false;
        }
    }
}