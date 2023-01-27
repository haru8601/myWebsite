package com.haroot.home_page.dto;

import java.util.ArrayList;
import java.util.List;

import com.haroot.home_page.entity.MusicEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicDto {
  private int id;
  private String title;
  private String summary;
  private String url;
  private String img;

  public static MusicDto of(MusicEntity entity) {
    return new MusicDto(
      entity.getId(),
      entity.getTitle(),
      entity.getSummary(),
      entity.getUrl(),
      entity.getImg());
  }

  public static List<MusicDto> ofList(List<MusicEntity> entityList) {
    List<MusicDto> dtoList = new ArrayList<>();
    entityList.forEach((x) -> dtoList.add(of(x)));
    return dtoList;
  }
}
