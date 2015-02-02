-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2014 at 06:01 AM
-- Server version: 5.5.32
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ap-project`
--
CREATE DATABASE IF NOT EXISTS `ap-project` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ap-project`;

-- --------------------------------------------------------

--
-- Table structure for table `armbands`
--

CREATE TABLE IF NOT EXISTS `armbands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `colour` int(11) NOT NULL,
  `code` varchar(12) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_armbands_armband_colours1_idx` (`colour`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `armbands`
--

INSERT INTO `armbands` (`id`, `startDate`, `endDate`, `colour`, `code`) VALUES
(1, '2014-04-09', '2014-04-26', 1, '12a'),
(2, '2014-04-01', '2014-04-23', 2, 'xdc1'),
(3, '2014-04-15', '2014-04-17', 1, 'xdc2');

-- --------------------------------------------------------

--
-- Table structure for table `armband_colours`
--

CREATE TABLE IF NOT EXISTS `armband_colours` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `value` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `armband_colours`
--

INSERT INTO `armband_colours` (`id`, `name`, `value`) VALUES
(1, 'blue', '#0000FF'),
(2, 'orange', '#FFA500');

-- --------------------------------------------------------

--
-- Table structure for table `drinks`
--

CREATE TABLE IF NOT EXISTS `drinks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `type` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_drinks_drink_types1_idx` (`type`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `drinks`
--

INSERT INTO `drinks` (`id`, `name`, `price`, `type`) VALUES
(1, 'Freza', 890, 1),
(2, 'Acaroo', 700, 1),
(3, 'rum', 1231, 1),
(5, 'wata', 151, 2),
(6, 'rosered', 110, 1),
(7, 'sprite', 120, 2),
(8, 'soda', 2998, 2),
(9, 'cran-wata', 101, 2),
(10, 'Alize', 397, 1);

-- --------------------------------------------------------

--
-- Table structure for table `drink_types`
--

CREATE TABLE IF NOT EXISTS `drink_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `drink_types`
--

INSERT INTO `drink_types` (`id`, `name`) VALUES
(1, 'Alcoholic'),
(2, 'non-alcoholic');

-- --------------------------------------------------------

--
-- Table structure for table `guests`
--

CREATE TABLE IF NOT EXISTS `guests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_real` varchar(45) DEFAULT NULL COMMENT 'eg. USR10021, MGR10025 or BTD10021',
  `name` varchar(50) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `armband_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_guests_armbands1_idx` (`armband_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `guests`
--

INSERT INTO `guests` (`id`, `id_real`, `name`, `password`, `code`, `armband_id`) VALUES
(1, NULL, 'ashani', '123456', '1', 1),
(2, NULL, 'lisa', '123456', 'xd123', 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `guest_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_guests1_idx` (`guest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `date`, `guest_id`) VALUES
(3, '2014-04-01', 1),
(93, '2014-05-02', 1),
(738, '2014-05-18', 2),
(813, '2014-05-18', 2),
(856, '2014-05-02', 1),
(921, '2014-05-02', 1),
(947, '2014-05-18', 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders_has_drinks`
--

CREATE TABLE IF NOT EXISTS `orders_has_drinks` (
  `orders_id` int(11) NOT NULL,
  `drinks_id` int(11) NOT NULL DEFAULT '5',
  PRIMARY KEY (`orders_id`,`drinks_id`),
  KEY `fk_orders_has_drinks_drinks1_idx` (`drinks_id`),
  KEY `fk_orders_has_drinks_orders1_idx` (`orders_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders_has_drinks`
--

INSERT INTO `orders_has_drinks` (`orders_id`, `drinks_id`) VALUES
(3, 1),
(93, 3),
(738, 3),
(856, 3),
(3, 5),
(947, 5),
(738, 6),
(93, 7),
(813, 7),
(856, 7),
(947, 7),
(921, 8),
(93, 9),
(813, 9),
(856, 9),
(947, 9);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_real` varchar(45) DEFAULT NULL COMMENT 'eg. USR10021, MGR10025 or BTD10021',
  `name` varchar(50) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `hours` varchar(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_staff_staff_roles_idx` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `id_real`, `name`, `password`, `hours`, `role_id`) VALUES
(1, 'BTD10001', 'Justin Bog', 'defaultPass', '9-5', 2),
(2, 'MGR10001', 'John', '123456', '9-5', 1),
(3, 'MGR10002', 'Brandon', '123456', '9-5', 1),
(4, 'MGR10003','ReliQ', '123456', '9-5', 1),
(5, NULL, 'Ashani', '123456', '5-2', 1);

-- --------------------------------------------------------

--
-- Table structure for table `staff_has_orders`
--

CREATE TABLE IF NOT EXISTS `staff_has_orders` (
  `staff_id` int(11) NOT NULL,
  `orders_id` int(11) NOT NULL,
  PRIMARY KEY (`staff_id`,`orders_id`),
  KEY `fk_staff_has_orders_orders1_idx` (`orders_id`),
  KEY `fk_staff_has_orders_staff1_idx` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staff_roles`
--

CREATE TABLE IF NOT EXISTS `staff_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `staff_roles`
--

INSERT INTO `staff_roles` (`id`, `name`) VALUES
(1, 'Manager'),
(2, 'Bartender');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `armbands`
--
ALTER TABLE `armbands`
  ADD CONSTRAINT `fk_armbands_armband_colours1` FOREIGN KEY (`colour`) REFERENCES `armband_colours` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `drinks`
--
ALTER TABLE `drinks`
  ADD CONSTRAINT `fk_drinks_drink_types1` FOREIGN KEY (`type`) REFERENCES `drink_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `guests`
--
ALTER TABLE `guests`
  ADD CONSTRAINT `fk_guests_armbands1` FOREIGN KEY (`armband_id`) REFERENCES `armbands` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_orders_guests1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `orders_has_drinks`
--
ALTER TABLE `orders_has_drinks`
  ADD CONSTRAINT `fk_orders_has_drinks_drinks1` FOREIGN KEY (`drinks_id`) REFERENCES `drinks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orders_has_drinks_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `fk_staff_staff_roles` FOREIGN KEY (`role_id`) REFERENCES `staff_roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `staff_has_orders`
--
ALTER TABLE `staff_has_orders`
  ADD CONSTRAINT `fk_staff_has_orders_orders1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_staff_has_orders_staff1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
