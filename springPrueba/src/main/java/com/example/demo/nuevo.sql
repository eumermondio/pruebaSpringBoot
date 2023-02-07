DROP TABLE IF EXISTS `usuaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuaria` (
  `id` int NOT NULL,
  `fecha_elim` date DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `img` longblob,
  `nombre` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `id_tipo_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKen9mbmo5p8pq9fj9ne5v6vk0o` (`id_tipo_usuario`),
  CONSTRAINT `FKen9mbmo5p8pq9fj9ne5v6vk0o` FOREIGN KEY (`id_tipo_usuario`) REFERENCES `usuario_tipo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuaria`
--

LOCK TABLES `usuaria` WRITE;
/*!40000 ALTER TABLE `usuaria` DISABLE KEYS */;
INSERT INTO `usuaria` VALUES (1,NULL,'2003-02-03',NULL,'juana','kk','ju',1),(2,'2022-10-10','2022-10-10',_binary 'iṖ',NULL,'1234','pp',NULL),(3,'2022-10-10','2022-10-10',_binary 'iṖ','jow3','1234','2pp',NULL),(4,'2022-10-10','2022-10-10',_binary 'iṖ','paul','1234','2pp',NULL),(5,'2022-10-10','2022-10-10',_binary 'iṖ','paul','1234','2pp',NULL),(6,'2020-12-11','2020-12-11',_binary '\Ṫmø','john','12','jj',2),(7,NULL,'2020-12-11',_binary '\Ṫmø','john','12','jj',2);
/*!40000 ALTER TABLE `usuaria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_tipo`
--

DROP TABLE IF EXISTS `usuario_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_tipo` (
  `id` int NOT NULL,
  `rol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_tipo`
--

LOCK TABLES `usuario_tipo` WRITE;
/*!40000 ALTER TABLE `usuario_tipo` DISABLE KEYS */;
INSERT INTO `usuario_tipo` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `usuario_tipo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
