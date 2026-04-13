package com.haroot.home_page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "work")
public class WorkEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotBlank
  private String name;
  @NotNull
  private int genreId;
  @NotBlank
  private String title;
  @NotBlank
  private String titleEn;
  private String summary;
  private String summaryEn;
  private String imagePath;
  private String workUrl;
  private String policyUrl;
}
