package com.haroot.home_page.quereyService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.WorkDto;
import com.haroot.home_page.dto.WorkGenreDto;
import com.haroot.home_page.repository.WorkGenreRepository;
import com.haroot.home_page.repository.WorksRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorksQueryService {

  private final WorksRepository worksRepository;
  private final WorkGenreRepository workGenreRepository;

  /**
   * ジャンルごとの作品を全件取得する
   *
   * @return
   */
  public Map<WorkGenreDto, List<WorkDto>> findAllGroupedByGenre() {
    // 作品一覧を取得(idの昇順)
    List<WorkDto> works = WorkDto.ofList(worksRepository.findAll(Sort.by("id")));
    // ジャンル一覧を取得
    List<WorkGenreDto> workGenres = WorkGenreDto.ofList(workGenreRepository.findAll());
    // ジャンルをキーごとのMapに変換
    Map<Integer, WorkGenreDto> genreMap = workGenres
        .stream()
        .collect(Collectors.toMap(WorkGenreDto::getId, g -> g));

    // 作品をジャンルごとにグルーピング
    // NOTE: LinkedHashMapで取得時のソートを保持している
    return works.stream()
        .collect(Collectors.groupingBy(
            work -> genreMap.get(work.getGenreId()),
            LinkedHashMap::new,
            Collectors.toList()));
  }
}
