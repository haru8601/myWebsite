package com.haroot.home_page.quereyService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.WorkDetailDto;
import com.haroot.home_page.dto.WorkDto;
import com.haroot.home_page.dto.WorkGenreDto;
import com.haroot.home_page.dto.WorkTagDetailDto;
import com.haroot.home_page.entity.TagEntity;
import com.haroot.home_page.entity.WorkEntity;
import com.haroot.home_page.entity.WorkTagEntity;
import com.haroot.home_page.repository.TagRepository;
import com.haroot.home_page.repository.WorkGenreRepository;
import com.haroot.home_page.repository.WorkRepository;
import com.haroot.home_page.repository.WorkTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkQueryService {

  private final WorkRepository workRepository;
  private final WorkGenreRepository workGenreRepository;
  private final WorkTagRepository workTagRepository;
  private final TagRepository tagRepository;

  /**
   * ジャンルごとの作品を全件取得する
   *
   * @return
   */
  public Map<WorkGenreDto, List<WorkDto>> findAllGroupedByGenre() {
    // 作品一覧を取得(idの昇順)
    List<WorkDto> workList = WorkDto.listOf(workRepository.findAll(Sort.by("id")));
    // ジャンル一覧を取得
    List<WorkGenreDto> workGenres = WorkGenreDto.ofList(workGenreRepository.findAll());
    // ジャンルをキーごとのMapに変換
    Map<Integer, WorkGenreDto> genreMap = workGenres
        .stream()
        .collect(Collectors.toMap(WorkGenreDto::getId, val -> val));

    // 作品をジャンルごとにグルーピング
    // NOTE: LinkedHashMapで取得時のソートを保持している
    return workList.stream()
        .collect(Collectors.groupingBy(
            work -> genreMap.get(work.getGenreId()),
            LinkedHashMap::new,
            Collectors.toList()));
  }

  public WorkDetailDto getDetail(String url) throws NoSuchElementException {
    // 作品取得
    WorkEntity work = workRepository.findByUrl(url).orElseThrow();
    // 作品のジャンル取得
    WorkGenreDto genre = WorkGenreDto.of(
        workGenreRepository.findById(work.getGenreId())
            .orElseThrow());

    // 作品に紐づくタグ一覧取得
    List<WorkTagEntity> workTagList = workTagRepository.findAllByWorkId(work.getId());
    // タグ一覧の取得
    List<TagEntity> tagList = tagRepository.findAll();
    // 作品のタグにタグ名を紐付け
    List<WorkTagDetailDto> tagDetailList = WorkTagDetailDto.listOf(workTagList, tagList);

    return WorkDetailDto.of(work, genre, tagDetailList);
  }
}
