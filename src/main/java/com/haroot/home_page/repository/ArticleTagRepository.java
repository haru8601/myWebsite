package com.haroot.home_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haroot.home_page.entity.ArticleTagEntity;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, Integer> {

}
