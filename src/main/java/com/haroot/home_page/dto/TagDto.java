package com.haroot.home_page.dto;

import java.util.List;

import com.haroot.home_page.entity.TagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {
  private int id;
  private String name;
  private String type;

  public static TagDto of(TagEntity entity) {
    return new TagDto(
        entity.getId(),
        entity.getName(),
        entity.getType());
  }

  public static List<TagDto> listOf(List<TagEntity> entityList) {
    return entityList
        .stream()
        .map(entity -> of(entity))
        .toList();
  }
}
