package com.tantec.socials.storycommentsapi.beans;

import java.io.Serializable;
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
import lombok.ToString;

@Entity
@Table(name = "comment_history", schema = "public")
@ToString(callSuper = true, includeFieldNames = true)
public class CommentHistory implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8215159729946221886L;

    @Id
    @Getter
    @Setter
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