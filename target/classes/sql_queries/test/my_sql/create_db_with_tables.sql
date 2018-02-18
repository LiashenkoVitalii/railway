CREATE DATABASE IF NOT EXISTS railway_test /*!40100 DEFAULT CHARACTER SET utf8 */;
USE railway_test;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: railway_test
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id`                BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`           BIGINT(44) UNSIGNED NOT NULL,
  `order_date`        DATETIME                     DEFAULT NULL,
  `route_num_id`      BIGINT(44) UNSIGNED NOT NULL,
  `train_id`          BIGINT(44) UNSIGNED NOT NULL,
  `vagon_type_id`     INT(20) UNSIGNED    NOT NULL,
  `departure_date`    DATETIME                     DEFAULT NULL,
  `departure_station` BIGINT(44) UNSIGNED NOT NULL,
  `arrival_station`   BIGINT(44) UNSIGNED NOT NULL,
  `general_price`     DECIMAL(20, 2)               DEFAULT NULL,
  `is_paid`           TINYINT(1)                   DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_orders_1_idx` (`user_id`),
  KEY `fk_orders_3_idx` (`route_num_id`),
  KEY `fk_orders_4_idx` (`departure_station`),
  KEY `fk_orders_5_idx` (`arrival_station`),
  KEY `fk_orders_2_idx` (`train_id`),
  CONSTRAINT `fk_orders_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_2` FOREIGN KEY (`train_id`) REFERENCES `train` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_3` FOREIGN KEY (`route_num_id`) REFERENCES `route_num` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_4` FOREIGN KEY (`departure_station`) REFERENCES `station` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_5` FOREIGN KEY (`arrival_station`) REFERENCES `station` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password` (
  `id`         BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `password`   BLOB,
  `salt`       BLOB,
  `algorithm`  VARCHAR(45)                  DEFAULT NULL,
  `iterations` INT(11)                      DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_per_km_for_vagon`
--

DROP TABLE IF EXISTS `price_per_km_for_vagon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_per_km_for_vagon` (
  `id`            BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `price`         DECIMAL(20, 2) UNSIGNED      DEFAULT NULL,
  `vagon_type_id` INT(20) UNSIGNED    NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_price_per_km_for_vagon_1_idx` (`vagon_type_id`),
  CONSTRAINT `fk_price_per_km_for_vagon_1` FOREIGN KEY (`vagon_type_id`) REFERENCES `vagon_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(255)
               CHARACTER SET utf8        DEFAULT NULL,
  `name_en_GB` VARCHAR(255)
               CHARACTER SET utf8        DEFAULT NULL,
  `name_uk_UA` VARCHAR(255)
               CHARACTER SET utf8        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `id`             BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `stations_order` INT(10) UNSIGNED    NOT NULL,
  `station_id`     BIGINT(44) UNSIGNED NOT NULL,
  `rout_number_id` BIGINT(44) UNSIGNED          DEFAULT NULL,
  `distance`       FLOAT UNSIGNED               DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_route_1_idx` (`station_id`),
  KEY `fk_route_2_idx` (`rout_number_id`),
  CONSTRAINT `fk_route_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_route_2` FOREIGN KEY (`rout_number_id`) REFERENCES `route_num` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 220
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route_num`
--

DROP TABLE IF EXISTS `route_num`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route_num` (
  `id`     BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT(10) UNSIGNED             DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route_rate`
--

DROP TABLE IF EXISTS `route_rate`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route_rate` (
  `id`       INT(10) UNSIGNED    NOT NULL AUTO_INCREMENT,
  `rate`     FLOAT UNSIGNED               DEFAULT NULL,
  `route_id` BIGINT(44) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `id`         BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `city`       VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  `name`       VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  `city_en_GB` VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  `name_en_GB` VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  `city_uk_UA` VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  `name_uk_UA` VARCHAR(255)
               CHARACTER SET utf8           DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 48
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `id`              BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `station_id`      BIGINT(44) UNSIGNED NOT NULL,
  `departure`       DATETIME                     DEFAULT NULL,
  `arrival`         DATETIME                     DEFAULT NULL,
  `route_number_id` BIGINT(44) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_timetable_1_idx` (`station_id`),
  KEY `fk_timetable_2_idx` (`route_number_id`),
  CONSTRAINT `fk_timetable_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_timetable_2` FOREIGN KEY (`route_number_id`) REFERENCES `route_num` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 201
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `id`           BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `vagon_number` VARCHAR(8)
                 CHARACTER SET utf8  NOT NULL,
  `route_num_id` BIGINT(44) UNSIGNED NOT NULL,
  `vagon_count`  INT(11)                      DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_train_1_idx` (`route_num_id`),
  CONSTRAINT `fk_train_1` FOREIGN KEY (`route_num_id`) REFERENCES `route_num` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id`          BIGINT(44) UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstname`   VARCHAR(255)
                CHARACTER SET utf8  NOT NULL,
  `lastname`    VARCHAR(255)
                CHARACTER SET utf8  NOT NULL,
  `e_mail`      VARCHAR(255)
                CHARACTER SET utf8  NOT NULL,
  `password_id` BIGINT(44) UNSIGNED NOT NULL,
  `role_id`     INT(10) UNSIGNED    NOT NULL,
  `is_banned`   TINYINT(1)                   DEFAULT '0',
  `language`    VARCHAR(45)                  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_user_1_idx` (`role_id`),
  KEY `fk_user_2_idx` (`password_id`),
  CONSTRAINT `fk_user_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_2` FOREIGN KEY (`password_id`) REFERENCES `password` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vagon_type`
--

DROP TABLE IF EXISTS `vagon_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vagon_type` (
  `id`              INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_name`       VARCHAR(255)
                    CHARACTER SET utf8        DEFAULT NULL,
  `type_name_en_GB` VARCHAR(255)              DEFAULT NULL,
  `type_name_uk_UA` VARCHAR(255)
                    CHARACTER SET utf8        DEFAULT NULL,
  `places_count`    INT(11)                   DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dump completed on 2018-01-16 13:21:41
