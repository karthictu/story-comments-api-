package com.tantec.socials.storycommentsapi.beans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class CommentHistoryResponse {

    @Getter
    @Setter
    @NotNull
    private BigInteger commentId;

    @Getter
    @Setter
    @NotNull
    private List<CommentHistoryResponseObject> edits;

    public CommentHistoryResponse() {
        this.edits = new ArrayList<CommentHistoryResponseObject>();
    }

    public void addHistory(CommentHistoryResponseObject historyObj) {
        this.edits.add(historyObj);
    }

}