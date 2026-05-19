package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.WorkTagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTagDto {
  private int id;
  private int workId;
  private int tagId;

  public static WorkTagDto of(WorkTagEntity entity) {
    return new WorkTagDto(
        entity.getId(),
        entity.getWorkId(),
        entity.getTagId());
  }

  public static List<WorkTagDto> listOf(List<WorkTagEntity> entityList) {
    return entityList
        .stream()
        .map(entity -> of(entity))
        .toList();
  }
}
