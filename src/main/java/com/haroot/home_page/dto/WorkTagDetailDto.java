package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.TagEntity;
import com.haroot.home_page.entity.WorkTagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkTagDetailDto {
  private int id;
  private int workId;
  private int tagId;
  private String tagName;
  private String tagType;

  public static WorkTagDetailDto of(WorkTagEntity entity, List<TagEntity> tagList) {
    return tagList.stream()
        .filter(tag -> tag.getId() == entity.getTagId())
        .findFirst()
        .map(tag -> new WorkTagDetailDto(
            entity.getId(),
            entity.getWorkId(),
            entity.getTagId(),
            tag.getName(),
            tag.getType()))
        .orElse(null);
  }

  public static List<WorkTagDetailDto> listOf(List<WorkTagEntity> entityList, List<TagEntity> tagList) {
    return entityList
        .stream()
        .map(entity -> of(entity, tagList))
        .toList();
  }
}
