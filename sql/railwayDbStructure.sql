-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: railway
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(44) unsigned NOT NULL,
  `order_date` datetime DEFAULT NULL,
  `route_num_id` bigint(44) unsigned NOT NULL,
  `train_id` bigint(44) unsigned NOT NULL,
  `vagon_type_id` int(20) unsigned NOT NULL,
  `departure_date` datetime DEFAULT NULL,
  `departure_station` bigint(44) unsigned NOT NULL,
  `arrival_station` bigint(44) unsigned NOT NULL,
  `general_price` decimal(20,2) DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_orders_1_idx` (`user_id`),
  KEY `fk_orders_3_idx` (`route_num_id`),
  KEY `fk_orders_4_idx` (`departure_station`),
  KEY `fk_orders_5_idx` (`arrival_station`),
  KEY `fk_orders_2_idx` (`train_id`),
  CONSTRAINT `fk_orders_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_2` FOREIGN KEY (`train_id`) REFERENCES `train` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_3` FOREIGN KEY (`route_num_id`) REFERENCES `route_num` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_4` FOREIGN KEY (`departure_station`) REFERENCES `station` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_5` FOREIGN KEY (`arrival_station`) REFERENCES `station` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `password` blob,
  `salt` blob,
  `algorithm` varchar(45) DEFAULT NULL,
  `iterations` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_per_km_for_vagon`
--

DROP TABLE IF EXISTS `price_per_km_for_vagon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_per_km_for_vagon` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `price` decimal(20,2) unsigned DEFAULT NULL,
  `vagon_type_id` int(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_price_per_km_for_vagon_1_idx` (`vagon_type_id`),
  CONSTRAINT `fk_price_per_km_for_vagon_1` FOREIGN KEY (`vagon_type_id`) REFERENCES `vagon_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name_en_GB` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name_uk_UA` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `stations_order` int(10) unsigned NOT NULL,
  `station_id` bigint(44) unsigned NOT NULL,
  `rout_number_id` bigint(44) unsigned DEFAULT NULL,
  `distance` float unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_route_1_idx` (`station_id`),
  KEY `fk_route_2_idx` (`rout_number_id`),
  CONSTRAINT `fk_route_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_route_2` FOREIGN KEY (`rout_number_id`) REFERENCES `route_num` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route_rate`
--

DROP TABLE IF EXISTS `route_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route_rate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rate` float unsigned DEFAULT NULL,
  `route_id` bigint(44) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_route_rate_1_idx` (`route_id`),
  CONSTRAINT `fk_route_rate_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city_en_GB` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name_en_GB` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city_uk_UA` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name_uk_UA` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `station_id` bigint(44) unsigned NOT NULL,
  `departure` datetime DEFAULT NULL,
  `arrival` datetime DEFAULT NULL,
  `route_number_id` bigint(44) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_timetable_1_idx` (`station_id`),
  KEY `fk_timetable_2_idx` (`route_number_id`),
  CONSTRAINT `fk_timetable_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_timetable_2` FOREIGN KEY (`route_number_id`) REFERENCES `route_num` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `vagon_number` varchar(8) CHARACTER SET utf8 NOT NULL,
  `route_num_id` bigint(44) unsigned NOT NULL,
  `vagon_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_train_1_idx` (`route_num_id`),
  CONSTRAINT `fk_train_1` FOREIGN KEY (`route_num_id`) REFERENCES `route` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(44) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `lastname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `e_mail` varchar(255) CHARACTER SET utf8 NOT NULL,
  `password_id` bigint(44) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  `is_banned` tinyint(1) DEFAULT '0',
  `language` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_user_1_idx` (`role_id`),
  KEY `fk_user_2_idx` (`password_id`),
  CONSTRAINT `fk_user_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_2` FOREIGN KEY (`password_id`) REFERENCES `password` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `users_view`
--

DROP TABLE IF EXISTS `users_view`;
/*!50001 DROP VIEW IF EXISTS `users_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `users_view` AS SELECT 
 1 AS `id`,
 1 AS `firstname`,
 1 AS `lastname`,
 1 AS `e_mail`,
 1 AS `role`,
 1 AS `is_banned`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `vagon_type`
--

DROP TABLE IF EXISTS `vagon_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vagon_type` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type_name_en_GB` varchar(255) DEFAULT NULL,
  `type_name_uk_UA` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `places_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `users_view`
--

/*!50001 DROP VIEW IF EXISTS `users_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `users_view` AS select `user`.`id` AS `id`,`user`.`firstname` AS `firstname`,`user`.`lastname` AS `lastname`,`user`.`e_mail` AS `e_mail`,`role`.`name` AS `role`,`user`.`is_banned` AS `is_banned` from (`user` left join `role` on((`user`.`role_id` = `role`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-22  0:46:17
