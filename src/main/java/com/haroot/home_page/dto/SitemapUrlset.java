package com.haroot.home_page.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class SitemapUrlset {
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
  private List<SitemapUrl> urls;
}
