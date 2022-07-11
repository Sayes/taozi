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
-- Table structure for table `stats_result_hundred_1000`
--

DROP TABLE IF EXISTS `stats_result_hundred_1000`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stats_result_hundred_1000` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` varchar(50) DEFAULT NULL,
  `road_code` varchar(50) DEFAULT NULL COMMENT '线路编码',
  `road_name` varchar(100) DEFAULT NULL COMMENT '线路名称',
  `line_dir` varchar(5) DEFAULT NULL COMMENT '方向',
  `mqi` decimal(10,2) DEFAULT NULL COMMENT 'MQI',
  `pqi` decimal(10,2) DEFAULT NULL COMMENT 'PQI',
  `pci` decimal(10,2) DEFAULT NULL COMMENT 'PCI',
  `rqi` decimal(10,2) DEFAULT NULL COMMENT 'RQI',
  `rdi` decimal(10,2) DEFAULT NULL COMMENT 'RDI',
  `pbi` decimal(10,2) DEFAULT NULL COMMENT 'PBI',
  `pwi` decimal(10,2) DEFAULT NULL COMMENT 'PWI',
  `sri` decimal(10,2) DEFAULT NULL COMMENT 'SRI',
  `sci` decimal(10,2) DEFAULT NULL COMMENT 'SCI',
  `bci` decimal(10,2) DEFAULT NULL COMMENT 'BCI',
  `tci` decimal(10,2) DEFAULT NULL COMMENT 'TCI',
  `pssi` decimal(10,2) DEFAULT NULL COMMENT 'PSSI',
  `start_stake` decimal(20,3) DEFAULT NULL COMMENT '起点桩号',
  `end_stake` decimal(20,3) DEFAULT NULL COMMENT '终点桩号',
  `length` decimal(20,3) DEFAULT NULL COMMENT '路段长度',
  `district` varchar(100) DEFAULT NULL,
  `maintain` varchar(100) DEFAULT NULL COMMENT '养管单位',
  `road_grade` varchar(5) DEFAULT NULL COMMENT '技术等级',
  `pavement_type` varchar(45) DEFAULT NULL COMMENT '路面类型',
  `mqi_level` varchar(45) DEFAULT NULL COMMENT 'MQI分级',
  `pqi_level` varchar(45) DEFAULT NULL COMMENT 'PQI分级',
  `pci_level` varchar(45) DEFAULT NULL COMMENT 'PCI分级',
  `rqi_level` varchar(45) DEFAULT NULL COMMENT 'RQI分级',
  `pbi_level` varchar(45) DEFAULT NULL COMMENT 'PBI分级',
  `rdi_level` varchar(45) DEFAULT NULL COMMENT 'RDI分级',
  `sri_level` varchar(45) DEFAULT NULL COMMENT 'SRI分级',
  `pwi_level` varchar(45) DEFAULT NULL COMMENT 'PWI分级',
  `sci_level` varchar(45) DEFAULT NULL COMMENT 'SCI分级',
  `bci_level` varchar(45) DEFAULT NULL COMMENT 'BCI分级',
  `tci_level` varchar(45) DEFAULT NULL COMMENT 'TCI分级',
  `pssi_level` varchar(45) DEFAULT NULL COMMENT 'PSSI分级',
  `dr` decimal(10,2) DEFAULT NULL COMMENT 'DR',
  `iri` decimal(10,2) DEFAULT NULL COMMENT 'IRI',
  `rd` decimal(10,2) DEFAULT NULL COMMENT 'RD',
  `sfc` decimal(10,2) DEFAULT NULL COMMENT 'SFC',
  `pb` decimal(10,2) DEFAULT NULL COMMENT 'PB',
  `pb_l` decimal(10,2) DEFAULT NULL COMMENT 'PB_L',
  `pb_m` decimal(10,2) DEFAULT NULL COMMENT 'PB_M',
  `pb_h` decimal(10,2) DEFAULT NULL COMMENT 'PB_H',
  `wr` decimal(10,2) DEFAULT NULL COMMENT 'WR',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5473 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-29 10:53:23
