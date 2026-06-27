package com.haroot.home_page.repository;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.exception.HarootNotFoundException;
import com.haroot.home_page.exception.HarootServerException;
import com.haroot.home_page.properties.PathProperty;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileRepository {
  private final PathProperty pathProperty;

  private static ObjectMapper mapper = new ObjectMapper();

  public <T> T readJson(String filePath, Class<T> clazz) throws HarootServerException {
    try {
      File file = new File(pathProperty.getStaticResources() + filePath);
      if (!file.exists()) {
        System.err.println("file was not found.");
        System.err.println(file.getAbsolutePath());
        throw new HarootNotFoundException("file was not found.");
      }

      BufferedReader br = Files.newBufferedReader(
          file.toPath(),
          Charset.forName("UTF-8"));

      return mapper.readValue(br, clazz);
    } catch (Throwable e) {
      System.err.println("file can not read.");
      System.err.println(e.getMessage());
      throw new HarootServerException("file can not read.", e);
    }
  }
}
