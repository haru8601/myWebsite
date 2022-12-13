package com.haroot.home_page.controller.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String content;
    private String ip;
    @CreationTimestamp
    private LocalDateTime createDate;

    public ContactEntity(String name, String email, String content, String ip) {
        this.name = name;
        this.email = email;
        this.content = content;
        this.ip = ip;
    }
}