-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lvtn
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dan_toc`
--

DROP TABLE IF EXISTS `dan_toc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dan_toc` (
  `MA_DT` int NOT NULL,
  `TEN_DT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`MA_DT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dan_toc`
--

LOCK TABLES `dan_toc` WRITE;
/*!40000 ALTER TABLE `dan_toc` DISABLE KEYS */;
INSERT INTO `dan_toc` VALUES (1,'Kinh'),(2,'Tày'),(3,'Thái'),(4,'Hoa'),(5,'Khơ-me'),(6,'Mường'),(7,'Nùng'),(8,'HMông'),(9,'Dao'),(10,'Gia-rai'),(11,'Ngái'),(12,'Ê-đê'),(13,'Ba na'),(14,'Xơ-Đăng'),(15,'Sán Chay'),(16,'Cơ-ho'),(17,'Chăm'),(18,'Sán Dìu'),(19,'Hrê'),(20,'Mnông'),(21,'Ra-glai'),(22,'Xtiêng'),(23,'Bru-Vân Kiều'),(24,'Thổ'),(25,'Giáy'),(26,'Cơ-tu'),(27,'Gié Triêng'),(28,'Mạ'),(29,'Khơ-mú'),(30,'Co'),(31,'Tà-ôi'),(32,'Chơ-ro'),(33,'Kháng'),(34,'Xinh-mun'),(35,'Hà Nhì'),(36,'Chu ru'),(37,'Lào'),(38,'La Chí'),(39,'La Ha'),(40,'Phù Lá'),(41,'La Hủ'),(42,'Lự'),(43,'Lô Lô'),(44,'Chứt'),(45,'Mảng'),(46,'Pà Thẻn'),(47,'Co Lao'),(48,'Cống'),(49,'Bố Y'),(50,'Si La'),(51,'Pu Péo'),(52,'Brâu'),(53,'Ơ Đu'),(54,'Rơ măm');
/*!40000 ALTER TABLE `dan_toc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtkk`
--

DROP TABLE IF EXISTS `dtkk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dtkk` (
  `MA_DTKK` int NOT NULL,
  `TEN_DTKK` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `DIEM_CONG` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`MA_DTKK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtkk`
--

LOCK TABLES `dtkk` WRITE;
/*!40000 ALTER TABLE `dtkk` DISABLE KEYS */;
INSERT INTO `dtkk` VALUES (0,'Không',0.00),(101,'Giải Nhất cấp TP cuộc thi do Sở GD&ĐT tổ chức hoặc phối hợp với các sở, ngành TP có liên quan về văn hóa, văn nghệ, thể thao, cuộc thi nghiên cứu KHKT',1.50),(102,'Giải Nhì cấp TP cuộc thi do Sở GD&ĐT tổ chức hoặc phối hợp với các sở, ngành TP có liên quan về văn hóa, văn nghệ, thể thao, cuộc thi nghiên cứu KHKT',1.00),(103,'Giải Ba cấp TP cuộc thi do Sở GD&ĐT tổ chức hoặc phối hợp với các sở, ngành TP có liên quan về văn hóa, văn nghệ, thể thao, cuộc thi nghiên cứu KHKT',0.50);
/*!40000 ALTER TABLE `dtkk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtkk_chuyen`
--

DROP TABLE IF EXISTS `dtkk_chuyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dtkk_chuyen` (
  `MA_DTKK_CHUYEN` int NOT NULL,
  `TEN_DTKK_CHUYEN` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `DIEM_CONG` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`MA_DTKK_CHUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtkk_chuyen`
--

LOCK TABLES `dtkk_chuyen` WRITE;
/*!40000 ALTER TABLE `dtkk_chuyen` DISABLE KEYS */;
INSERT INTO `dtkk_chuyen` VALUES (0,'Không',0.00),(101,'Giải Nhất kỳ thi chọn HSG THCS cấp tỉnh, thành phố',1.50),(102,'Giải Nhì kỳ thi chọn HSG THCS cấp tỉnh, thành phố',1.00),(103,'Giải Ba kỳ thi chọn HSG THCS cấp tỉnh, thành phố',0.50);
/*!40000 ALTER TABLE `dtkk_chuyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtut`
--

DROP TABLE IF EXISTS `dtut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dtut` (
  `MA_DTUT` int NOT NULL,
  `TEN_DTUT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `DIEM_CONG` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`MA_DTUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtut`
--

LOCK TABLES `dtut` WRITE;
/*!40000 ALTER TABLE `dtut` DISABLE KEYS */;
INSERT INTO `dtut` VALUES (0,'Không',0.00),(101,'Con của liệt sĩ',2.00),(102,'Con của thương binh mất sức lao động 81% trở lên',2.00),(103,'Con của bệnh binh mất sức lao động 81% trở lên',2.00),(104,'Con của người được cấp \"GCN người hưởng chính sách như thương binh mà người được cấp GCN người hưởng chính sách như thương binh bị suy giảm khả năng lao động 81% trở lên\"',2.00),(105,'Con của người hoạt động kháng chiến bị nhiễm chất độc hóa học',2.00),(106,'Con của người hoạt động cách mạng trước ngày 01/01/1945',2.00),(107,'Con của người hoạt động cách mạng từ ngày 01/01/1945 đến ngày khởi nghĩa tháng 8 năm 1945',2.00),(201,'Con của AHLLVT, con của AHLĐ, con của Bà mẹ Việt Nam anh hùng',1.50),(202,'Con của thương binh mất sức lao động dưới 81%',1.50),(203,'Con của bệnh binh mất sức lao động dưới 81%',1.50),(204,'Con của người được cấp \"GCN người hưởng chính sách như thương binh mà người được cấp GCN người hưởng chính sách như thương binh bị suy giảm khả năng lao động dưới 81%\"',1.50),(301,'Người có cha hoặc mẹ là người dân tộc thiểu số',1.00),(302,'Người dân tộc thiểu số',1.00),(303,'Người học đang sinh sống, học tập ở các vùng có điều kiện kinh tế - xã hội đặc biệt khó khăn',1.00);
/*!40000 ALTER TABLE `dtut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoc_sinh`
--

DROP TABLE IF EXISTS `hoc_sinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoc_sinh` (
  `MA_HS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `HO_VA_CHU_LOT_HS` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `TEN_HS` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `GIOI_TINH` tinyint(1) DEFAULT NULL,
  `NGAY_SINH` date DEFAULT NULL,
  `NOI_SINH` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MA_DT` int DEFAULT '1',
  `DIA_CHI_THUONG_TRU` varchar(2048) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `CHO_O_HIEN_NAY` varchar(2048) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MA_THCS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `NAM_TOT_NGHIEP_THCS` int DEFAULT '2026',
  `MA_DTUT` int DEFAULT '0',
  `MA_DTKK` int DEFAULT '0',
  `MA_DTKK_CHUYEN` int DEFAULT '0',
  `NGOAI_NGU_DANG_HOC` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `NGOAI_NGU_DU_THI` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `SO_DIEN_THOAI` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `DIEM_TB_MON_CHUYEN` decimal(5,2) DEFAULT NULL,
  `TONG_DIEM_TB_LOP_9` decimal(5,2) DEFAULT NULL,
  `ANH_DAI_DIEN` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `TEN_THCS_NGOAI_TPCT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `TEN_XA_NGOAI_TPCT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `TEN_TINH_NGOAI_TPCT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`MA_HS`),
  KEY `hoc_sinh_ibfk_2` (`MA_THCS`),
  KEY `hoc_sinh_ibfk_1` (`MA_DT`),
  KEY `hoc_sinh_ibfk_3` (`MA_DTUT`),
  KEY `hoc_sinh_ibfk_4` (`MA_DTKK`),
  KEY `hoc_sinh_ibfk_5` (`MA_DTKK_CHUYEN`),
  CONSTRAINT `hoc_sinh_ibfk_1` FOREIGN KEY (`MA_DT`) REFERENCES `dan_toc` (`MA_DT`),
  CONSTRAINT `hoc_sinh_ibfk_2` FOREIGN KEY (`MA_THCS`) REFERENCES `truong_thcs` (`MA_THCS`),
  CONSTRAINT `hoc_sinh_ibfk_3` FOREIGN KEY (`MA_DTUT`) REFERENCES `dtut` (`MA_DTUT`),
  CONSTRAINT `hoc_sinh_ibfk_4` FOREIGN KEY (`MA_DTKK`) REFERENCES `dtkk` (`MA_DTKK`),
  CONSTRAINT `hoc_sinh_ibfk_5` FOREIGN KEY (`MA_DTKK_CHUYEN`) REFERENCES `dtkk_chuyen` (`MA_DTKK_CHUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoc_sinh`
--

LOCK TABLES `hoc_sinh` WRITE;
/*!40000 ALTER TABLE `hoc_sinh` DISABLE KEYS */;
INSERT INTO `hoc_sinh` VALUES ('0921','Lê Thị Ngọc','Hạnh',1,'2011-07-11','Cần Thơ',1,'35 Trần Hưng Đạo, P.Ninh Kiều, TPCT','35 Trần Hưng Đạo, P.Ninh Kiều, TPCT','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','0927555198',9.20,101.10,NULL,NULL,NULL,NULL),('092211392345','Phan Tuấn','Tài',0,'2011-12-07','Đồng Tháp',1,'Phường Cao Lãnh','Phường Cao Lãnh','0',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.50,98.90,'092211392345.jpg','THCS Nguyễn Chí Thanh','Phường Cao Lãnh','Tỉnh Đồng Tháp'),('093211002011','Châu Thanh','Quang',0,'2011-11-17','Cà Mau',5,'Phường Bạc Liêu, Tỉnh Cà Mau','Phường Bạc Liêu, Tỉnh Cà Mau','0',2026,302,0,103,'Tiếng Anh','Tiếng Anh','',9.50,100.10,'093211002011.jpg','THCS Trần Huỳnh','Phường Bạc Liêu','Tỉnh Cà Mau'),('093211002012','Dương Thanh','Tùng',0,'2011-06-07','An Giang',1,'An Giang','An Giang','0',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.90,100.00,'093211002012.jpg','THCS Hùng Vương','Phường Long Xuyên','Tỉnh An Giang'),('093211002013','Trần','Hùng',0,'2011-03-01','An Giang',1,'An Giang','An Giang','0',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.20,100.10,NULL,'THCS Ngô Quyền','Phường Rạch Giá','Tỉnh An Giang'),('0944','Nguyễn Hoàng Thanh','Tùng',0,'2011-02-15','Cần Thơ',4,'57A Nguyễn Thần Hiến, P.Ninh Kiều, TPCT','57A Nguyễn Thần Hiến, P.Ninh Kiều, TPCT','92916507',2026,0,0,101,'Tiếng Anh','Tiếng Anh',NULL,9.80,99.30,NULL,NULL,NULL,NULL),('099323451001','Hoàng Hữu','Nam',0,'2011-01-10','Cà Mau',1,'Phường An Xuyên, Tỉnh Cà Mau','Phường An Xuyên, Tỉnh Cà Mau','0',2026,0,0,0,'Tiếng Anh','Tiếng Anh','0919220331',9.40,98.40,NULL,'THCS Phan Bội Châu','Phường Tân Thành','Tỉnh Cà Mau'),('HSNT092345','Lâm Tuấn','Khanh',0,'2011-11-09','Cần Thơ',1,'Phường Bình Đức, tỉnh An Giang','Phường Bình Đức, tỉnh An Giang','0',2026,0,0,102,'Tiếng Anh','Tiếng Anh','0899123321',9.70,100.10,NULL,'THCS Bình Đức','Phường Bình Đức','Tỉnh An Giang'),('HSNT373200','Nguyễn Thị Ngọc','Trâm',1,'2011-05-07','Cần Thơ',1,'Phường Bình Minh, tỉnh Vĩnh Long','Phường Bình Minh, tỉnh Vĩnh Long','0',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.10,99.90,NULL,'THCS Bình Minh','Phường Bình Minh','Tỉnh Vĩnh Long'),('M01','Nguyễn Văn','An',0,'2011-01-01','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','0912121212',9.50,102.50,'M01.jpg',NULL,NULL,NULL),('M02','Nguyễn Thị','Bình',1,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,103,'Tiếng Anh','Tiếng Anh',NULL,9.00,101.00,NULL,NULL,NULL,NULL),('M03','Nguyễn Văn','Cường',0,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,301,0,101,'Tiếng Anh','Tiếng Anh',NULL,9.30,97.60,NULL,NULL,NULL,NULL),('M04','Nguyễn Văn','Dũng',0,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,NULL,99.40,NULL,NULL,NULL,NULL),('M05','Nguyễn Thị Trúc','Em',1,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,201,102,102,'Tiếng Anh','Tiếng Anh',NULL,7.00,101.20,NULL,NULL,NULL,NULL),('M06','Nguyễn Văn','Giang',0,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,9.10,94.40,NULL,NULL,NULL,NULL),('M07','Nguyễn Văn','Hùng',0,'2011-01-01','Cần Thơ',1,NULL,NULL,'92916507',2026,101,0,0,'Tiếng Anh','Tiếng Anh',NULL,NULL,100.00,NULL,NULL,NULL,NULL),('M10','Lê Văn','Thanh',0,'2011-10-12','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Pháp','Tiếng Pháp','',9.10,100.00,'M10.jpg',NULL,NULL,NULL),('M101','Phan Văn','Long',0,'2011-01-10','Cần Thơ',1,'P. Ninh Kiều, TPCT','P. Ninh Kiều, TPCT','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',NULL,109.50,NULL,NULL,NULL,NULL),('M102','Trần Văn','Long',0,'2011-11-27','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',NULL,100.00,NULL,NULL,NULL,NULL),('M11','Lê Đăng','Quang',0,'2011-02-17','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,9.20,100.10,'M11.jpg',NULL,NULL,NULL),('M110','Trương Quốc','Dũng',0,'2011-02-09','Cần Thơ',NULL,NULL,NULL,'92916507',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('M12','Phạm Thị Thanh','Trà',1,'2011-08-31','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,9.00,98.20,'M12.jpg',NULL,NULL,NULL),('M20','Trần Thị','Thanh',0,'2011-02-02','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','0927100229',9.20,98.00,NULL,NULL,NULL,NULL),('M51','Phạm Thị Thanh','Trà',1,'2011-11-17','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.40,96.70,NULL,NULL,NULL,NULL),('M52','Trần Thị Thu','Sương',1,'2011-07-05','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh','',9.60,99.00,NULL,NULL,NULL,NULL),('M55','Nguyễn Văn','Khang',0,'2011-11-17','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,103,'Tiếng Anh','Tiếng Anh','',9.30,98.50,'M55.jpg',NULL,NULL,NULL),('M559','Ngô','Quyền',0,'2011-10-20','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,103,'Tiếng Anh','Tiếng Anh','0933888111',9.90,108.00,'M559.jpg',NULL,NULL,NULL),('M81','Trần Ngọc','Tiến',0,'2011-03-05','Cần Thơ',1,'P. Ninh Kiều','P. Ninh Kiều','92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,9.00,9.50,NULL,NULL,NULL,NULL),('M82','Nguyễn Thị Lộc','An',1,'2011-09-07','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,103,'Tiếng Anh','Tiếng Anh',NULL,9.30,100.00,NULL,NULL,NULL,NULL),('M83','Nguyễn Văn','Dương',0,'2011-02-03','Cần Thơ',1,NULL,NULL,'92916507',2026,301,0,101,'Tiếng Anh','Tiếng Anh',NULL,9.20,98.90,NULL,NULL,NULL,NULL),('M84','Phan Văn','Tài',0,'2011-01-11','Cần Thơ',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,NULL,97.70,NULL,NULL,NULL,NULL),('M85','Lê Thị','Minh',1,'2011-02-23','Cần Thơ',1,NULL,NULL,'92916507',2026,201,102,102,'Tiếng Anh','Tiếng Anh',NULL,9.20,95.00,NULL,NULL,NULL,NULL),('M86','Nguyễn Trung','Giang',0,'2011-12-13','Đồng Tháp',1,NULL,NULL,'92916507',2026,0,0,0,'Tiếng Anh','Tiếng Anh',NULL,9.10,95.00,NULL,NULL,NULL,NULL),('M87','Hoàng Ngọc','Thuận',0,'2011-07-01','Cần Thơ',1,NULL,NULL,'92916507',2026,101,0,0,'Tiếng Anh','Tiếng Anh',NULL,NULL,99.20,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `hoc_sinh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ket_qua_thi`
--

DROP TABLE IF EXISTS `ket_qua_thi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ket_qua_thi` (
  `MA_HS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `DIEM_TOAN` decimal(5,2) DEFAULT NULL,
  `DIEM_VAN` decimal(5,2) DEFAULT NULL,
  `DIEM_MON_THU_3` decimal(5,2) DEFAULT NULL,
  `DIEM_MON_CHUYEN` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`MA_HS`),
  CONSTRAINT `ket_qua_thi_ibfk_1` FOREIGN KEY (`MA_HS`) REFERENCES `hoc_sinh` (`MA_HS`) ON DELETE CASCADE,
  CONSTRAINT `ket_qua_thi_chk_1` CHECK ((`DIEM_TOAN` between 0.00 and 10.00)),
  CONSTRAINT `ket_qua_thi_chk_2` CHECK ((`DIEM_VAN` between 0.00 and 10.00)),
  CONSTRAINT `ket_qua_thi_chk_3` CHECK ((`DIEM_MON_THU_3` between 0.00 and 10.00)),
  CONSTRAINT `ket_qua_thi_chk_4` CHECK ((`DIEM_MON_CHUYEN` between 0.00 and 10.00))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ket_qua_thi`
--

LOCK TABLES `ket_qua_thi` WRITE;
/*!40000 ALTER TABLE `ket_qua_thi` DISABLE KEYS */;
INSERT INTO `ket_qua_thi` VALUES ('0921',7.25,6.75,8.25,7.25),('092211392345',10.00,7.00,9.75,10.00),('093211002011',10.00,6.75,9.75,10.00),('093211002012',NULL,NULL,NULL,NULL),('093211002013',9.25,7.50,9.25,9.25),('0944',NULL,NULL,NULL,NULL),('099323451001',8.25,5.50,9.25,8.25),('M01',8.75,9.00,5.00,8.75),('M02',8.50,7.50,9.25,8.50),('M03',9.50,8.00,9.25,9.50),('M04',9.00,9.25,8.00,NULL),('M05',10.00,8.50,9.25,10.00),('M06',8.75,8.50,6.00,8.75),('M07',9.50,9.75,8.75,NULL),('M10',9.50,5.75,9.50,9.50),('M11',9.50,7.25,9.25,9.50),('M12',8.25,6.50,9.00,8.25),('M20',9.00,7.25,9.25,9.00),('M51',NULL,NULL,NULL,NULL),('M52',5.50,6.25,9.25,5.50),('M55',7.75,7.50,9.25,7.75),('M81',9.75,8.00,9.25,9.75),('M82',7.00,6.75,7.50,7.00),('M83',8.25,7.75,7.25,8.25),('M84',9.00,8.00,9.00,NULL),('M85',9.50,6.00,10.00,9.50),('M86',8.50,7.50,6.50,8.50),('M87',9.25,8.00,9.00,NULL);
/*!40000 ALTER TABLE `ket_qua_thi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kq_hoc_tap`
--

DROP TABLE IF EXISTS `kq_hoc_tap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kq_hoc_tap` (
  `MA_HS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `LOP` int NOT NULL,
  `HOC_TAP` varchar(50) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `REN_LUYEN` varchar(50) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`MA_HS`,`LOP`),
  CONSTRAINT `kq_hoc_tap_ibfk_1` FOREIGN KEY (`MA_HS`) REFERENCES `hoc_sinh` (`MA_HS`) ON DELETE CASCADE,
  CONSTRAINT `kq_hoc_tap_chk_1` CHECK ((`LOP` between 6 and 9))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kq_hoc_tap`
--

LOCK TABLES `kq_hoc_tap` WRITE;
/*!40000 ALTER TABLE `kq_hoc_tap` DISABLE KEYS */;
INSERT INTO `kq_hoc_tap` VALUES ('0921',6,'Tốt','Tốt'),('0921',7,'Tốt','Tốt'),('0921',8,'Tốt','Tốt'),('0921',9,'Tốt','Tốt'),('092211392345',6,'Tốt','Tốt'),('092211392345',7,'Tốt','Tốt'),('092211392345',8,'Tốt','Tốt'),('092211392345',9,'Tốt','Tốt'),('093211002011',6,'Tốt','Tốt'),('093211002011',7,'Tốt','Tốt'),('093211002011',8,'Tốt','Tốt'),('093211002011',9,'Tốt','Tốt'),('093211002012',6,'Tốt','Tốt'),('093211002012',7,'Tốt','Tốt'),('093211002012',8,'Tốt','Tốt'),('093211002012',9,'Tốt','Tốt'),('093211002013',6,'Tốt','Tốt'),('093211002013',7,'Tốt','Tốt'),('093211002013',8,'Tốt','Tốt'),('093211002013',9,'Tốt','Tốt'),('0944',6,'Tốt','Tốt'),('0944',7,'Tốt','Tốt'),('0944',8,'Tốt','Tốt'),('0944',9,'Tốt','Tốt'),('099323451001',6,'Tốt','Tốt'),('099323451001',7,'Tốt','Tốt'),('099323451001',8,'Tốt','Tốt'),('099323451001',9,'Tốt','Tốt'),('HSNT092345',6,'Tốt','Tốt'),('HSNT092345',7,'Tốt','Tốt'),('HSNT092345',8,'Tốt','Tốt'),('HSNT092345',9,'Tốt','Tốt'),('HSNT373200',6,'Tốt','Tốt'),('HSNT373200',7,'Tốt','Tốt'),('HSNT373200',8,'Tốt','Tốt'),('HSNT373200',9,'Tốt','Tốt'),('M01',6,'Tốt','Tốt'),('M01',7,'Tốt','Tốt'),('M01',8,'Tốt','Tốt'),('M01',9,'Tốt','Tốt'),('M02',6,'Tốt','Tốt'),('M02',7,'Tốt','Tốt'),('M02',8,'Tốt','Tốt'),('M02',9,'Tốt','Tốt'),('M03',6,'Tốt','Tốt'),('M03',7,'Tốt','Tốt'),('M03',8,'Tốt','Tốt'),('M03',9,'Tốt','Tốt'),('M04',6,'Tốt','Tốt'),('M04',7,'Tốt','Tốt'),('M04',8,'Tốt','Tốt'),('M04',9,'Tốt','Tốt'),('M05',6,'Tốt','Tốt'),('M05',7,'Tốt','Tốt'),('M05',8,'Tốt','Tốt'),('M05',9,'Tốt','Tốt'),('M06',6,'Tốt','Tốt'),('M06',7,'Tốt','Tốt'),('M06',8,'Tốt','Tốt'),('M06',9,'Tốt','Tốt'),('M07',6,'Tốt','Tốt'),('M07',7,'Tốt','Tốt'),('M07',8,'Tốt','Tốt'),('M07',9,'Tốt','Tốt'),('M10',6,'Tốt','Tốt'),('M10',7,'Tốt','Tốt'),('M10',8,'Tốt','Tốt'),('M10',9,'Tốt','Tốt'),('M101',6,'Tốt','Tốt'),('M101',7,'Tốt','Tốt'),('M101',8,'Tốt','Tốt'),('M101',9,'Tốt','Tốt'),('M102',6,'Tốt','Tốt'),('M102',7,'Tốt','Tốt'),('M102',8,'Tốt','Tốt'),('M102',9,'Tốt','Tốt'),('M11',6,'Tốt','Tốt'),('M11',7,'Tốt','Tốt'),('M11',8,'Tốt','Tốt'),('M11',9,'Tốt','Tốt'),('M12',6,'Tốt','Tốt'),('M12',7,'Tốt','Tốt'),('M12',8,'Tốt','Tốt'),('M12',9,'Tốt','Tốt'),('M20',6,'Tốt','Tốt'),('M20',7,'Tốt','Tốt'),('M20',8,'Tốt','Tốt'),('M20',9,'Tốt','Tốt'),('M51',6,'Tốt','Tốt'),('M51',7,'Tốt','Tốt'),('M51',8,'Tốt','Tốt'),('M51',9,'Tốt','Tốt'),('M52',6,'Tốt','Tốt'),('M52',7,'Tốt','Tốt'),('M52',8,'Tốt','Tốt'),('M52',9,'Tốt','Tốt'),('M55',6,'Tốt','Tốt'),('M55',7,'Tốt','Tốt'),('M55',8,'Tốt','Tốt'),('M55',9,'Tốt','Tốt'),('M559',6,'Tốt','Tốt'),('M559',7,'Tốt','Tốt'),('M559',8,'Tốt','Tốt'),('M559',9,'Tốt','Tốt'),('M81',6,'Tốt','Tốt'),('M81',7,'Tốt','Tốt'),('M81',8,'Tốt','Tốt'),('M81',9,'Tốt','Tốt'),('M82',6,'Giỏi','Tốt'),('M82',7,'Giỏi','Tốt'),('M82',8,'Giỏi','Tốt'),('M82',9,'Giỏi','Tốt'),('M83',6,'Giỏi','Tốt'),('M83',7,'Giỏi','Tốt'),('M83',8,'Giỏi','Tốt'),('M83',9,'Giỏi','Tốt'),('M84',6,'Giỏi','Tốt'),('M84',7,'Giỏi','Tốt'),('M84',8,'Giỏi','Tốt'),('M84',9,'Giỏi','Tốt'),('M85',6,'Giỏi','Tốt'),('M85',7,'Giỏi','Tốt'),('M85',8,'Giỏi','Tốt'),('M85',9,'Giỏi','Tốt'),('M86',6,'Giỏi','Tốt'),('M86',7,'Giỏi','Tốt'),('M86',8,'Giỏi','Tốt'),('M86',9,'Giỏi','Tốt'),('M87',6,'Giỏi','Tốt'),('M87',7,'Giỏi','Tốt'),('M87',8,'Giỏi','Tốt'),('M87',9,'Giỏi','Tốt');
/*!40000 ALTER TABLE `kq_hoc_tap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_tk`
--

DROP TABLE IF EXISTS `loai_tk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_tk` (
  `MA_LOAI_TK` int NOT NULL,
  `TEN_LOAI_TK` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`MA_LOAI_TK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_tk`
--

LOCK TABLES `loai_tk` WRITE;
/*!40000 ALTER TABLE `loai_tk` DISABLE KEYS */;
INSERT INTO `loai_tk` VALUES (0,'Quản trị viên'),(1,'Hội đồng tuyển sinh'),(2,'Giáo viên THCS'),(3,'Giáo viên THPT'),(4,'Học sinh');
/*!40000 ALTER TABLE `loai_tk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lop_chuyen`
--

DROP TABLE IF EXISTS `lop_chuyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lop_chuyen` (
  `MA_LOP_CHUYEN` varchar(5) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `TEN_LOP_CHUYEN` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MON_CHUYEN` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`MA_LOP_CHUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lop_chuyen`
--

LOCK TABLES `lop_chuyen` WRITE;
/*!40000 ALTER TABLE `lop_chuyen` DISABLE KEYS */;
INSERT INTO `lop_chuyen` VALUES ('01','Chuyên Toán','Toán'),('02','Chuyên Tin học','Tin học'),('03','Chuyên Vật lý','Khoa học Tự nhiên 1'),('04','Chuyên Hóa học','Khoa học Tự nhiên 2'),('05','Chuyên Sinh học','Khoa học Tự nhiên 3'),('06','Chuyên Ngữ văn','Ngữ văn'),('07','Chuyên Lịch sử','Lịch sử và Địa lý 1'),('08','Chuyên Địa lý','Lịch sử và Địa lý 2'),('09','Chuyên Tiếng Anh','Tiếng Anh'),('10','Chuyên Tiếng Pháp','Tiếng Pháp');
/*!40000 ALTER TABLE `lop_chuyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lop_chuyen_thpt`
--

DROP TABLE IF EXISTS `lop_chuyen_thpt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lop_chuyen_thpt` (
  `MA_THPT` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MA_LOP_CHUYEN` varchar(5) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `CHI_TIEU` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`MA_THPT`,`MA_LOP_CHUYEN`),
  KEY `MA_LOP_CHUYEN` (`MA_LOP_CHUYEN`),
  CONSTRAINT `lop_chuyen_thpt_ibfk_1` FOREIGN KEY (`MA_THPT`) REFERENCES `truong_thpt` (`MA_THPT`) ON DELETE CASCADE,
  CONSTRAINT `lop_chuyen_thpt_ibfk_2` FOREIGN KEY (`MA_LOP_CHUYEN`) REFERENCES `lop_chuyen` (`MA_LOP_CHUYEN`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lop_chuyen_thpt`
--

LOCK TABLES `lop_chuyen_thpt` WRITE;
/*!40000 ALTER TABLE `lop_chuyen_thpt` DISABLE KEYS */;
INSERT INTO `lop_chuyen_thpt` VALUES ('92000F01','01',70),('92000F01','02',35),('92000F01','03',35),('92000F01','04',35),('92000F01','05',35),('92000F01','06',35),('92000F01','07',35),('92000F01','08',35),('92000F01','09',70),('92000F01','10',35),('93000F16','01',35),('93000F16','02',35),('93000F16','03',35),('93000F16','04',35),('93000F16','05',35),('93000F16','06',35),('93000F16','07',35),('93000F16','08',35),('93000F16','09',35),('94000706','01',70),('94000706','02',35),('94000706','03',35),('94000706','04',35),('94000706','05',35),('94000706','06',35),('94000706','07',18),('94000706','08',17),('94000706','09',70);
/*!40000 ALTER TABLE `lop_chuyen_thpt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguyen_vong`
--

DROP TABLE IF EXISTS `nguyen_vong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguyen_vong` (
  `MA_HS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MA_THPT` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `THU_TU` int NOT NULL,
  `NV_2B` tinyint(1) DEFAULT '0',
  `LOP_TIENG_PHAP` tinyint(1) DEFAULT '0',
  `MA_LOP_CHUYEN` varchar(5) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `KET_QUA` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`MA_HS`,`THU_TU`),
  UNIQUE KEY `MA_HS` (`MA_HS`,`MA_THPT`),
  KEY `MA_LOP_CHUYEN` (`MA_LOP_CHUYEN`),
  KEY `nguyen_vong_ibfk_2` (`MA_THPT`),
  CONSTRAINT `nguyen_vong_ibfk_1` FOREIGN KEY (`MA_HS`) REFERENCES `hoc_sinh` (`MA_HS`) ON DELETE CASCADE,
  CONSTRAINT `nguyen_vong_ibfk_2` FOREIGN KEY (`MA_THPT`) REFERENCES `truong_thpt` (`MA_THPT`),
  CONSTRAINT `nguyen_vong_ibfk_3` FOREIGN KEY (`MA_LOP_CHUYEN`) REFERENCES `lop_chuyen` (`MA_LOP_CHUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguyen_vong`
--

LOCK TABLES `nguyen_vong` WRITE;
/*!40000 ALTER TABLE `nguyen_vong` DISABLE KEYS */;
INSERT INTO `nguyen_vong` VALUES ('0921','92000F01',1,0,0,'01','Đậu'),('0921','92000701',2,0,0,NULL,NULL),('0921','92000705',3,0,0,NULL,NULL),('092211392345','92000F01',1,0,0,'05','Đậu'),('093211002011','92000F01',1,0,0,'03','Đậu'),('093211002012','92000F01',1,0,0,'01','Hỏng'),('093211002013','92000F01',1,0,0,'01','Đậu'),('093211002013','92000727',2,0,0,NULL,NULL),('0944','92000F01',1,0,0,'03','Hỏng'),('0944','92000727',2,0,0,NULL,'Hỏng'),('099323451001','92000F01',1,0,0,'02','Đậu'),('099323451001','92000727',2,0,0,NULL,NULL),('HSNT092345','92000F01',1,0,0,'01',NULL),('HSNT092345','92000727',2,0,0,NULL,NULL),('HSNT373200','92000F01',1,0,0,'06',NULL),('M01','94000706',1,0,0,'01',NULL),('M01','92000701',2,1,0,NULL,NULL),('M01','92000728',3,0,0,NULL,NULL),('M01','92000709',4,0,0,NULL,NULL),('M01','92000723',5,0,0,NULL,NULL),('M02','92000F01',1,0,0,'03','Đậu'),('M02','92000701',2,0,0,NULL,NULL),('M02','92000705',3,0,0,NULL,NULL),('M02','92000728',4,0,0,NULL,NULL),('M02','94000701',5,0,0,NULL,NULL),('M03','93000F16',1,0,0,'07','Đậu'),('M03','92000705',2,0,0,NULL,NULL),('M03','92000701',3,0,0,NULL,NULL),('M03','92000728',4,0,0,NULL,NULL),('M04','92000701',2,0,0,NULL,'Đậu'),('M04','94000701',3,0,0,NULL,NULL),('M05','92000F01',1,0,0,'09','Đậu'),('M05','92000705',2,0,0,NULL,NULL),('M05','92000701',3,0,0,NULL,NULL),('M05','94000701',4,0,0,NULL,NULL),('M06','94000706',1,0,0,'09','Đậu'),('M06','92000728',2,0,0,NULL,NULL),('M06','92000705',3,0,0,NULL,NULL),('M07','92000701',2,0,0,NULL,'Đậu'),('M07','92000728',3,0,0,NULL,NULL),('M07','94000701',4,0,0,NULL,NULL),('M10','92000F01',1,0,0,'10','Đậu'),('M10','92000701',2,0,0,NULL,NULL),('M10','92000705',3,0,0,NULL,NULL),('M101','92000701',2,0,0,NULL,NULL),('M101','92000702',3,0,0,NULL,NULL),('M102','92000701',2,0,1,NULL,NULL),('M102','92000705',3,0,0,NULL,NULL),('M11','92000F01',1,0,0,'06','Đậu'),('M11','92000701',2,0,0,NULL,NULL),('M11','92000728',3,0,0,NULL,NULL),('M11','92000705',4,0,0,NULL,NULL),('M12','92000F01',1,0,0,'03','Đậu'),('M12','92000701',2,0,0,NULL,NULL),('M12','92000705',3,0,0,NULL,NULL),('M12','92000728',4,0,0,NULL,NULL),('M12','94000701',5,0,0,NULL,NULL),('M20','92000F01',1,0,0,'01','Đậu'),('M20','92000701',2,0,0,NULL,NULL),('M51','92000F01',1,0,0,'01','Hỏng'),('M51','92000714',2,0,0,NULL,'Hỏng'),('M52','92000F01',1,0,0,'03','Đậu'),('M52','92000705',2,0,0,NULL,NULL),('M55','92000F01',1,0,0,'04','Đậu'),('M55','92000727',2,0,0,NULL,NULL),('M55','92000705',3,0,0,NULL,NULL),('M559','92000F01',1,0,0,'04',NULL),('M559','92000701',2,0,1,NULL,NULL),('M559','92000709',3,0,0,NULL,NULL),('M81','92000F01',1,0,0,'05','Đậu'),('M81','92000701',2,0,0,NULL,NULL),('M81','92000728',3,0,0,NULL,NULL),('M81','92000705',4,0,0,NULL,NULL),('M82','92000F01',1,0,0,'02','Đậu'),('M82','92000701',2,0,0,NULL,NULL),('M82','92000705',3,0,0,NULL,NULL),('M82','92000728',4,0,0,NULL,NULL),('M82','94000701',5,0,0,NULL,NULL),('M83','93000F16',1,0,0,'03','Đậu'),('M83','92000705',2,0,0,NULL,NULL),('M83','92000701',3,0,0,NULL,NULL),('M83','92000728',4,0,0,NULL,NULL),('M84','92000701',2,0,0,NULL,'Đậu'),('M84','94000701',3,0,0,NULL,NULL),('M85','92000F01',1,0,0,'07','Đậu'),('M85','92000705',2,0,0,NULL,NULL),('M85','92000701',3,0,0,NULL,NULL),('M85','94000701',4,0,0,NULL,NULL),('M86','94000706',1,0,0,'08','Đậu'),('M86','92000728',2,0,0,NULL,NULL),('M86','92000705',3,0,0,NULL,NULL),('M87','92000701',2,0,0,NULL,'Đậu'),('M87','92000728',3,0,0,NULL,NULL),('M87','94000701',4,0,0,NULL,NULL);
/*!40000 ALTER TABLE `nguyen_vong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phuong_xa`
--

DROP TABLE IF EXISTS `phuong_xa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phuong_xa` (
  `MA_PHUONG_XA` int NOT NULL,
  `TEN_PHUONG_XA` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  PRIMARY KEY (`MA_PHUONG_XA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phuong_xa`
--

LOCK TABLES `phuong_xa` WRITE;
/*!40000 ALTER TABLE `phuong_xa` DISABLE KEYS */;
INSERT INTO `phuong_xa` VALUES (0,'Ngoài TP Cần Thơ'),(31120,'Phường Cái Khế'),(31135,'Phường Ninh Kiều'),(31147,'Phường Tân An'),(31150,'Phường An Bình'),(31153,'Phường Ô Môn'),(31157,'Phường Thới Long'),(31162,'Phường Phước Thới'),(31168,'Phường Bình Thủy'),(31174,'Phường Thới An Đông'),(31183,'Phường Long Tuyền'),(31186,'Phường Cái Răng'),(31201,'Phường Hưng Phú'),(31207,'Phường Thuận Hưng'),(31212,'Phường Thốt Nốt'),(31213,'Phường Tân Lộc'),(31217,'Phường Trung Nhứt'),(31231,'Xã Thạnh An'),(31232,'Xã Vĩnh Thạnh'),(31237,'Xã Vĩnh Trinh'),(31246,'Xã Thạnh Quới'),(31249,'Xã Thạnh Phú'),(31255,'Xã Trung Hưng'),(31258,'Xã Thới Lai'),(31261,'Xã Cờ Đỏ'),(31264,'Xã Thới Hưng'),(31273,'Xã Đông Hiệp'),(31282,'Xã Đông Thuận'),(31288,'Xã Trường Thành'),(31294,'Xã Trường Xuân'),(31299,'Xã Phong Điền'),(31309,'Xã Trường Long'),(31315,'Xã Nhơn Ái'),(31321,'Phường Vị Thanh'),(31333,'Phường Vị Tân'),(31338,'Xã Hỏa Lựu'),(31340,'Phường Ngã Bảy'),(31342,'Xã Tân Hòa'),(31348,'Xã Trường Long Tây'),(31360,'Xã Thạnh Xuân'),(31366,'Xã Châu Thành'),(31369,'Xã Đông Phước'),(31378,'Xã Phú Hữu'),(31393,'Xã Hòa An'),(31396,'Xã Hiệp Hưng'),(31399,'Xã Tân Bình'),(31408,'Xã Thạnh Hòa'),(31411,'Phường Đại Thành'),(31420,'Xã Phụng Hiệp'),(31426,'Xã Phương Bình'),(31432,'Xã Tân Phước Hưng'),(31441,'Xã Vị Thủy'),(31453,'Xã Vĩnh Thuận Đông'),(31459,'Xã Vĩnh Tường'),(31465,'Xã Vị Thanh 1'),(31471,'Phường Long Mỹ'),(31473,'Phường Long Bình'),(31480,'Phường Long Phú 1'),(31489,'Xã Vĩnh Viễn'),(31492,'Xã Lương Tâm'),(31495,'Xã Xà Phiên'),(31507,'Phường Sóc Trăng'),(31510,'Phường Phú Lợi'),(31528,'Xã Kế Sách'),(31531,'Xã An Lạc Thôn'),(31537,'Xã Phong Nẫm'),(31540,'Xã Thới An Hội'),(31552,'Xã Nhơn Mỹ'),(31561,'Xã Đại Hải'),(31567,'Xã Mỹ Tú'),(31569,'Xã Phú Tâm'),(31570,'Xã Hồ Đắc Kiện'),(31579,'Xã Long Hưng'),(31582,'Xã Thuận Hòa'),(31591,'Xã Mỹ Hương'),(31594,'Xã An Ninh'),(31603,'Xã Mỹ Phước'),(31615,'Xã An Thạnh'),(31633,'Xã Cù Lao Dung'),(31639,'Xã Long Phú'),(31645,'Xã Đại Ngãi'),(31654,'Xã Trường Khánh'),(31666,'Xã Tân Thạnh'),(31673,'Xã Trần Đề'),(31675,'Xã Liêu Tú'),(31679,'Xã Lịch Hội Thượng'),(31684,'Phường Mỹ Xuyên'),(31687,'Xã Tài Văn'),(31699,'Xã Thạnh Thới An'),(31708,'Xã Nhu Gia'),(31717,'Xã Hòa Tú'),(31723,'Xã Ngọc Tố'),(31726,'Xã Gia Hòa'),(31732,'Phường Ngã Năm'),(31741,'Xã Tân Long'),(31753,'Phường Mỹ Quới'),(31756,'Xã Phú Lộc'),(31759,'Xã Lâm Tân'),(31777,'Xã Vĩnh Lợi'),(31783,'Phường Vĩnh Châu'),(31789,'Phường Khánh Hòa'),(31795,'Xã Vĩnh Hải'),(31804,'Phường Vĩnh Phước'),(31810,'Xã Lai Hòa');
/*!40000 ALTER TABLE `phuong_xa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tai_khoan`
--

DROP TABLE IF EXISTS `tai_khoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tai_khoan` (
  `TEN_TK` varchar(50) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MAT_KHAU` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MA_LOAI_TK` int NOT NULL DEFAULT '4',
  `SO_DINH_DANH` varchar(20) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `HASH_MAT_KHAU` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `DA_DOI_MAT_KHAU` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TEN_TK`),
  KEY `tai_khoan_ibfk_1` (`MA_LOAI_TK`),
  CONSTRAINT `tai_khoan_ibfk_1` FOREIGN KEY (`MA_LOAI_TK`) REFERENCES `loai_tk` (`MA_LOAI_TK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tai_khoan`
--

LOCK TABLES `tai_khoan` WRITE;
/*!40000 ALTER TABLE `tai_khoan` DISABLE KEYS */;
INSERT INTO `tai_khoan` VALUES ('0921','M7yBQ0zn',4,'0921',NULL,0),('092211392345','pl357Wxt',4,'092211392345',NULL,0),('093211002011','mS3l9gQe',4,'093211002011',NULL,0),('093211002012','L4O9ZyIr',4,'093211002012',NULL,0),('093211002013','3GOnMwaZ',4,'093211002013',NULL,0),('0944','BDhBlXjy',4,'0944',NULL,0),('099323451001','sxPDQtJ7',4,'099323451001',NULL,0),('admin',NULL,0,'0','$2a$10$1F0w08ZijpNGW0P/hLtAJuFHZ6LMsXjO.M0e.mADa/zqdrzox2j6O',1),('HSNT092345','wqdz5SdP',4,'HSNT092345','$2a$10$/ClU3gg8vrYl3LN3wk.CVuU/xSYpzG77qrSGFZL3h5nszJmkR9xCm',0),('HSNT373200','acToUmgn',4,'HSNT373200','$2a$10$uj0oluX96buAOdUawWXybekUnUOvzjL4FI8pCw8rZELXHsbckPnmq',0),('M01','irq5al69',4,'M01',NULL,0),('M02','uSHH1b2z',4,'M02',NULL,0),('M03','Clj3oS9c',4,'M03',NULL,0),('M04','K3lG6YjN',4,'M04',NULL,0),('M05','JS8CbGAI',4,'M05',NULL,0),('M06','zvzlbdyP',4,'M06',NULL,0),('M07','32S3HmKj',4,'M07',NULL,0),('M10','fm844cKE',4,'M10',NULL,0),('M101','O6KyKNts',4,'M101',NULL,0),('M102','wwgJQNJX',4,'M102',NULL,0),('M11','r3kdH9bs',4,'M11',NULL,0),('M110','tCuDn05D',4,'M110','$2a$10$zXzdGomVbNqgfhVV5lQDKuTT9Fz2ugcOK.bwn3tMZQM4nLaQ5lTKy',0),('M12','Kcv7t1q5',4,'M12',NULL,0),('M20','mwnL1wid',4,'M20',NULL,0),('M51','F6zXSlp4',4,'M51',NULL,0),('M52','AKgopjHh',4,'M52',NULL,0),('M55','JU462EWK',4,'M55',NULL,0),('M559','aaoSAoYB',4,'M559',NULL,0),('M81','1igwytqT',4,'M81',NULL,0),('M82','nDcyxPQC',4,'M82',NULL,0),('M83','fgz5qiGH',4,'M83',NULL,0),('M84','em7NT5As',4,'M84',NULL,0),('M85','lOyvvA9X',4,'M85',NULL,0),('M86','3iGxPILg',4,'M86',NULL,0),('M87','45bpfTQ5',4,'M87',NULL,0),('sgddt',NULL,1,'0','$2a$10$BAWhhCcDLZFHtZWPbSATmus0yqsyGJ96oS5ZafMj0uBOqHFClwbtO',1),('thcs-dtd',NULL,2,'92916506','$2a$10$pTNq2jQbIqYOautm.GcSwOEhJepEfg4.IarFtjxHq6JSu/L5/oDXy',1),('thcs-ltv',NULL,2,'92916507','$2a$10$YFf1Pcy6lNHwKZhz.Hy1qOb6z.aXZTT8Bs/rIi.f2R7tuB4IjHilu',1),('thcs-thd','1V3yd7pT',2,'92916503','$2a$10$RDpnk/RmHgCAyMkjJ/kGY.ItaeCVkNIvwYlpzZmMd73JIwUJ/LBAq',0),('thpt-cltt',NULL,3,'92000F01','$2a$10$svkbrrsQgdVl256LbGgQVu4VWoofWAsE4yGcnTRP29enDIg8JJPLS',1),('thpt-cvl',NULL,3,'92000701','$2a$10$M1sXqsNjgs.PiAXv0GB4Oe0B4TK2iM9ia0UcRoo0Sg68n/vl0oMn.',1);
/*!40000 ALTER TABLE `tai_khoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thi_sinh`
--

DROP TABLE IF EXISTS `thi_sinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thi_sinh` (
  `MA_HS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MA_THPT` varchar(20) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MA_LOP_CHUYEN` varchar(5) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `SO_BAO_DANH` varchar(10) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `PHONG_THI` varchar(10) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `PHONG_THI_CHUYEN` varchar(10) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`MA_HS`),
  UNIQUE KEY `SO_BAO_DANH` (`SO_BAO_DANH`),
  KEY `MA_THPT` (`MA_THPT`),
  KEY `MA_LOP_CHUYEN` (`MA_LOP_CHUYEN`),
  CONSTRAINT `thi_sinh_ibfk_1` FOREIGN KEY (`MA_HS`) REFERENCES `hoc_sinh` (`MA_HS`) ON DELETE CASCADE,
  CONSTRAINT `thi_sinh_ibfk_2` FOREIGN KEY (`MA_THPT`) REFERENCES `truong_thpt` (`MA_THPT`),
  CONSTRAINT `thi_sinh_ibfk_3` FOREIGN KEY (`MA_LOP_CHUYEN`) REFERENCES `lop_chuyen` (`MA_LOP_CHUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thi_sinh`
--

LOCK TABLES `thi_sinh` WRITE;
/*!40000 ALTER TABLE `thi_sinh` DISABLE KEYS */;
INSERT INTO `thi_sinh` VALUES ('0921','92000F01','01','270004','2701','2701'),('092211392345','92000F01','01','270013','2701','2701'),('093211002011','92000F01','02','270010','2701','2702'),('093211002012','92000F01','03','270019','2701','2703'),('093211002013','92000F01','04','270005','2701','2704'),('0944','92000F01','03','270020','2701','2703'),('099323451001','92000F01','02','270009','2701','2702'),('HSNT092345','92000F01','01',NULL,NULL,NULL),('HSNT373200','92000F01','06',NULL,NULL,NULL),('M01','94000706','01',NULL,NULL,NULL),('M02','92000F01','01','270002','2701','2701'),('M03','93000F16','02','500001','5001','5001'),('M04','92000701',NULL,'010001','0101',NULL),('M05','92000F01','02','270003','2701','2702'),('M06','94000706','03','560003','5601','5603'),('M07','92000701',NULL,'010002','0101',NULL),('M10','92000F01','05','270014','2701','2705'),('M101','92000701',NULL,NULL,NULL,NULL),('M102','92000701',NULL,NULL,NULL,NULL),('M11','92000F01','06','270011','2701','2706'),('M12','92000F01','07','270017','2701','2707'),('M20','92000F01','08','270015','2701','2708'),('M51','92000F01','09','270018','2701','2709'),('M52','92000F01','08','270012','2701','2708'),('M55','92000F01','07','270006','2701','2707'),('M559','92000F01','04',NULL,NULL,NULL),('M81','92000F01','06','270016','2701','2706'),('M82','92000F01','05','270001','2701','2705'),('M83','93000F16','04','500002','5001','5002'),('M84','92000701',NULL,'010003','0101',NULL),('M85','92000F01','02','270008','2701','2702'),('M86','94000706','01','560002','5601','5601'),('M87','92000701',NULL,'010004','0101',NULL);
/*!40000 ALTER TABLE `thi_sinh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trang_thai_csdl`
--

DROP TABLE IF EXISTS `trang_thai_csdl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trang_thai_csdl` (
  `MA_TT` int NOT NULL,
  `TEN_TRANG_THAI` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `KIEU_DU_LIEU` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `GIA_TRI_BOOLEAN` tinyint(1) DEFAULT NULL,
  `GIA_TRI_TIMESTAMP` timestamp NULL DEFAULT NULL,
  `GIA_TRI_CHUOI` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MO_TA` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `TG_CAP_NHAT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`MA_TT`),
  UNIQUE KEY `TEN_TRANG_THAI_UNIQUE` (`TEN_TRANG_THAI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trang_thai_csdl`
--

LOCK TABLES `trang_thai_csdl` WRITE;
/*!40000 ALTER TABLE `trang_thai_csdl` DISABLE KEYS */;
INSERT INTO `trang_thai_csdl` VALUES (1,'Cho phép cập nhật hồ sơ','BOOLEAN',1,NULL,'','Trạng thái cho phép cập nhật hồ sơ','2025-11-06 01:16:45'),(2,'Thời hạn đăng ký hồ sơ','TIMESTAMP',NULL,'2025-11-19 17:00:00','','Thời hạn đăng ký hồ sơ','2025-11-06 01:16:41'),(3,'Cho phép cập nhật số báo danh, phòng thi','BOOLEAN',0,NULL,NULL,'Trạng thái cho phép cập nhật số báo danh, phòng thi (0/1)','2025-10-31 02:13:20'),(4,'Cho phép cập nhật điểm thí sinh','BOOLEAN',0,NULL,NULL,'Trạng thái cho phép cập nhật điểm thí sinh','2025-10-31 02:21:06'),(5,'Cho phép chạy hệ thống xét tuyển','BOOLEAN',0,NULL,NULL,'Trạng thái cho phép chạy hệ thống xét tuyển','2025-10-31 02:16:45'),(6,'Cho phép khóa công bố kết quả tuyển sinh','BOOLEAN',1,NULL,'','Trạng thái khóa công bố kết quả tuyển sinh','2025-11-03 00:30:52'),(7,'Thời gian công bố kết quả tuyển sinh','TIMESTAMP',NULL,'2025-10-30 11:00:00',NULL,'Thời gian công bố kết quả tuyển sinh','2025-10-31 03:03:04');
/*!40000 ALTER TABLE `trang_thai_csdl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `truong_thcs`
--

DROP TABLE IF EXISTS `truong_thcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `truong_thcs` (
  `MA_THCS` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `TEN_THCS` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `MA_PHUONG_XA` int NOT NULL,
  PRIMARY KEY (`MA_THCS`),
  KEY `truong_thcs_ibfk_1` (`MA_PHUONG_XA`),
  CONSTRAINT `truong_thcs_ibfk_1` FOREIGN KEY (`MA_PHUONG_XA`) REFERENCES `phuong_xa` (`MA_PHUONG_XA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `truong_thcs`
--

LOCK TABLES `truong_thcs` WRITE;
/*!40000 ALTER TABLE `truong_thcs` DISABLE KEYS */;
INSERT INTO `truong_thcs` VALUES ('0','THCS ngoài TP Cần Thơ',0),('9200000002','TH, THCS và THPT FPT (HG)',31441),('9200000003','TH, THCS và THPT FPT (ST)',31510),('9200004001','TH, THCS và THPT song ngữ Aston',31201),('9200004002','TH, THCS và THPT song ngữ DNC',31150),('92000723','THCS và THPT Trần Ngọc Hoằng',31264),('92000802','Phổ thông Năng khiếu TDTT',31135),('92000813','THCS và THPT Trường Xuân',31294),('92000814','THCS và THPT Tân Lộc',31213),('92000815','THCS và THPT Thới Thuận',31212),('92000816','THCS và THPT Thạnh Thắng',31231),('92000901','Phổ thông Thái Bình Dương',31135),('92000902','Phổ thông Việt Mỹ',31201),('92000903','Phổ thông Âu Việt Mỹ',31147),('92000904','Phổ thông Việt Hoa',31186),('92000905','TH, THCS và THPT Quốc tế Singapore',31150),('92000906','TH, THCS và THPT Quốc tế Hòa Bình',31120),('92000E01','Phổ thông Dân tộc Nội trú Cần Thơ',31153),('9291603001','THCS An Bình',31150),('92916501','THCS An Hòa 1',31120),('92916502','THCS An Hòa 2',31120),('92916503','THCS Trần Hưng Đạo',31135),('92916504','THCS An Lạc',31135),('92916505','THCS Huỳnh Thúc Kháng',31135),('92916506','THCS Đoàn Thị Điểm',31135),('92916507','THCS Lương Thế Vinh',31147),('92916508','THCS Thới Bình',31135),('92916509','THCS Chu Văn An',31135),('92916510','THCS Trần Ngọc Quế',31150),('92916511','THCS An Khánh',31147),('92917501','THCS Châu Văn Liêm',31153),('92917502','THCS Lê Lợi',31162),('92917503','THCS Nguyễn Trãi',31153),('92917504','THCS Ngô Quyền',31162),('92917505','THCS Thới Long',31157),('92917506','THCS Thới Hòa',31153),('92918501','THCS Bình Thủy',31168),('92918502','THCS Long Tuyền',31183),('92918503','THCS Long Hòa',31183),('92918504','THCS Thới An Đông',31174),('92918505','THCS An Thới',31168),('92918506','THCS Trà An',31174),('92919501','THCS Lê Bình',31186),('92919502','THCS Thường Thạnh',31186),('92919503','THCS Hưng Phú',31201),('92919504','THCS Phú Thứ',31201),('92919505','THCS Hưng Thạnh',31186),('92923506','THCS Trung Nhứt',31217),('92923507','THCS Trung Kiên',31207),('92923508','THCS Thuận Hưng',31207),('92923509','THCS Thới Thuận',31212),('92923512','THCS Thốt Nốt',31212),('92923513','THCS Tân Hưng',31157),('92923514','THCS Tân Lộc',31213),('9292403001','THCS Thạnh Quới',31246),('92924501','THCS Thạnh Phú 2',31249),('92924502','THCS Thạnh An',31246),('92924503','THCS Vĩnh Thạnh 3',31232),('92924504','THCS Thạnh Phú 1',31249),('92924505','THCS Vĩnh Thạnh 1',31232),('92924508','THCS Thạnh An',31231),('92924509','THCS Trung Hưng',31255),('92924510','THCS Thạnh An 1',31231),('92924511','THCS Vĩnh Thạnh 2',31232),('92924512','THCS Vĩnh Trinh',31237),('92924513','THCS Thạnh An 2',31231),('92924514','TH và THCS Thạnh An',31231),('92924515','THCS Vĩnh Bình',31237),('92925501','THCS Định Môn',31288),('92925502','THCS Đông Bình',31282),('92925503','THCS Đông Hiệp',31273),('92925504','THCS Đông Thuận',31282),('92925505','THCS Thới Xuân',31261),('92925506','THCS Thị trấn Thới Lai',31258),('92925507','THCS và THPT Thới Thạnh',31153),('92925508','THCS Trường Thành 1',31288),('92925509','THCS Trường Thành 2',31288),('92925510','THCS Trường Xuân',31294),('92925511','TH và THCS Viện Lúa',31288),('92925512','THCS Thị trấn Cờ Đỏ',31261),('92925514','THCS Trung Thạnh',31255),('92925515','THCS Trung An',31217),('92926501','THCS Mỹ Khánh',31150),('92926502','THCS Giai Xuân',31299),('92926503','THCS Trường Long',31309),('92926504','THCS Nhơn Nghĩa',31315),('92926505','THCS Tân Thới',31299),('92926506','THCS Thị trấn Phong Điền',31299),('92926507','THCS Nhơn Ái',31315),('93000724','THPT Hòa An',31393),('93000725','THPT Cây Dương',31396),('93000822','THPT Vĩnh Tường',31459),('93000E02','Phổ thông Dân tộc nội trú Him Lam',31360),('933934502','THCS Long Thạnh',31408),('93930501','THCS Hoàng Diệu',31333),('93930502','THCS Phan Văn Trị',31321),('93930503','THCS Nguyễn Viết Xuân',31338),('93930504','THCS Nguyễn Việt Hồng',31321),('93930505','THCS Lê Quí Đôn',31321),('93930506','THCS Châu Văn Liêm',31333),('93931501','THCS Nguyễn Trãi',31340),('93931502','THCS Nguyễn Du',31340),('93931503','THCS Nguyễn Khuyến',31340),('93931504','THCS Lê Hồng Phong',31411),('93931505','THCS Hiệp Lợi',31411),('93931506','THCS Đại Thành',31411),('93932501','THCS Võ Thị Sáu',31342),('93932502','THCS Trường Long Tây',31348),('93932503','THCS Nhơn Nghĩa A',31342),('93932504','THCS Thị trấn Cái Tắc',31369),('93932505','THCS Trường Long A',31348),('93932506','THCS Tân Hòa',31342),('93933502','THCS Nam Kỳ Khởi Nghĩa',31366),('93933503','THCS Ngô Hữu Hạnh',31378),('93933504','THCS Đông Phú',31366),('93933505','THCS Lê Hồng Phong',31378),('93933506','THCS Nguyễn Văn Quy',31366),('93933508','THCS Đông Phước A',31369),('93934501','THCS Tân Long',31408),('93934503','THCS Thạnh Hòa',31408),('93934504','THCS Tân Bình',31399),('93934505','THCS Tây Đô',31426),('93934506','THCS Phương Phú',31426),('93934507','THCS Hòa Mỹ',31420),('93934508','THCS Búng Tàu',31432),('93934509','THCS Hưng Điền',31432),('93934510','THCS Bình Thành',31399),('93934511','THCS Hiệp Hưng',31396),('93934512','THCS Kinh Cùng',31393),('93934701','TH và THCS Phương Ninh',31426),('93935501','THCS Vĩnh Thuận Tây',31453),('93935503','THCS Vị Thanh',31465),('93935504','THCS Vị Đông',31465),('93935505','THCS Vị Thắng',31441),('93935506','THCS Vị Thủy',31453),('93935507','THCS Vị Bình',31465),('93935508','THCS Vĩnh Trung',31459),('93935509','THCS Ngô Quốc Trị',31441),('93936506','THCS Thuận Hưng',31495),('93936507','THCS Xà Phiên',31495),('93936508','THCS Lương Nghĩa',31492),('93936509','THCS Trương Tấn Lập',31489),('93936510','THCS Nguyễn Thành Đô',31489),('93936511','THCS Vĩnh Thuận Đông',31453),('93936512','THCS Chiêm Thành Tấn',31489),('93936513','THCS Lương Tâm',31492),('93936516','THCS Thuận Hòa',31495),('93937501','THCS Trịnh Văn Thì',31473),('93937502','THCS Long Trị A',31471),('93937503','THCS Long Trị',31471),('93937504','THCS Trà Lồng',31480),('93937505','THCS Long Phú',31480),('93937514','THCS Tân Phú',31480),('93937519','THCS Thuận An',31471),('94000820','PT DTNT THCS huyện Kế Sách',31540),('94000822','THCS và THPT Mỹ Thuận',31567),('94000826','THCS và THPT DTNT Vĩnh Châu',31804),('94000829','THCS và THPT Hưng Lợi',31756),('94000830','THCS và THPT Lai Hòa',31810),('94000831','THCS và THPT Tân Thạnh',31666),('94000835','THCS và THPT Khánh Hòa',31789),('94000837','THCS và THPT Trần Đề',31673),('94000838','PT DTNT THCS và THPT Thạnh Phú',31708),('9494103001','THCS Lý Thường Kiệt',31510),('94941501','THCS Dương Kỳ Hiệp',31510),('94941502','THCS Lê Quý Đôn',31510),('94941503','THCS Lê Vĩnh Hòa',31507),('94941505','THCS Tôn Đức Thắng',31507),('94941506','THCS Lê Hồng Phong',31510),('94941603','TH và THCS Dục Anh',31510),('94941900','TH, THCS và THPT Ischool Sóc Trăng',31507),('94942501','THCS An Hiệp',31594),('94942502','THCS Hồ Đắc Kiện',31570),('94942503','TH và THCS Phú Tâm',31569),('94942504','THCS Phú Tân',31582),('94942505','THCS Thị trấn Châu Thành',31569),('94942506','THCS Thiện Mỹ',31570),('94942507','THCS Vũng Thơm',31569),('94942508','THCS An Ninh',31594),('94942509','THCS DTNT Châu Thành',31594),('94942510','THCS Thuận Hòa',31582),('94943433','TH và THCS Phong Nẫm',31537),('94943501','THCS Ba Trinh',31561),('94943502','THCS Đại Hải 2',31561),('94943503','THCS Kế An',31528),('94943504','TH và THCS Kế Thành',31528),('94943505','THCS Kế Sách',31528),('94943506','THCS Nhơn Mỹ',31552),('94943508','THCS Trinh Phú',31531),('94943509','THCS Xuân Hòa',31531),('94943511','TH và THCS An Mỹ 1',31552),('94943512','TH và THCS An Mỹ 2',31552),('94943513','THCS An Lạc Tây',31540),('94943515','THCS Thới An Hội',31540),('94944507','THCS và THPT Long Hưng',31567),('94944508','THCS Hưng Phú',31579),('94944509','THCS Mỹ Tú',31567),('94944511','THCS Phú Mỹ',31591),('94944512','THCS Thuận Hưng',31591),('94944513','THCS DTNT Mỹ Tú',31567),('94944514','THCS Huỳnh Hữu Nghĩa',31567),('94944515','THCS Mỹ Phước A',31603),('94945501','THCS An Thạnh Tây',31615),('94945502','THCS An Thạnh 2',31633),('94945503','THCS An Thạnh Đông',31615),('94945505','THCS An Thạnh Nam',31633),('94945506','THCS Đại Ân 1',31633),('94945507','THCS An Thạnh 1',31615),('94945508','THCS Thị trấn Cù Lao Dung',31615),('94946426','TH và THCS Song Phụng',31552),('94946501','THCS Thị trấn Long Phú',31639),('94946502','THCS Long Phú',31639),('94946504','THCS Tân Hưng',31666),('94946505','THCS Tân Thạnh',31666),('94946506','THCS Châu Khánh',31666),('94946507','THCS Phú Hữu',31654),('94946508','THCS Long Đức',31645),('94946509','THCS Thị trấn Đại Ngãi',31645),('94946510','THCS Hậu Thạnh',31654),('94946511','THCS và THPT Dương Kỳ Hiệp',31654),('94947501','THCS Mỹ Xuyên',31684),('94947503','THCS Tham Đôn',31723),('94947504','THCS Đại Tâm',31684),('94947505','THCS Thạnh Quới',31726),('94947508','THCS Ngọc Đông',31723),('94947512','THCS Hòa Tú 2',31717),('94947513','THCS Gia Hòa 1',31708),('94947514','THCS Gia Hòa 2',31726),('94947515','THCS Ngọc Tố',31723),('94947519','THCS Hòa Tú 1',31717),('94947521','THCS Thạnh Phú',31708),('94947524','PT DTNT THCS Mỹ Xuyên',31684),('94947525','Thực hành Sư phạm Sóc Trăng',31684),('94948501','THCS Phường Ngã Năm',31732),('94948502','THCS Tân Long',31741),('94948503','THCS Long Tân',31732),('94948504','THCS Vĩnh Biên',31753),('94948505','TH và THCS Vĩnh Quới',31732),('94948506','TH và THCS Long Bình',31741),('94948507','THCS Mỹ Bình',31753),('94949501','THCS Phú Lộc',31756),('94949502','THCS Châu Hưng',31777),('94949503','TH và THCS Tuân Tức',31759),('94949505','THCS Vĩnh Lợi',31777),('94949506','THCS Thạnh Trị',31756),('94949508','THCS Lâm Tân',31759),('94949509','THCS Vĩnh Thành',31777),('94949511','THCS Lâm Kiết',31759),('94949512','THCS Phú Lộc 2',31756),('94949513','THCS Thạnh Tân',31741),('94949514','PT DTNT THCS huyện Thạnh Trị',31756),('94950501','TH và THCS Lai Hòa',31810),('94950503','THCS Vĩnh Phước 2',31804),('94950505','THCS Châu Văn Đơ',31783),('94950506','THCS Vĩnh Hiệp',31789),('94950508','THCS Phường 2',31783),('94950510','THCS Vĩnh Hải',31795),('94950511','THCS Lạc Hòa',31783),('94950512','THCS Hòa Đông',31789),('94950513','THCS Vĩnh Tân',31804),('94950604','PTCS Dân lập Bồi Thanh',31783),('94951501','THCS Thạnh Thới Thuận',31699),('94951502','THCS Thạnh Thới An',31699),('94951503','THCS Tài Văn',31687),('94951504','THCS Viên An',31687),('94951505','THCS Viên Bình',31675),('94951506','THCS Đại Ân 2',31673),('94951509','THCS Trung Bình',31673),('94951510','THCS Thị trấn Lịch Hội Thượng',31679),('94951511','THCS Liêu Tú',31675),('94951513','THCS xã Lịch Hội Thượng',31679),('94951514','PT DTNT THCS Trần Đề',31679);
/*!40000 ALTER TABLE `truong_thcs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `truong_thpt`
--

DROP TABLE IF EXISTS `truong_thpt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `truong_thpt` (
  `MA_THPT` varchar(20) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `TEN_THPT` varchar(255) COLLATE utf8mb4_vi_0900_as_cs NOT NULL,
  `DIA_CHI` varchar(255) COLLATE utf8mb4_vi_0900_as_cs DEFAULT NULL,
  `MA_PHUONG_XA` int NOT NULL DEFAULT '0',
  `CHI_TIEU` int NOT NULL DEFAULT '0',
  `THPT_CHUYEN` tinyint(1) NOT NULL DEFAULT '0',
  `TS_NGOAI_TPCT` tinyint(1) NOT NULL DEFAULT '0',
  `STT_THPT` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`MA_THPT`),
  KEY `truong_thpt_ibfk_1` (`MA_PHUONG_XA`),
  CONSTRAINT `truong_thpt_ibfk_1` FOREIGN KEY (`MA_PHUONG_XA`) REFERENCES `phuong_xa` (`MA_PHUONG_XA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vi_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `truong_thpt`
--

LOCK TABLES `truong_thpt` WRITE;
/*!40000 ALTER TABLE `truong_thpt` DISABLE KEYS */;
INSERT INTO `truong_thpt` VALUES ('92000701','THPT Châu Văn Liêm','58 Ngô Quyền, phường Ninh Kiều, TP Cần Thơ',31135,602,0,0,1),('92000702','THPT Lưu Hữu Phước','Khu vực 15, phường Ô Môn, TP Cần Thơ',31153,365,0,0,2),('92000705','THPT Nguyễn Việt Hồng','568 đường 3/2, khu vực 1, phường An Bình, TP Cần Thơ',31150,440,0,0,3),('92000709','THPT Thới Lai','Ấp Thới Thuận A, xã Thới Lai, TP Cần Thơ',31258,495,0,0,4),('92000711','THPT Thốt Nốt','Phường Thốt Nốt, TP Cần Thơ',31212,460,0,0,5),('92000713','THPT Hà Huy Giáp','Ấp Thới Bình, xã Cờ Đỏ, TP Cần Thơ',31261,348,0,0,6),('92000714','THPT Nguyễn Việt Dũng','161 Lê Bình, phường Cái Răng, TP Cần Thơ',31186,350,0,0,7),('92000715','THPT Phan Văn Trị','Xã Phong Điền, TP Cần Thơ',31299,480,0,0,8),('92000717','THPT Bùi Hữu Nghĩa','55 Cách mạng tháng Tám, phường Bình Thủy, TP Cần Thơ',31168,585,0,0,9),('92000718','THPT Lương Định Của','Khu vực 13, phường Ô Môn, TP Cần Thơ',31153,400,0,0,10),('92000719','THPT Thuận Hưng','Khu vực Tân Phước 1, phường Thuận Hưng, TP Cần Thơ',31207,286,0,0,11),('92000720','THPT Phan Ngọc Hiển','33 Xô Viết Nghệ Tĩnh, phường Ninh Kiều, TP Cần Thơ',31135,520,0,0,12),('92000721','THPT Thạnh An','Ấp Phụng Qưới A, xã Thạnh An, TP Cần Thơ',31231,400,0,0,13),('92000722','THPT Trần Đại Nghĩa','Đường A3, KDC Hưng Phú 1, phường Hưng Phú, TP Cần Thơ',31201,449,0,0,14),('92000723','THCS và THPT Trần Ngọc Hoằng','Ấp 2, xã Thới Hưng, TP Cần Thơ',31264,150,0,0,15),('92000724','THPT Vĩnh Thạnh','Xã Vĩnh Thạnh, TP Cần Thơ',31232,420,0,0,16),('92000725','THPT Trung An','Ấp Thạnh Lộc 2, phường Trung Nhứt, TP Cần Thơ',31217,325,0,0,17),('92000726','THPT Giai Xuân','Tỉnh lộ 918, ấp Thới An A, xã Phong Điền, TP Cần Thơ',31299,290,0,0,18),('92000727','THPT Thực hành sư phạm - ĐHCT','Khu II Đại học Cần Thơ, phường Ninh Kiều, TP Cần Thơ',31135,216,0,1,19),('92000728','THPT An Khánh','106 Trần Văn Long, KDC Thới Nhựt 2, phường Tân An, TP Cần Thơ',31147,480,0,0,20),('92000806','THPT Thới Long','177 Thái Thị Hạnh, khu vực Long Định, phường Thới Long, TP Cần Thơ',31157,360,0,0,21),('92000812','THPT Bình Thủy','KDC Ngân Thuận, phường Bình Thủy, TP Cần Thơ',31168,600,0,0,22),('92000813','THCS và THPT Trường Xuân','Ấp Trường Ninh 1, xã Trường Xuân, TP Cần Thơ',31294,180,0,0,23),('92000814','THCS và THPT Tân Lộc','Khu vực Phước Lộc, phường Tân Lộc, TP Cần Thơ',31213,222,0,0,24),('92000815','THCS và THPT Thới Thuận','Quốc lộ 80, khu vực Thới Hòa 2, phường Thốt Nốt, TP Cần Thơ',31212,180,0,0,25),('92000816','THCS và THPT Thạnh Thắng','Xã Thạnh An, TP Cần Thơ',31231,160,0,0,26),('92000F01','THPT Chuyên Lý Tự Trọng','Số 79, khu vực 2, phường Cái Răng, TP Cần Thơ',31186,420,1,1,27),('92925507','THCS và THPT Thới Thạnh','Ấp Thới Bình B, phường Ô Môn, TP Cần Thơ',31153,260,0,0,28),('93000701','THPT Ngã Sáu','Ấp Thuận Hưng, xã Châu Thành, TP Cần Thơ',31366,310,0,0,29),('93000702','THPT Tầm Vu','Xã Thạnh Xuân, TP Cần Thơ',31360,364,0,0,30),('93000703','THPT Cái Tắc','Xã Đông Phước, TP Cần Thơ',31369,387,0,0,31),('93000705','THPT Lương Thế Vinh','Ấp Hoà Phụng B, xã Hòa An, TP Cần Thơ',31393,315,0,0,32),('93000706','THPT Nguyễn Minh Quang','58 Đường 30/4, phường Ngã Bảy, TP Cần Thơ',31340,344,0,0,33),('93000708','THPT Long Mỹ','KV Bình Thạnh B, phường Long Bình, TP Cần Thơ',31473,564,0,0,34),('93000709','THPT Tây Đô','Ấp 12, xã Vĩnh Viễn, TP Cần Thơ',31489,280,0,0,35),('93000710','THPT Vị Thủy','Xã Vị Thủy, TP Cần Thơ',31441,440,0,0,36),('93000711','THPT Vị Thanh','559 Trần Hưng Đạo, phường Vị Thanh, TP Cần Thơ',31321,475,0,0,37),('93000712','THPT Tân Long','Xã Thạnh Hòa, TP Cần Thơ',31408,225,0,0,38),('93000713','THPT Tân Phú','Ấp Tân Hưng 2, phường Long Phú 1, TP Cần Thơ',31480,225,0,0,39),('93000714','THPT Chiêm Thành Tấn','03 Chu Văn An, KV1, phường Vị Thanh, TP Cần Thơ',31321,315,0,0,40),('93000715','THPT Lê Hồng Phong','Ấp 1, xã Vị Thanh 1, TP Cần Thơ',31465,270,0,0,41),('93000717','THPT Lê Quý Đôn','Đường 30/04, KV2, phường Ngã Bảy, TP Cần Thơ',31340,315,0,0,42),('93000718','THPT Phú Hữu','Ấp Phú Lợi, xã Phú Hữu, TP Cần Thơ',31378,177,0,0,43),('93000719','THPT Lương Tâm','Ấp 2, xã Lương Tâm, TP Cần Thơ',31492,240,0,0,44),('93000721','THPT Trường Long Tây','Ấp Trường Thọ A, xã Trường Long Tây, TP Cần Thơ',31348,180,0,0,45),('93000724','THPT Hòa An','Tỉnh lộ 927, ấp 8, xã Hòa An, TP Cần Thơ',31393,180,0,0,46),('93000725','THPT Cây Dương','Ấp Hưng Phú, xã Hiệp Hưng, TP Cần Thơ',31396,450,0,0,47),('93000804','THPT Châu Thành A','Ấp Thị Tứ, xã Tân Hòa, TP Cần Thơ',31342,356,0,0,48),('93000822','THPT Vĩnh Tường','Ấp Vĩnh Hiếu, xã Vĩnh Tường, TP Cần Thơ',31459,124,0,0,49),('93000F16','THPT Chuyên Vị Thanh','19 Nguyễn Công Trứ, phường Vị Thanh, TP Cần Thơ',31321,315,1,1,50),('94000701','THPT Hoàng Diệu','01 Mạc Đĩnh Chi, phường Phú Lợi, TP Cần Thơ',31510,675,0,0,51),('94000702','THPT Huỳnh Hữu Nghĩa','Ấp Nội Ô, xã Mỹ Tú, TP Cần Thơ',31567,350,0,0,52),('94000703','THPT Kế Sách','Ấp An Khương, xã Kế Sách, TP Cần Thơ',31528,650,0,0,53),('94000704','THPT Mỹ Xuyên','195 Tỉnh lộ 934, ấp Châu Thành, phường Mỹ Xuyên, TP Cần Thơ',31684,530,0,0,54),('94000705','THPT Nguyễn Khuyến','Nguyễn Huệ, khóm 1, phường Vĩnh Châu, TP Cần Thơ',31783,500,0,0,55),('94000706','THPT Chuyên Nguyễn Thị Minh Khai','Đường Hồ Nước Ngọt, phường Sóc Trăng, TP Cần Thơ',31507,350,1,1,56),('94000707','THPT Trần Văn Bảy','Ấp 3, xã Phú Lộc, TP Cần Thơ',31756,630,0,0,57),('94000709','THPT Thiều Văn Chỏi','Ấp 10, xã An Lạc Thôn, TP Cần Thơ',31531,450,0,0,58),('94000710','THPT Mai Thanh Thế','04 Đường Phạm Văn Đồng, phường Ngã Năm, TP Cần Thơ',31732,480,0,0,59),('94000711','THPT Đại Ngãi','Ấp Ngãi Hội 2, xã Đại Ngãi, TP Cần Thơ',31645,430,0,0,60),('94000712','THPT Lê Văn Tám','Phường Mỹ Quới, TP Cần Thơ',31753,260,0,0,61),('94000713','THPT Lịch Hội Thượng','Ấp Phố Dưới, xã Lịch Hội Thượng, TP Cần Thơ',31679,380,0,0,62),('94000714','THPT Ngọc Tố','Ấp Cổ Cò, xã Ngọc Tố, TP Cần Thơ',31723,170,0,0,63),('94000715','THPT Thuận Hòa','Ấp Trà Quýt, xã Phú Tâm, TP Cần Thơ',31569,480,0,0,64),('94000717','THPT Hòa Tú','Ấp Hòa Phuông, xã Hòa Tú, TP Cần Thơ',31717,300,0,0,65),('94000718','THPT Vĩnh Hải','Phường Vĩnh Châu, TP Cần Thơ',31783,315,0,0,66),('94000719','THPT Đoàn Văn Tố','Xã An Thạnh, TP Cần Thơ',31615,380,0,0,67),('94000720','THPT Lương Định Của (Long Phú)','Ấp 2, xã Long Phú, TP Cần Thơ',31639,340,0,0,68),('94000721','THPT Phú Tâm','Xã Phú Tâm, TP Cần Thơ',31569,260,0,0,69),('94000722','THPT Văn Ngọc Chính','QL1A, ấp Khu 3, xã Nhu Gia, TP Cần Thơ',31708,310,0,0,70),('94000723','THPT An Ninh','Xã Mỹ Hương, TP Cần Thơ',31591,300,0,0,71),('94000724','THPT Ngã Năm','Khóm Tân Thành A, phường Ngã Năm, TP Cần Thơ',31732,220,0,0,72),('94000725','THPT Thành phố Sóc Trăng','1115 Trần Hưng Đạo, phường Mỹ Xuyên, TP Cần Thơ',31684,650,0,0,73),('94000801','THPT An Lạc Thôn','Ấp An Ninh 2, xã An Lạc Thôn, TP Cần Thơ',31531,330,0,0,74),('94000803','THPT Phan Văn Hùng','Ấp Đông Hải, xã Đại Hải, TP Cần Thơ',31561,300,0,0,75),('94000809','THPT Mỹ Hương','Ấp Xóm Lớn, xã Mỹ Hương, TP Cần Thơ',31591,175,0,0,76),('94000819','THPT An Thạnh 3','Xã Cù Lao Dung, TP Cần Thơ',31633,220,0,0,77),('94000822','THCS và THPT Mỹ Thuận','Xã Mỹ Tú, TP Cần Thơ',31567,220,0,0,78),('94000828','THPT Thạnh Tân','Xã Tân Long, TP Cần Thơ',31741,280,0,0,79),('94000829','THCS và THPT Hưng Lợi','Ấp Số 8, xã Phú Lộc, TP Cần Thơ',31756,160,0,0,80),('94000830','THCS và THPT Lai Hòa','Ấp Xẻo Su, xã Lai Hòa, TP Cần Thơ',31810,280,0,0,81),('94000831','THCS và THPT Tân Thạnh','Xã Tân Thạnh, TP Cần Thơ',31666,320,0,0,82),('94000835','THCS và THPT Khánh Hòa','Khóm Bưng Tum, phường Khánh Hòa, TP Cần Thơ',31789,280,0,0,83),('94000837','THCS và THPT Trần Đề','Xã Trần Đề, TP Cần Thơ',31673,270,0,0,84),('94944507','THCS và THPT Long Hưng','Ấp Mỹ Khánh A, xã Long Hưng, TP Cần Thơ',31579,120,0,0,85),('94946511','THCS và THPT Dương Kỳ Hiệp','QL60, ấp Trường Thành A, xã Trường Khánh, TP Cần Thơ',31654,135,0,0,86);
/*!40000 ALTER TABLE `truong_thpt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-13 14:27:33
