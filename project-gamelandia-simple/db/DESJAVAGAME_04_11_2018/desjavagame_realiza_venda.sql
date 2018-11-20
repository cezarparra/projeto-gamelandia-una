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
-- Table structure for table `realiza_venda`
--

DROP TABLE IF EXISTS `realiza_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `realiza_venda` (
  `ID_VENDA` int(11) NOT NULL AUTO_INCREMENT,
  `ID_VENDEDOR` int(11) NOT NULL,
  `ID_CLIENTE` int(11) NOT NULL,
  `QTDE_VENDIDA` int(11) NOT NULL,
  `DT_VENDA` varchar(12) DEFAULT NULL,
  `TEMPO_GARANTIA` int(11) NOT NULL,
  `PRECO_VENDA` int(11) NOT NULL,
  `FORM_PAYMENT` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_VENDA`),
  KEY `vendedor_fk` (`ID_VENDEDOR`),
  KEY `cliente_fk` (`ID_CLIENTE`),
  CONSTRAINT `cliente_fk` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `cliente` (`ID_CLIENTE`),
  CONSTRAINT `vendedor_fk` FOREIGN KEY (`ID_VENDEDOR`) REFERENCES `vendedor` (`ID_VENDEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `realiza_venda`
--

LOCK TABLES `realiza_venda` WRITE;
/*!40000 ALTER TABLE `realiza_venda` DISABLE KEYS */;
INSERT INTO `realiza_venda` VALUES (2,2,45,2,'29/10/2018',3,0,'Cartão Débito'),(3,2,45,2,'29/10/2018',3,0,'Cartão Débito'),(4,2,45,2,'29/10/2018',3,200,'Cartão Débito'),(5,2,48,2,'29/10/2018',3,318,'Cartão Débito'),(6,2,48,2,'30/10/2018',3,200,'Cartão Débito'),(7,2,48,2,'30/10/2018',3,200,'Cartão Débito'),(8,2,48,2,'30/10/2018',3,200,'Cartão Débito'),(9,2,45,2,'30/10/2018',3,318,'Dinheiro'),(10,2,45,2,'30/10/2018',3,318,'Cartão Débito'),(11,2,45,2,'30/10/2018',3,318,'Cartão Débito'),(12,2,45,2,'30/10/2018',3,318,'Cartão Débito'),(13,2,45,2,'04/11/2018',3,500,'Cartão Débito'),(14,2,45,2,'04/11/2018',6,250,'Cartão Débito'),(15,2,45,2,'04/11/2018',6,360,'Cartão Débito');
/*!40000 ALTER TABLE `realiza_venda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 22:10:08
