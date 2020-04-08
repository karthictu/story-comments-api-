package com.tantec.socials.storycommentsapi.beans;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment_history")
public class CommentHistory {

    @Id
    @Getter
    @Setter
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyId")
    private BigInteger historyId;

    @Getter
    @Setter
    @NotNull
    @Column(name = "commentId")
    private BigInteger commentId;

    @Getter
    @Setter
    @NotNull
    @Column(name = "content")
    private String content;

    @Getter
    @Setter
    @NotNull
    @Column(name = "createdTimestamp")
    private Date createdTimestamp;

    public CommentHistory() {
        this.content = "";
        this.createdTimestamp = new Date();
    }

}