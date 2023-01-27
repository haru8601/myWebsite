package com.haroot.home_page.dto;

import java.util.ArrayList;
import java.util.List;

import com.haroot.home_page.entity.PokeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokeDto {
	private int id;
	private String title;
	private String summary;
	private String url;
	private String img;

	public static PokeDto of(PokeEntity entity) {
		return new PokeDto(
			entity.getId(),
			entity.getTitle(),
			entity.getSummary(),
			entity.getUrl(),
			entity.getImg());
	}

	public static List<PokeDto> ofList(List<PokeEntity> entityList) {
		List<PokeDto> dtoList = new ArrayList<>();
		entityList.forEach((x) -> dtoList.add(of(x)));
		return dtoList;
	}
}
