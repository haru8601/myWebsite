package com.haroot.home_page.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 記事データ
 * 
 * @author sekiharuhito
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private int likeCount;
    private boolean wip;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ArticleDto(int id, String title, String content, int likeCount, boolean wip) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.wip = wip;
    }
}
