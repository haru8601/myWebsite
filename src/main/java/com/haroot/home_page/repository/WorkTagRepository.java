package com.haroot.home_page.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haroot.home_page.entity.WorkTagEntity;

@Repository
public interface WorkTagRepository extends JpaRepository<WorkTagEntity, Integer> {
  List<WorkTagEntity> findAllByWorkId(int workId);
}
