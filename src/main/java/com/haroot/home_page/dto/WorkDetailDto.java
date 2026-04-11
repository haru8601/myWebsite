package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkDetailDto {
  private int id;
  private int genreId;
  private String title;
  private String titleEn;
  private String summary;
  private String summaryEn;
  private String url;
  private String imagePath;
  private List<WorkTagDetailDto> tagList;

  public static WorkDetailDto of(WorkEntity entity, List<WorkTagDetailDto> tagList) {
    return new WorkDetailDto(
        entity.getId(),
        entity.getGenreId(),
        entity.getTitle(),
        entity.getTitleEn(),
        entity.getSummary(),
        entity.getSummaryEn(),
        entity.getUrl(),
        entity.getImagePath(),
        tagList);
  }

  public static List<WorkDetailDto> listOf(List<WorkEntity> entityList, List<WorkTagDetailDto> tagList) {
    return entityList
        .stream()
        .map(entity -> of(entity, tagList))
        .toList();
  }
}
