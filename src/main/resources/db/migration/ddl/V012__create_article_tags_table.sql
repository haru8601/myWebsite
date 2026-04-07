CREATE TABLE
  `article_tags` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `article_id` INT NOT NULL,
    `tag_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_article_tags_article_tag` (`article_id`, `tag_id`),
    CONSTRAINT `article_tags_article_id_fk` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
    CONSTRAINT `article_tags_tag_id_fk` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
