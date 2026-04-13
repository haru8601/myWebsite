package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkDetailDto {
  private int id;
  private String name;
  private WorkGenreDto genre;
  private String title;
  private String titleEn;
  private String summary;
  private String summaryEn;
  private String imagePath;
  private String workUrl;
  private String policyUrl;
  private List<WorkTagDetailDto> tagList;

  public static WorkDetailDto of(WorkEntity entity,
      WorkGenreDto genre, List<WorkTagDetailDto> tagList) {
    return new WorkDetailDto(
        entity.getId(),
        entity.getName(),
        genre,
        entity.getTitle(),
        entity.getTitleEn(),
        entity.getSummary(),
        entity.getSummaryEn(),
        entity.getImagePath(),
        entity.getWorkUrl(),
        entity.getPolicyUrl(),
        tagList);
  }
}
