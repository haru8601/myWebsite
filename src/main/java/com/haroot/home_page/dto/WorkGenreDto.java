package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkGenreEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkGenreDto {
  private int id;
  private String name;
  private String url;
  private String imagePath;

  public static WorkGenreDto of(WorkGenreEntity entity) {
    return new WorkGenreDto(
        entity.getId(),
        entity.getName(),
        entity.getUrl(),
        entity.getImagePath());
  }

  public static List<WorkGenreDto> ofList(List<WorkGenreEntity> entityList) {
    return entityList
        .stream()
        .map(entity -> of(entity))
        .toList();
  }
}
