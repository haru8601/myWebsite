package com.haroot.home_page.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.WordsDto;
import com.haroot.home_page.exception.HarootServerException;
import com.haroot.home_page.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHunterService {
  private final FileRepository fileRepository;

  public List<String> get() throws HarootServerException {
    // 魚単語一覧
    WordsDto fishWords = fileRepository.readJson("/json/words/fish.json", WordsDto.class);
    // 野菜単語一覧
    WordsDto vegetableWords = fileRepository.readJson("/json/words/vegetables.json", WordsDto.class);

    return Stream.of(
        fishWords.getNames(),
        vegetableWords.getNames())
        // 単一のリストに変換
        .flatMap(List::stream)
        .toList();
  }
}
