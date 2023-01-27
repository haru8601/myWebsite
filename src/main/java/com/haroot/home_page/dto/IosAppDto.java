package com.haroot.home_page.dto;

import java.util.ArrayList;
import java.util.List;

import com.haroot.home_page.entity.IosAppEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IosAppDto {
  private int id;
  private String title;
  private String url;
  private String summary;
  private String img;

  public static IosAppDto of(IosAppEntity entity) {
    return new IosAppDto(
      entity.getId(),
      entity.getTitle(),
      entity.getUrl(),
      entity.getSummary(),
      entity.getImg());
  }

  public static List<IosAppDto> listOf(List<IosAppEntity> entityList) {
    List<IosAppDto> dtoList = new ArrayList<>();
    entityList.forEach((entity) -> dtoList.add(of(entity)));
    return dtoList;
  }
}
