package com.haroot.home_page.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.WorkDto;
import com.haroot.home_page.dto.WorkGenreDto;
import com.haroot.home_page.quereyService.WorksQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkService {
  private final WorksQueryService worksQueryService;

  public Map<WorkGenreDto, List<WorkDto>> getAllWithGenres() {
    return worksQueryService.findAllGroupedByGenre();
  }
}
