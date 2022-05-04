-- --------------------------------------------------------
-- Host:                         bracuspacedb.cfvbdj3wsdll.ap-south-1.rds.amazonaws.com
-- Server version:               8.0.23 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for bracuspacedb
CREATE DATABASE IF NOT EXISTS `bracuspacedb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bracuspacedb`;

-- Dumping structure for table bracuspacedb.file
CREATE TABLE IF NOT EXISTS `file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `directory` bit(1) DEFAULT NULL,
  `file` bit(1) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_size` bigint DEFAULT NULL,
  `last_modified_date` date DEFAULT NULL,
  `mime_type` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table bracuspacedb.file: ~0 rows (approximately)
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;

-- Dumping structure for table bracuspacedb.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table bracuspacedb.hibernate_sequence: ~1 rows (approximately)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(33);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table bracuspacedb.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table bracuspacedb.role: ~2 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT IGNORE INTO `role` (`id`, `name`) VALUES
	(1, 'ROLE_ADMIN'),
	(31, 'ROLE_MANAGER'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table bracuspacedb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table bracuspacedb.user: ~20 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `email`, `enabled`, `name`, `password`, `surname`, `username`) VALUES
	(3, 'admin@gmail.com', b'1', 'Admin', '$2a$10$B46ivMJw1koghKy4au.ND.oyfMTGEHc1b4pmI11EQk.rM9rllEwwS', 'Admin', 'admin'),
	(4, 'user1@gmail.com', b'1', 'User1', '$2a$10$41qVwJntqLq.lEjtsglG5Oal1O18KmPYttZgQQgft0s0NVbUn/Fvm', 'User1', 'user1'),
	(5, 'user2@gmail.com', b'1', 'User2', '$2a$10$pmtNV79NRsA6ZgIqEYlBzenE1BO5ZtB8Se44ktZB3fMS9R9a5M1Oq', 'User2', 'user2'),
	(9, 'user6@gmail.com', b'1', 'User6', '$2a$10$EqY5sGT9m.asTwmRO4LpsOmQ843xqXtkVsAz7jF9uKgHvrFIznoZW', 'User6', 'user6'),
	(10, 'user7@gmail.com', b'1', 'User7', '$2a$10$XBZe7shbitY9m2eJEGkn.O2/DfhJrWq81vQkx2UQUrLkgt60WwXTK', 'User7', 'user7'),
	(11, 'user8@gmail.com', b'1', 'User8', '$2a$10$JBw2RLVBD2m3rX2/QUKrReqW1HyibPPimIUjGkzxXVVwPXsBFyksm', 'User8', 'user8'),
	(12, 'user9@gmail.com', b'1', 'User9', '$2a$10$uoIbcfgtcCILe2D/5tH5gO.H1rqBmehP8uXgW5KdlNYHMUkVegX7S', 'User9', 'user9'),
	(13, 'user10@gmail.com', b'1', 'User10', '$2a$10$s2DK8KXz8/AVnLPCvBymaOQJ42xt3nBKiqKaJbR95h/n8DbAuHjPS', 'User10', 'user10'),
	(14, 'user11@gmail.com', b'1', 'User11', '$2a$10$qtgRcpKhXZxQTUaTTRvyQ.F1ZcDLUHvmVWrGw4dhPjcpsSNwJB/sm', 'User11', 'user11'),
	(15, 'user12@gmail.com', b'1', 'User12', '$2a$10$UUhquP8HCx0mwHWT/chA/u3C39hSOedUkfxw0CAHp7OQHN/W3m46y', 'User12', 'user12'),
	(16, 'user13@gmail.com', b'1', 'User13', '$2a$10$bqC/s2u6k1I.jfhNPIijPuqp2aSYgd7WgS9mjSJoyRE4FitFVUmHS', 'User13', 'user13'),
	(17, 'user14@gmail.com', b'1', 'User14', '$2a$10$fJpUDvwBFVVz/4CwO.EyAe7Ps.d/G3zEUgWKOhX064r5GRl0lJkhG', 'User14', 'user14'),
	(18, 'user15@gmail.com', b'1', 'User15', '$2a$10$COjxosEH38U/E8WVYz8Ajed3eLHEPp3Czvvsz1w5pjXHy3q1CR.Wa', 'User15', 'user15'),
	(19, 'user16@gmail.com', b'1', 'User16', '$2a$10$J7/tmmmHBUKkwpXpE7Wf1OCfKqaR/xKZKinXoDvQVpkQ90U2l1JVe', 'User16', 'user16'),
	(20, 'user17@gmail.com', b'1', 'User17', '$2a$10$eWV.u6slN.ZK.wky/fFiiOyIsuJ4yxiiotNyFm95iEECOkprH3k7.', 'User17', 'user17'),
	(21, 'user18@gmail.com', b'1', 'User18', '$2a$10$h6nUqWiTMT2FyD/xwDG.JOB/Oy/jlHgA.wxqTTelNMDLoGQ0QVsyK', 'User18', 'user18'),
	(22, 'user19@gmail.com', b'1', 'User19', '$2a$10$YtawIYYn27/31aBKw2frXO3KGzMk2JrPI7wMr04.8SHemDu.5K5rq', 'User19', 'user19'),
	(29, 'user4@gmail.com', b'1', 'User4', '$2a$10$eXlfrORVheVAmAuO8l3/ZOEsWea8y/D8rFbL7vpaDdCqv5izIiVg6', 'User4', 'user4'),
	(32, 'tanvir.rahman1@g.bracu.ac.bd', b'1', 'Tanvir', '$2a$10$zfU/fqtvJtjN1I6DpisNVuVRpAwLOBa2zyFgo16g7IenogeuPTT9y', 'Rahman', 'tanvirrahman');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table bracuspacedb.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table bracuspacedb.user_role: ~20 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT IGNORE INTO `user_role` (`user_id`, `role_id`) VALUES
	(3, 1),
	(4, 2),
	(5, 2),
	(9, 2),
	(10, 2),
	(11, 2),
	(12, 2),
	(13, 2),
	(14, 2),
	(15, 2),
	(16, 2),
	(17, 2),
	(18, 2),
	(19, 2),
	(20, 2),
	(21, 2),
	(22, 2),
	(29, 2),
	(32, 2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
