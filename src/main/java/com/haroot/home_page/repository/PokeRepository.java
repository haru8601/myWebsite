package com.haroot.home_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haroot.home_page.entity.PokeEntity;

@Repository
public interface PokeRepository extends JpaRepository<PokeEntity, Integer> {

}
