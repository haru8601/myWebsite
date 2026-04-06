CREATE TABLE
  `work` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `genre_id` INT UNSIGNED NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `title_en` VARCHAR(50) NOT NULL,
    `summary` VARCHAR(255) DEFAULT NULL,
    `summary_en` VARCHAR(255) DEFAULT NULL,
    `url` VARCHAR(255) NOT NULL,
    `image_path` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `genre_id_fk_idx` (`genre_id`),
    CONSTRAINT `work_genre_id_fk` FOREIGN KEY (`genre_id`) REFERENCES `work_genres` (`id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
