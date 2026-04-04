package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkDto {
  private int id;
  private int genreId;
  private String title;
  private String titleEn;
  private String summary;
  private String summaryEn;
  private String url;
  private String imagePath;

  public static WorkDto of(WorkEntity entity) {
    return new WorkDto(
        entity.getId(),
        entity.getGenreId(),
        entity.getTitle(),
        entity.getTitleEn(),
        entity.getSummary(),
        entity.getSummaryEn(),
        entity.getUrl(),
        entity.getImagePath());
  }

  public static List<WorkDto> ofList(List<WorkEntity> entityList) {
    return entityList
        .stream()
        .map((entity) -> WorkDto.of(entity))
        .toList();
  }
}
