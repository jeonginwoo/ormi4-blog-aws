package com.estsoft.blogjpa.repository;

import com.estsoft.blogjpa.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Article, Long> {
    // JPQL = Java Parsistence Query Language   id, title
    @Modifying  // 변경에 대한 쿼리인지 인지시키기
    @Query("update Article set title = :title, content = :content where id = :id")
    void updateTitle(Long id, String title, String content);
}