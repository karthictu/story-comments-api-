package com.tantec.socials.storycommentsapi.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class CommentHistoryResponseObject {

    @Getter
    @Setter   
    @NotNull
    private String content;
    
    @Getter
    @Setter
    @NotNull
    private Date createdTimestamp;

    public CommentHistoryResponseObject() {
        this.content = "";
        this.createdTimestamp = new Date();
    }

}