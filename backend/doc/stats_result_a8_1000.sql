-- MySQL dump 10.13  Distrib 5.7.33, for Linux (x86_64)
--
-- Host: localhost    Database: taozi
-- ------------------------------------------------------
-- Server version	5.7.33-0ubuntu0.16.04.1

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
-- Table structure for table `stats_result_a8_1000`
--

DROP TABLE IF EXISTS `stats_result_a8_1000`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stats_result_a8_1000` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` varchar(50) DEFAULT NULL,
  `road_code` varchar(50) DEFAULT NULL COMMENT '线路编码',
  `road_name` varchar(100) DEFAULT NULL COMMENT '线路名称',
  `start_stake` decimal(20,3) DEFAULT NULL COMMENT '起点桩号',
  `length` decimal(20,3) DEFAULT NULL COMMENT '评定长度km',
  `line_dir` varchar(5) DEFAULT NULL COMMENT '调查方向',
  `road_grade` varchar(50) DEFAULT NULL COMMENT '技术等级',
  `pavement_type` varchar(50) DEFAULT NULL COMMENT '路面类型',
  `pqi` decimal(10,2) DEFAULT NULL COMMENT 'PQI',
  `pci` decimal(10,2) DEFAULT NULL COMMENT 'PCI',
  `rqi` decimal(10,2) DEFAULT NULL COMMENT 'RQI',
  `rdi` decimal(10,2) DEFAULT NULL COMMENT 'RDI',
  `pbi` decimal(10,2) DEFAULT NULL COMMENT 'PBI',
  `pwi` decimal(10,2) DEFAULT NULL COMMENT 'PWI',
  `sri` decimal(10,2) DEFAULT NULL COMMENT 'SRI',
  `pssi` decimal(10,2) DEFAULT NULL COMMENT 'PSSI',
  `excellent` decimal(20,3) DEFAULT NULL COMMENT '优',
  `good` decimal(20,3) DEFAULT NULL COMMENT '良',
  `general` decimal(20,3) DEFAULT NULL COMMENT '中',
  `fair` decimal(20,3) DEFAULT NULL COMMENT '次',
  `bad` decimal(20,3) DEFAULT NULL COMMENT '差',
  `percent_excellent` decimal(10,2) DEFAULT NULL COMMENT '优等路率',
  `percent_good` decimal(10,2) DEFAULT NULL COMMENT '优良路率',
  `percent_bad` decimal(10,2) DEFAULT NULL COMMENT '次差路率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-29 12:08:59
