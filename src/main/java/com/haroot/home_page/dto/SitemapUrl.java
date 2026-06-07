package com.haroot.home_page.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SitemapUrl {
  @JacksonXmlProperty(localName = "loc", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
  private String loc;

  @JacksonXmlProperty(localName = "lastmod", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
  private String lastmod;

  @JacksonXmlProperty(localName = "priority", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
  private String priority;

  @JacksonXmlProperty(localName = "changefreq", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
  private String changefreq;
}
