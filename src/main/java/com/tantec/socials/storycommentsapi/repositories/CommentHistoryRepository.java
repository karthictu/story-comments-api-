package com.tantec.socials.storycommentsapi.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.tantec.socials.storycommentsapi.beans.CommentHistory;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentHistoryRepository extends CrudRepository<CommentHistory, BigInteger> {

    @Query(value = "SELECT * FROM comment_history WHERE comment_id = :commentId",
            nativeQuery = true)
    public Optional<List<CommentHistory>> findByCommentId(@Param("commentId") BigInteger commentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment_history WHERE comment_id = :commentId",
            nativeQuery = true)
    public void deleteAllByCommentId(@Param("commentId") BigInteger commentId);

}