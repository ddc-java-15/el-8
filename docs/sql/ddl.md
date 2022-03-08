---
title: Data Definition Language
description: SQL statements that create the SQLite database for the app.
menu: DDL
order: 40
---

```sqlite
CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`     TEXT                              NOT NULL,
    `age`      INTEGER                           NOT NULL,
    `location` TEXT                              NOT NULL
);

CREATE INDEX IF NOT EXISTS `index_user_name` ON `user` (`name`);

CREATE TABLE IF NOT EXISTS `diary`
(
    `diary_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`  INTEGER                           NOT NULL,
    `entry`    TEXT                              NOT NULL,
    `user_id`  INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_diary_user_id` ON `diary` (`user_id`);

CREATE TABLE IF NOT EXISTS `mood_check_in`
(
    `mood_check_in_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`          INTEGER                           NOT NULL,
    `rating`           INTEGER                           NOT NULL,
    `user_id`          INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_mood_check_in_user_id` ON `mood_check_in` (`user_id`);

CREATE TABLE IF NOT EXISTS `advice`
(
    `advice_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `action`    TEXT,
    `image`     TEXT,
    `favorite`  INTEGER                           NOT NULL,
    `user_id`   INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_advice_favorite` ON `advice` (`favorite`);

CREATE INDEX IF NOT EXISTS `index_advice_user_id` ON `advice` (`user_id`);

```
[`ddl.sql`](ddl.sql)