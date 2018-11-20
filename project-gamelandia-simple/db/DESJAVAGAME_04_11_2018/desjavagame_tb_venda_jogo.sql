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
-- Table structure for table `tb_venda_jogo`
--

DROP TABLE IF EXISTS `tb_venda_jogo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_venda_jogo` (
  `lista_carrinho_id` int(11) NOT NULL,
  `venda_jogo_id` int(11) NOT NULL,
  PRIMARY KEY (`lista_carrinho_id`,`venda_jogo_id`),
  KEY `fk_venda_jogo_id` (`venda_jogo_id`),
  KEY `fk_lista_carrinho_id` (`lista_carrinho_id`),
  CONSTRAINT `fk_lista_carrinho_id` FOREIGN KEY (`lista_carrinho_id`) REFERENCES `realiza_venda` (`ID_VENDA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_venda_jogo_id` FOREIGN KEY (`venda_jogo_id`) REFERENCES `game` (`ID_GAME`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_venda_jogo`
--

LOCK TABLES `tb_venda_jogo` WRITE;
/*!40000 ALTER TABLE `tb_venda_jogo` DISABLE KEYS */;
INSERT INTO `tb_venda_jogo` VALUES (2,2),(3,2),(4,2),(6,2),(7,2),(8,2),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(5,5),(9,22),(10,22),(11,22),(12,22),(9,34),(10,34),(11,34),(12,34),(14,34),(15,34),(13,35),(14,35),(15,35);
/*!40000 ALTER TABLE `tb_venda_jogo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 22:10:04
