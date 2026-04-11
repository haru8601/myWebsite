package com.haroot.home_page.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haroot.home_page.entity.WorkEntity;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, Integer> {
  Optional<WorkEntity> findByUrl(String url);
}
