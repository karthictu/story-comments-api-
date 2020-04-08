package com.tantec.socials.storycommentsapi.repositories;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tantec.socials.storycommentsapi.beans.Comment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, BigInteger> {

    @Query(value = "SELECT * FROM comment WHERE story_id = :storyId")
    public Optional<List<Comment>> findByStoryId(@Param("storyId") BigInteger storyId);

    @Query(value = "SELECT * FROM comment WHERE user_id = :userId")
    public Optional<List<Comment>> findByUserId(@Param("userId") BigInteger userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE comment SET content = :content, last_updated_timestamp = :lastUpdatedTimestamp, is_edited = true WHERE comment_id = :id",
            nativeQuery = true)
    public void updateComment(@Param("id") BigInteger id, @Param("content") String content, @Param("lastUpdatedTimestamp") Date lastUpdatedTimestamp);

}