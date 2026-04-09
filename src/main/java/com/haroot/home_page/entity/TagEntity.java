package com.haroot.home_page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class TagEntity {
  @Id
  private int id;

  private String name;

  private String type;

  public TagEntity(int id, String name, String type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }
}
