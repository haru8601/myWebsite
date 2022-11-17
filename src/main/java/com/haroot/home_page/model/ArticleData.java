package com.haroot.home_page.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 記事データ
 * 
 * @author sekiharuhito
 *
 */
@Data
public class ArticleData {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private boolean wip;
}
