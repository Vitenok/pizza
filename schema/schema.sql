CREATE DATABASE  IF NOT EXISTS `pizza` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pizza`;
-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: localhost    Database: pizza
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ingredient_type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bcuaj97y3iu3t2vj26jg6hijj` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'CHEESE','Mozarella',10),(2,'CHEESE','Parmejano',10),(3,'VEGETABLES','Tomatoes',10),(4,'VEGETABLES','Olives',10),(5,'SPICES','Chili',10),(6,'MEAT','Chicken',10),(7,'MEAT','Beef',10),(8,'MEAT','Pepperoni',10),(9,'SEAFOOD','Prawns',10),(10,'SEAFOOD','Oysters',10),(11,'MEAT','Bacon',10);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza`
--

DROP TABLE IF EXISTS `pizza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_template` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` varchar(255) NOT NULL,
  `thickness` varchar(255) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jw6j03dipwu1v694xdt57wjl1` (`order_id`),
  CONSTRAINT `FK_jw6j03dipwu1v694xdt57wjl1` FOREIGN KEY (`order_id`) REFERENCES `pizza_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza`
--

LOCK TABLES `pizza` WRITE;
/*!40000 ALTER TABLE `pizza` DISABLE KEYS */;
INSERT INTO `pizza` VALUES (1,0,NULL,'BIG','THICK',NULL),(2,1,'Delicious','SMALL','THIN',NULL),(3,1,'Excellent','MEDIUM','THIN',NULL),(4,1,'Primavera','SMALL','THIN',NULL),(5,0,NULL,'SMALL','THIN',NULL);
/*!40000 ALTER TABLE `pizza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza_ingredient`
--

DROP TABLE IF EXISTS `pizza_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza_ingredient` (
  `pizzas_id` int(11) NOT NULL,
  `ingredients_id` int(11) NOT NULL,
  KEY `FK_afnfjgwr7js6gtv91p6qlgx8l` (`ingredients_id`),
  KEY `FK_ios2qqn977my27so6gylqns1o` (`pizzas_id`),
  CONSTRAINT `FK_ios2qqn977my27so6gylqns1o` FOREIGN KEY (`pizzas_id`) REFERENCES `pizza` (`id`),
  CONSTRAINT `FK_afnfjgwr7js6gtv91p6qlgx8l` FOREIGN KEY (`ingredients_id`) REFERENCES `ingredient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza_ingredient`
--

LOCK TABLES `pizza_ingredient` WRITE;
/*!40000 ALTER TABLE `pizza_ingredient` DISABLE KEYS */;
INSERT INTO `pizza_ingredient` VALUES (1,6),(1,7),(1,7),(1,8),(1,8),(1,8),(2,1),(2,1),(2,2),(2,5),(2,5),(2,5),(2,6),(2,7),(2,7),(2,8),(2,8),(2,8),(3,1),(3,1),(3,1),(3,9),(3,10),(3,10),(4,1),(4,1),(4,1),(4,2),(4,2),(4,3),(4,3),(4,3),(4,4),(4,4),(5,1),(5,1),(5,2),(5,5),(5,5),(5,5),(5,6),(5,7),(5,7),(5,8),(5,8),(5,8);
/*!40000 ALTER TABLE `pizza_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza_order`
--

DROP TABLE IF EXISTS `pizza_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `order_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza_order`
--

LOCK TABLES `pizza_order` WRITE;
/*!40000 ALTER TABLE `pizza_order` DISABLE KEYS */;
INSERT INTO `pizza_order` VALUES (1,'ololo','2013-09-07 19:52:27'),(2,'some street 1','2013-09-07 19:56:17');
/*!40000 ALTER TABLE `pizza_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza_order_pizza`
--

DROP TABLE IF EXISTS `pizza_order_pizza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza_order_pizza` (
  `pizza_order_id` int(11) NOT NULL,
  `pizzas_id` int(11) NOT NULL,
  UNIQUE KEY `UK_j17oghsmpjj9sawxqgp4s838m` (`pizzas_id`),
  KEY `FK_j17oghsmpjj9sawxqgp4s838m` (`pizzas_id`),
  KEY `FK_s9ao2lfb42ebo01o1j3ocwp69` (`pizza_order_id`),
  CONSTRAINT `FK_s9ao2lfb42ebo01o1j3ocwp69` FOREIGN KEY (`pizza_order_id`) REFERENCES `pizza_order` (`id`),
  CONSTRAINT `FK_j17oghsmpjj9sawxqgp4s838m` FOREIGN KEY (`pizzas_id`) REFERENCES `pizza` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza_order_pizza`
--

LOCK TABLES `pizza_order_pizza` WRITE;
/*!40000 ALTER TABLE `pizza_order_pizza` DISABLE KEYS */;
INSERT INTO `pizza_order_pizza` VALUES (1,1),(2,5);
/*!40000 ALTER TABLE `pizza_order_pizza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza_pizza_tag`
--

DROP TABLE IF EXISTS `pizza_pizza_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza_pizza_tag` (
  `pizzas_id` int(11) NOT NULL,
  `tags_id` int(11) NOT NULL,
  KEY `FK_dfwhnf4388xyycmnvx3tpjxuj` (`tags_id`),
  KEY `FK_l3kymi3i98syjise8f6au7vj4` (`pizzas_id`),
  CONSTRAINT `FK_l3kymi3i98syjise8f6au7vj4` FOREIGN KEY (`pizzas_id`) REFERENCES `pizza` (`id`),
  CONSTRAINT `FK_dfwhnf4388xyycmnvx3tpjxuj` FOREIGN KEY (`tags_id`) REFERENCES `pizza_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza_pizza_tag`
--

LOCK TABLES `pizza_pizza_tag` WRITE;
/*!40000 ALTER TABLE `pizza_pizza_tag` DISABLE KEYS */;
INSERT INTO `pizza_pizza_tag` VALUES (2,3),(2,1),(3,2),(4,4);
/*!40000 ALTER TABLE `pizza_pizza_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pizza_tag`
--

DROP TABLE IF EXISTS `pizza_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pizza_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gxeiundfe8ko1xrski5h7ch99` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pizza_tag`
--

LOCK TABLES `pizza_tag` WRITE;
/*!40000 ALTER TABLE `pizza_tag` DISABLE KEYS */;
INSERT INTO `pizza_tag` VALUES (3,'Meat'),(2,'Seafood'),(1,'Spicy'),(4,'Vegetarian');
/*!40000 ALTER TABLE `pizza_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-07 20:02:07
