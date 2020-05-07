-- phpMyAdmin SQL Dump
-- version 4.2.3
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jun 17, 2017 at 01:20 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `_personal`
--

-- --------------------------------------------------------

--
-- Table structure for table `xzclf_ads`
--

CREATE TABLE IF NOT EXISTS `xzclf_ads` (
`adid` int(10) unsigned NOT NULL,
  `adtitle` varchar(100) NOT NULL DEFAULT '',
  `addesc` longtext NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `subcatid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `enabled` enum('0','1') NOT NULL DEFAULT '0',
  `createdon` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `expireson` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `picfile` varchar(300) NOT NULL DEFAULT 'http://scadsdnd.ddns.net/bib/empty.png'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

-- --------------------------------------------------------

--
-- Table structure for table `xzclf_cats`
--

CREATE TABLE IF NOT EXISTS `xzclf_cats` (
`catid` smallint(5) unsigned NOT NULL,
  `catname` varchar(50) NOT NULL DEFAULT '',
  `enabled` enum('0','1') NOT NULL DEFAULT '0'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=132 ;


-- --------------------------------------------------------

--
-- Table structure for table `xzclf_subcats`
--

CREATE TABLE IF NOT EXISTS `xzclf_subcats` (
`subcatid` smallint(5) unsigned NOT NULL,
  `subcatname` varchar(50) NOT NULL DEFAULT '',
  `catid` smallint(5) unsigned NOT NULL DEFAULT '0',
  `enabled` enum('0','1') NOT NULL DEFAULT '0'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4520 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `xzclf_ads`
--
ALTER TABLE `xzclf_ads`
 ADD PRIMARY KEY (`adid`), ADD KEY `subcatid` (`subcatid`), ADD KEY `enabled` (`enabled`);

--
-- Indexes for table `xzclf_cats`
--
ALTER TABLE `xzclf_cats`
 ADD PRIMARY KEY (`catid`), ADD KEY `enabled` (`enabled`);

--
-- Indexes for table `xzclf_subcats`
--
ALTER TABLE `xzclf_subcats`
 ADD PRIMARY KEY (`subcatid`), ADD KEY `catid` (`catid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `xzclf_ads`
--
ALTER TABLE `xzclf_ads`
MODIFY `adid` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `xzclf_cats`
--
ALTER TABLE `xzclf_cats`
MODIFY `catid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=132;
--
-- AUTO_INCREMENT for table `xzclf_subcats`
--
ALTER TABLE `xzclf_subcats`
MODIFY `subcatid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4520;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
