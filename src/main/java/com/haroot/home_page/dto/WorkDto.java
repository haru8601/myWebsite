package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkDto {
  private int id;
  private String name;
  private int genreId;
  private String title;
  private String titleEn;
  private String summary;
  private String summaryEn;
  private String imagePath;
  private String workUrl;
  private String policyUrl;

  public static WorkDto of(WorkEntity entity) {
    return new WorkDto(
        entity.getId(),
        entity.getName(),
        entity.getGenreId(),
        entity.getTitle(),
        entity.getTitleEn(),
        entity.getSummary(),
        entity.getSummaryEn(),
        entity.getImagePath(),
        entity.getWorkUrl(),
        entity.getPolicyUrl());
  }

  public static List<WorkDto> listOf(List<WorkEntity> entityList) {
    return entityList
        .stream()
        .map(entity -> of(entity))
        .toList();
  }
}
