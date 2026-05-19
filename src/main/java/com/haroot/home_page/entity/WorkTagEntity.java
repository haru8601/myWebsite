package com.haroot.home_page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_tags")
@Data
@NoArgsConstructor
public class WorkTagEntity {
  @Id
  private int id;

  private int workId;

  private int tagId;

  public WorkTagEntity(int id, int workId, int tagId) {
    this.id = id;
    this.workId = workId;
    this.tagId = tagId;
  }
}
