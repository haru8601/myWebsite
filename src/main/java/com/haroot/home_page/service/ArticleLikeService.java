package com.haroot.home_page.service;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.dto.ArticleLikeDto;
import com.haroot.home_page.entity.ArticleLikeEntity;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.properties.QiitaProperty;
import com.haroot.home_page.repository.ArticleLikeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeService {
  private final ArticleLikeRepository articleLikeRepository;
  private final QiitaProperty qiitaProperty;

  /**
   * 取得(1件)
   * 
   * @param id 記事ID
   * @return likeDTO
   */
  public ArticleLikeDto getOne(String id) {
    int idNum = -1;
    try {
      idNum = Integer.parseInt(id);
    } catch (NumberFormatException ex) {
      throw new HarootNotFoundException(
        ex.getMessage(),
        ex);
    }
    Optional<ArticleLikeEntity> entity = articleLikeRepository.findById(idNum);
    // なかったらレコード作成しておく
    if (entity.isEmpty()) {
      ArticleLikeEntity insertEntity = new ArticleLikeEntity();
      insertEntity.setId(idNum);
      return ArticleLikeDto.of(articleLikeRepository.save(insertEntity));
    }
    return ArticleLikeDto.of(entity.get());
  }

  /**
   * 取得してQiita分のいいねも追加
   * 
   * @param id    記事ID
   * @param title 記事タイトル
   * @return likeDTO
   */
  public ArticleLikeDto getAndAddQiita(String id, String title) {
    ArticleLikeDto likeDto = getOne(id);
    int tmpLikeCount = likeDto.getLikeCount();
    // QiitaAPIから記事一覧を取得
    try {
      URL url = new URL(
        "https://qiita.com/api/v2/users/" + qiitaProperty.getUser() + "/items" + "?token=" + qiitaProperty.getToken());
      ObjectMapper mapper = new ObjectMapper();
      JsonNode nodeList = mapper.readTree(url);
      // 各記事情報取得
      for (JsonNode node : nodeList) {
        String qiitaTitle = node.get("title").toString().replaceAll("\"", "");
        // 同じタイトルのがあったらlike数加算
        if (qiitaTitle.equals(title)) {
          tmpLikeCount += node.get("likes_count").asInt();
          break;
        }
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
    likeDto.setLikeCount(tmpLikeCount);
    return likeDto;
  }

  /**
   * 更新
   * 
   * @param dto likeDTO
   * @return 更新後のlikeDTO
   */
  public ArticleLikeDto update(ArticleLikeDto dto) {
    return ArticleLikeDto.of(articleLikeRepository.save(new ArticleLikeEntity(
      dto.getId(),
      dto.getLikeCount())));
  }
}
