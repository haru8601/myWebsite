package com.haroot.home_page.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleRegisterDto {
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private boolean wip;

    public static ArticleRegisterDto of(ArticleDto dto) {
        return new ArticleRegisterDto(dto.getId(), dto.getTitle(), dto.getContent(), dto.isWip());
    }
}
