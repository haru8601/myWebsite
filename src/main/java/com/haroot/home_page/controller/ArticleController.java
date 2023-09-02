package com.haroot.home_page.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.haroot.home_page.dto.ArticleDto;
import com.haroot.home_page.dto.ArticleLikeDto;
import com.haroot.home_page.dto.ArticleRegisterDto;
import com.haroot.home_page.properties.PathProperty;
import com.haroot.home_page.service.ArticleLikeService;
import com.haroot.home_page.service.ArticleService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 記事コントローラー
 *
 * @author haroot
 *
 */
@Controller
@RequestMapping("articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
  private final HttpSession session;
  private final PathProperty pathProperty;
  private final ArticleService articleService;
  private final ArticleLikeService articleLikeService;

  /**
   * 記事一覧表示
   *
   * @param mav MAV
   * @return
   */
  @GetMapping
  public ModelAndView articleAll(ModelAndView mav) {
    List<ArticleDto> articlesList = articleService.getAll();
    mav.addObject("articlesList", articlesList);
    mav.addObject("errStr", "");
    mav.setViewName("contents/articles");
    return mav;
  }

  /**
   * 個別記事表示
   *
   * @param mav MAV
   * @param id  記事ID
   * @return
   */
  @GetMapping("{id}")
  public ModelAndView article(ModelAndView mav, @PathVariable String id) {
    ArticleDto article = articleService.getAndFixArticle(id);
    int likeCount = articleLikeService.getAndAddQiita(id, article.getTitle()).getLikeCount();
    mav.addObject("article", article);
    mav.addObject("likeCount", likeCount);
    mav.setViewName("/contents/articles/template");
    return mav;
  }

  /**
   * 記事のいいね数更新
   *
   * @param id   記事ID
   * @param type 追加か削除か
   */
  @GetMapping("updateCount/{id}/{type}")
  @ResponseBody
  public void updateCount(@PathVariable String id, @PathVariable String type) {
    // qiitaのいいね数は取得せず直接DBから取得
    ArticleLikeDto likeDto = articleLikeService.getOne(id);

    int tmpLikeCount = likeDto.getLikeCount();
    if (type.equals("up")) {
      tmpLikeCount++;
    } else if (type.equals("down")) {
      tmpLikeCount--;
    }
    likeDto.setLikeCount(tmpLikeCount);
    // いいね数更新
    articleLikeService.update(likeDto);
  }

  /**
   * 記事登録API
   *
   * @param mav MAV
   * @return
   */
  @GetMapping("create")
  public ModelAndView registerLink(ModelAndView mav) {
    if (session.getAttribute("isLogin") != null) {
      // 自分のみ作成できる
      final int ID = -1;
      final String TITLE = "";
      final String CONTENT = "";
      final boolean WIP = false;
      mav.addObject("articleRegisterDto", new ArticleRegisterDto(
          ID,
          TITLE,
          CONTENT,
          WIP));
      mav.setViewName("contents/articles/create");
    } else {
      // 他は戻す
      List<ArticleDto> articlesList = articleService.getAll();
      mav.addObject("articlesList", articlesList);
      mav.addObject("errStr", "Sorry, you can't create articles....");
      mav.setViewName("contents/articles");
    }
    return mav;
  }

  /**
   * 記事更新API
   *
   * @param mav MAV
   * @param id  記事ID
   * @return
   */
  @GetMapping("edit/{id}")
  public ModelAndView editLink(ModelAndView mav, @PathVariable String id) {
    // 自分のみ作成できる
    if (session.getAttribute("isLogin") != null) {
      ArticleDto article = articleService.getAndFixArticle(id);
      mav.addObject("articleRegisterDto", ArticleRegisterDto.of(article));
      mav.setViewName("contents/articles/create");
    } else {
      // 他は戻す
      ArticleDto article = articleService.getAndFixArticle(id);
      int likeCount = articleLikeService.getAndAddQiita(id, article.getTitle()).getLikeCount();
      mav.addObject("article", article);
      mav.addObject("likeCount", likeCount);
      mav.addObject("errStr", "Sorry, you can't edit articles....");
      mav.setViewName("contents/articles/template");
    }
    return mav;
  }

  /**
   * 記事登録処理
   *
   * @param articleDto    記事フォーム
   * @param bindingResult エラー結果
   * @param mav           MAV
   * @return
   */
  @PostMapping("register")
  public ModelAndView register(@ModelAttribute @Validated ArticleRegisterDto articleRegisterDto,
      BindingResult bindingResult, ModelAndView mav) {
    // エラーがあれば戻る
    if (bindingResult.hasErrors()) {
      mav.addObject("articleRegisterDto", articleRegisterDto);
      mav.setViewName("contents/articles/create");
      return mav;
    }
    // 外部からの侵入を防ぐためsession切れていれば強制非公開
    if (session.getAttribute("isLogin") == null) {
      articleRegisterDto.setWip(true);
    }
    int id = articleRegisterDto.getId();
    ArticleDto articleDto = new ArticleDto();
    // 既存記事なら色々取得
    if (id != -1) {
      articleDto = articleService.getAndFixArticle(String.valueOf(id));
    }
    articleDto.setId(id);
    articleDto.setTitle(articleRegisterDto.getTitle());
    articleDto.setContent(articleRegisterDto.getContent());
    articleDto.setWip(articleRegisterDto.isWip());
    articleService.update(articleDto);
    mav.setViewName("contents/articles/created");
    return mav;
  }

  /**
   * 画像アップロード
   *
   * @param id   記事IDr
   * @param file ファイル
   */
  @PostMapping("uploadImage/{id}")
  @ResponseBody
  public void uploadImage(@PathVariable String id, @RequestParam("imageFile") MultipartFile file) {
    String articleImagePathStr = pathProperty.getResources() + "/images/articles" + "/" + id;
    Path articleImagePath = Paths.get(articleImagePathStr);
    // フォルダがなければ作成
    if (Files.notExists(articleImagePath)) {
      try {
        // 実行可能にする
        Files.createDirectory(articleImagePath);
        Files.setPosixFilePermissions(articleImagePath, PosixFilePermissions.fromString("rwxrwxr-x"));
      } catch (IOException ex) {
        log.error(ex.getMessage(), ex);
        return;
      }
    }

    // 画像出力
    try (OutputStream os = Files.newOutputStream(Paths.get(articleImagePathStr + "/" + file.getOriginalFilename()),
        StandardOpenOption.CREATE_NEW)) {
      os.write(file.getBytes());
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
    // 権限変更
    try {
      Files.setPosixFilePermissions(Paths.get(articleImagePathStr + "/" + file.getOriginalFilename()),
          PosixFilePermissions.fromString("rw-rw-r--"));
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
  }
}
