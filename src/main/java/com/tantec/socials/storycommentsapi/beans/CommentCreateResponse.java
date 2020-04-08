package com.tantec.socials.storycommentsapi.beans;

import java.math.BigInteger;

import com.tantec.socials.storycommentsapi.constants.CommonConstants;

import lombok.Getter;
import lombok.Setter;

public class CommentCreateResponse {

    @Getter
    @Setter
    private BigInteger commentId;

    @Getter
    @Setter
    private String message;

    public CommentCreateResponse(BigInteger id, String message) {
        this.commentId = id;
        if (message.equalsIgnoreCase(CommonConstants.SUCCESS)) this.message = CommonConstants.RESPONSE_MSG_CREATE_SUCCESS;
        else if (message.equalsIgnoreCase(CommonConstants.FAILURE)) this.message = CommonConstants.RESPONSE_MSG_CREATE_FAILURE;
    }

}