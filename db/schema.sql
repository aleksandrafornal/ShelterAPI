CREATE TABLE `adopters`
(
    `id`               varchar(36) NOT NULL,
    `adopters_name`    text        NOT NULL,
    `adopters_surname` text        NOT NULL,
    `adopters_age`     int         NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `adopters_requirements`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `adopters_id` varchar(36) NOT NULL,
    `requirement` text        NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);
CREATE TABLE `adoption_data`
(
    `id`            varchar(36) NOT NULL,
    `adopters_id`   varchar(36) DEFAULT NULL,
    `pet_id`        varchar(36) DEFAULT NULL,
    `adoption_date` datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `pets`
(
    `id`      varchar(36) NOT NULL,
    `name`    text        NOT NULL,
    `species` text        NOT NULL,
    `age`     int DEFAULT NULL,
    `size`    text        NOT NULL,
    `status`  text,
    PRIMARY KEY (`id`)
);
CREATE TABLE `requirements`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `requirement` text NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id` (`id`)
);
INSERT INTO requirements (id, requirement)
VALUES (1, 'HOUSE_WITH_GARDEN');
INSERT INTO requirements (id, requirement)
VALUES (2, 'GOOD_SALARY');
INSERT INTO requirements (id, requirement)
VALUES (3, 'IS_TRAINED');
INSERT INTO requirements (id, requirement)
VALUES (4, 'NETTED_BALCONY');








