package com.haroot.home_page.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haroot.home_page.entity.ArticleEntity;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    List<ArticleEntity> findAllByOrderByUpdateDateDesc();

    List<ArticleEntity> findAllByWipFalseOrderByUpdateDateDesc();

    Integer findLikeCountById(int id);
}
