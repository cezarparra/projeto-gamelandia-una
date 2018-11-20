-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: desjavagame
-- ------------------------------------------------------
-- Server version	5.7.22-log

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `ID_CLIENTE` int(11) NOT NULL AUTO_INCREMENT,
  `NOME_CLIENTE` varchar(255) NOT NULL,
  `END_RUA_CLIENTE` varchar(255) NOT NULL,
  `END_BAIRRO_CLIENTE` varchar(255) NOT NULL,
  `END_CIDADE_CLIENTE` varchar(255) NOT NULL,
  `END_ESTADO_CLIENTE` varchar(2) NOT NULL,
  `TELEFONE_CONTATO` varchar(14) DEFAULT NULL,
  `EMAIL_CLIENTE` varchar(255) NOT NULL,
  `CPF` varchar(255) NOT NULL,
  `PASSWORD` varchar(60) DEFAULT NULL,
  `celular_cliente` varchar(14) DEFAULT NULL,
  `end_numero_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CLIENTE`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (45,'Douglas Candido','Paschoal Bruno','Santa Luzia','Uberlândia','MG','(34)3210-7401','douggycandido92@gmail.com','423.753.618-14','$2a$10$aN5piS7Zzy2a8vH1MMBEZuGqhwb8xFSXobhw5BzQVxHp26DxJYNhe','(34)99250-2362',312),(48,'João Silva','Rua Campos Gerais','Geral I','Rio de Janeiro','RJ','(21)8851-7606','unknowsname4@gmail.com','257.887.473-50','$2a$10$nqBX0.rLJUrbdy.b0M7nhO3k2z1pzUCvEhWgoEo6RD.HP9ZjmeV7u','(21)90561-8901',1532);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 22:10:06
