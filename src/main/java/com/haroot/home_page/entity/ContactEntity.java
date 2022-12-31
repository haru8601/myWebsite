package com.haroot.home_page.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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