CREATE TABLE IF NOT EXISTS
  `work_tags` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `work_id` INT UNSIGNED NOT NULL,
    `tag_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_work_tags_work_tag` (`work_id`, `tag_id`),
    CONSTRAINT `work_tags_work_id_fk` FOREIGN KEY (`work_id`) REFERENCES `work` (`id`),
    CONSTRAINT `work_tags_tag_id_fk` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
