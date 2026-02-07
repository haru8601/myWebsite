package com.haroot.home_page.dto;

import java.util.List;

import lombok.Data;

/**
 * 単語一覧データ
 * 
 * @author sekiharuhito
 *
 */
@Data
public class WordsDto {
  private List<String> names;
}
