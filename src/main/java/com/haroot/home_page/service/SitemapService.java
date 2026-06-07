package com.haroot.home_page.service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.SitemapUrl;
import com.haroot.home_page.dto.SitemapUrlset;
import com.haroot.home_page.properties.PathProperty;
import com.haroot.home_page.quereyService.ArticleQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SitemapService {

  private final ArticleQueryService articleQueryService;
  private final WorkService workService;
  private final PathProperty pathProperty;

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static final String[] STATIC_PATHS = {
      "/about", "/work", "/articles", "/contact", "/policy", "/policy/english-quiz"
  };

  private static final String CHANGE_FREQUENCY = "monthly";

  public SitemapUrlset generate() {
    String baseUrl = "https://" + pathProperty.getSite();

    Stream<SitemapUrl> topUrl = Arrays.stream(STATIC_PATHS)
        .map(path -> new SitemapUrl(
            baseUrl,
            null,
            "1.0",
            CHANGE_FREQUENCY));

    Stream<SitemapUrl> staticUrls = Arrays.stream(STATIC_PATHS)
        .map(path -> new SitemapUrl(
            baseUrl + path,
            null,
            "0.8",
            CHANGE_FREQUENCY));

    Stream<SitemapUrl> articleUrls = articleQueryService.findAllWithoutPrivate().stream()
        .map(article -> new SitemapUrl(
            baseUrl + "/articles/" + article.getId(),
            article.getUpdateDate().format(DATE_FORMATTER),
            "0.7",
            CHANGE_FREQUENCY));

    Stream<SitemapUrl> workUrls = workService.getAllWithGenres()
        .entrySet().stream()
        // キーを無視して作品一覧のリストにする
        .flatMap(entry -> entry.getValue().stream()
            .map(work -> new SitemapUrl(
                baseUrl + "/work/" + entry.getKey().getUrl() + "/" + work.getName(),
                null,
                "0.7",
                CHANGE_FREQUENCY)));

    List<SitemapUrl> urls = Stream.of(topUrl, staticUrls, articleUrls, workUrls)
        .flatMap(s -> s)
        .toList();

    return new SitemapUrlset(urls);
  }
}
