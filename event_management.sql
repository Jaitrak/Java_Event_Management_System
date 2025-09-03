-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 29, 2024 at 06:34 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `event_management`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSponsor` (IN `sp_id` INT(11), IN `name` VARCHAR(10), IN `contact` VARCHAR(50), IN `sp_date` DATE)   begin
insert into sponsor (sponsor_name,contact_info,sponsorship_date) values(name,contact,sp_date);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `changeCost` (IN `id_in` INT(11), IN `cost_in` DOUBLE)   BEGIN
update venue set cost=cost_in where id=id_in;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `changeRating` (IN `id_in` INT(11), IN `rating_in` DOUBLE)   BEGIN
update  venue set ratings=rating_in where id=id_in;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUserPassword` (IN `user_name_in` INT, IN `passwordIn` INT)   BEGIN
    UPDATE user
    SET password = passwordIN
    WHERE userName = user_name_in;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `password`, `role`) VALUES
(1, 'jaitrak', '3306', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `cost` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sponsor`
--

CREATE TABLE `sponsor` (
  `sponsor_id` int(11) NOT NULL,
  `sponsor_name` varchar(50) NOT NULL,
  `contact_info` varchar(50) NOT NULL,
  `sponsorshilp_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES
(1, 'jay', '3306', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `venue`
--

CREATE TABLE `venue` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` varchar(10) NOT NULL,
  `ratings` double NOT NULL,
  `cost` double NOT NULL,
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `venue`
--

INSERT INTO `venue` (`id`, `name`, `type`, `ratings`, `cost`, `location`) VALUES
(1, 'Hotel Hyatt', 'Hotel', 8.3, 40000, '17/A, Ashram Rd, Usmanpura, Ahmedabad, Gujarat 380'),
(2, 'Artilla Inn', 'Hotel', 8.2, 31020, 'Artilla Inn , Opposite TownHall , At, Swami Viveka'),
(3, 'Novotel Ahmedabad', 'Hotel', 8.1, 40770, 'Iscon Cross Roads, Sarkhej - Gandhinagar Hwy, next'),
(4, 'DoubleTree by Hilton', 'Hotel', 8.2, 52270, 'Ambli Rd, VikramNagar, Ahmedabad, Gujarat 380058'),
(5, 'Hotel Crown Plaza', 'Hotel', 8.2, 59999, 'Sarkhej - Gandhinagar, near Shapath V, Prahlad Nag'),
(6, 'Hotel Ummed', 'Hotel', 6.9, 62490, 'The Ummed, Airport Cir, Sardarnagar, Hansol, Ahmed'),
(7, 'Courtyard by Marriott', 'Hotel', 8.5, 60010, 'Ramdev Nagar Cross Road, Satellite Rd, Ahmedabad, '),
(8, 'Hotel Elysian residency', 'Hotel', 8, 39999, 'Hotel Elysian Residency, Opp Reliance Mall Near Na'),
(9, 'Four Points by Sheraton', 'Hotel', 8.2, 42999, 'opposite Gujarat College, Ellisbridge, Ahmedabad, '),
(10, 'Arose Foods Banquet', 'Party Plot', 4.7, 379, '2F5P+92W, Arose Foods, Karnavati Club Road YMCA to'),
(11, 'Under The Neem Trees', 'Party Plot', 4.6, 2250, 'opp. Mahila Municipal Garden, Rajpath Rangoli Rd, '),
(12, 'Suramya Abode Resort', 'Party Plot', 3, 850, 'Suramya Abode Club, Sanand - Nalsarovar Rd, Rethal'),
(13, 'Arose Foods Banquet', 'Party Plot', 4.7, 379, '2F5P+92W, Arose Foods, Karnavati Club Road YMCA to'),
(14, 'Under The Neem Trees', 'Party Plot', 4.6, 2250, 'opp. Mahila Municipal Garden, Rajpath Rangoli Rd, '),
(15, 'Suramya Abode Resort', 'Party Plot', 3, 850, 'Suramya Abode Club, Sanand - Nalsarovar Rd, Rethal'),
(16, 'Club Babylon', 'Party Plot', 5, 950, 'Near, Babylon Club Road, S P Road, Science City Ci'),
(17, 'Maple 99 Resort and Banquet', 'Party Plot', 5, 600, 'Sardar Patel Ring Rd, NEAR TAPOVAN CIRCLE, Chandkh');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sponsor`
--
ALTER TABLE `sponsor`
  ADD PRIMARY KEY (`sponsor_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `venue`
--
ALTER TABLE `venue`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sponsor`
--
ALTER TABLE `sponsor`
  MODIFY `sponsor_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `venue`
--
ALTER TABLE `venue`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
