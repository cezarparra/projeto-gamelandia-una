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
-- Table structure for table `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendedor` (
  `ID_VENDEDOR` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `end_rua_vendedor` varchar(255) DEFAULT NULL,
  `end_bairro_vendedor` varchar(255) DEFAULT NULL,
  `end_cidade_vendedor` varchar(255) DEFAULT NULL,
  `telefone_vendedor` varchar(255) DEFAULT NULL,
  `cpf_vendedor` varchar(255) DEFAULT NULL,
  `celular_vendedor` varchar(14) DEFAULT NULL,
  `end_numero_vendedor` int(11) DEFAULT NULL,
  `total_preco_vendida` int(11) DEFAULT NULL,
  `total_quantidade_vendida` int(11) DEFAULT NULL,
  `comissao` int(11) DEFAULT NULL,
  `salario` int(11) DEFAULT NULL,
  `porc_salario` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_VENDEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedor`
--

LOCK TABLES `vendedor` WRITE;
/*!40000 ALTER TABLE `vendedor` DISABLE KEYS */;
INSERT INTO `vendedor` VALUES (1,'Willian Mendonça','will_mendonca@gmail.com','$2a$10$VEpcbvycoq/DJlia6u8MHOI/SdNEfQDwhhj8fsGOWZJkLcLy3rnia','Av. João Naves de Ávila','Tibery','Uberlândia/MG','(34)3210-7455','525.877.878-90','(34)99200-1111',1331,159,0,39,988,15),(2,'ADMIN','admin@admin.com','$2a$10$0nLGzIgB4.IRC4gL/JFOuewo44HGUV3qwqWfZ3Nz5dbgt3nNR/PPO','Rua Paschoal Bruno','Santa Luzia','Uberlândia/MG','(34)3210-2392','423.753.618-14','(34)99250-2362',312,110,30,27,1400,25),(5,'Manuela Santos Azevedo','msa123@gmail.com','$2a$10$wgx5..yTgqr2TxVYqriH4eQJ4GRBdXEea3TEAm5SMotLI/BiMAKkG','Quadra CLN','Bloco B','Brasília','(61)3015-6150','222.660.931-85','(61)99905-0120',103,0,0,0,988,15);
/*!40000 ALTER TABLE `vendedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 22:10:05
