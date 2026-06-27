package com.haroot.home_page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "work_genres")
public class WorkGenreEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotBlank
  private String name;
  @NotBlank
  private String url;
  private String imagePath;
}
