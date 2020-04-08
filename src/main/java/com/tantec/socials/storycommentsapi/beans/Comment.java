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
@Table(name = "comment")
public class Comment {

    @Id
    @Getter
    @Setter
    @NotNull
    @Column(name = "commentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;    

    @Getter
    @Setter
    @NotNull
    @Column(name = "userId")
    private BigInteger userId;

    @Getter
    @Setter
    @NotNull
    @Column(name = "storyId")
    private BigInteger storyId;

    @Getter
    @Setter
    @NotNull
    @Column(name = "content")
    private String content;

    @Getter
    @Setter
    @NotNull
    @Column(name = "lastUpdatedTimestamp")
    private boolean isEdited;

    @Getter
    @Setter
    @NotNull
    @Column(name = "lastUpdatedTimestamp")
    private Date lastUpdatedTimestamp;

    public Comment() {
        this.content = "";
        this.lastUpdatedTimestamp = new Date();
    }
    
}