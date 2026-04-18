package com.haroot.home_page.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.dto.WorkDto;
import com.haroot.home_page.dto.WorkGenreDto;
import com.haroot.home_page.quereyService.WorkQueryService;
import com.haroot.home_page.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author haroot
 */
@Service
@RequiredArgsConstructor
public class WorkService {
  private final WorkRepository workRepository;
  private final WorkQueryService workQueryService;

  public WorkDto get(int workId) throws NoSuchElementException {
    return WorkDto.of(
        workRepository.findById(workId)
            .orElseThrow());
  }

  public WorkDetailDto getWithTags(String name) throws NoSuchElementException {
    return workQueryService.getDetail(name);
  }

  public Map<WorkGenreDto, List<WorkDto>> getAllWithGenres() {
    return workQueryService.findAllGroupedByGenre();
  }
}
