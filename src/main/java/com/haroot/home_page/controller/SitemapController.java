package com.haroot.home_page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.haroot.home_page.exception.HarootServerException;
import com.haroot.home_page.service.SitemapService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SitemapController {

  private final SitemapService sitemapService;

  private static final XmlMapper XML_MAPPER = new XmlMapper();

  @GetMapping(value = "/sitemap.xml", produces = "application/xml;charset=UTF-8")
  @ResponseBody
  public String sitemap() {
    try {
      return XML_MAPPER.writeValueAsString(sitemapService.generate());
    } catch (JsonProcessingException e) {
      throw new HarootServerException("サイトマップの生成に失敗しました.", e);
    }
  }
}
