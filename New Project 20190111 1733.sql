-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.72-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema pos
--

CREATE DATABASE IF NOT EXISTS pos;
USE pos;

--
-- Definition of table `audit`
--

DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `idaudit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `timer` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdby` int(10) unsigned NOT NULL,
  `action` varchar(200) NOT NULL,
  PRIMARY KEY (`idaudit`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audit`
--

/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` (`idaudit`,`timer`,`createdby`,`action`) VALUES 
 (1,'2019-01-11 13:54:28',1,'created status'),
 (2,'2019-01-11 16:19:19',1,'saved user pos'),
 (3,'2019-01-11 16:20:01',1,'created group'),
 (4,'2019-01-11 16:28:09',1,'logged into the system at  Fri Jan 11 16:28:09 EAT 2019'),
 (5,'2019-01-11 16:29:49',1,'logged into the system at  Fri Jan 11 16:29:49 EAT 2019'),
 (6,'2019-01-11 16:42:38',1,'logged into the system at  Fri Jan 11 16:42:38 EAT 2019');
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;


--
-- Definition of table `outlet`
--

DROP TABLE IF EXISTS `outlet`;
CREATE TABLE `outlet` (
  `idoutlet` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `outletname` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`idoutlet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `outlet`
--

/*!40000 ALTER TABLE `outlet` DISABLE KEYS */;
/*!40000 ALTER TABLE `outlet` ENABLE KEYS */;


--
-- Definition of table `paymentmethods`
--

DROP TABLE IF EXISTS `paymentmethods`;
CREATE TABLE `paymentmethods` (
  `idpaymentmethods` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `method` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`idpaymentmethods`),
  KEY `FK_paymentmethods_1` (`status`),
  KEY `FK_paymentmethods_2` (`createdBy`),
  CONSTRAINT `FK_paymentmethods_1` FOREIGN KEY (`status`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_paymentmethods_2` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paymentmethods`
--

/*!40000 ALTER TABLE `paymentmethods` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentmethods` ENABLE KEYS */;


--
-- Definition of table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments` (
  `idpayments` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `methodcode` int(10) unsigned NOT NULL,
  `transactionID` int(10) unsigned NOT NULL,
  `paymentAmount` int(10) unsigned NOT NULL,
  `otherdetails` varchar(200) NOT NULL,
  PRIMARY KEY (`idpayments`),
  KEY `FK_payments_1` (`transactionID`),
  KEY `FK_payments_2` (`methodcode`),
  CONSTRAINT `FK_payments_1` FOREIGN KEY (`transactionID`) REFERENCES `transactions` (`idtransactions`),
  CONSTRAINT `FK_payments_2` FOREIGN KEY (`methodcode`) REFERENCES `paymentmethods` (`idpaymentmethods`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;


--
-- Definition of table `productcategory`
--

DROP TABLE IF EXISTS `productcategory`;
CREATE TABLE `productcategory` (
  `idproductcategory` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`idproductcategory`),
  KEY `FK_productcategory_1` (`createdBy`),
  KEY `FK_productcategory_2` (`statusID`),
  CONSTRAINT `FK_productcategory_1` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_productcategory_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `productcategory`
--

/*!40000 ALTER TABLE `productcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `productcategory` ENABLE KEYS */;


--
-- Definition of table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `idproducts` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `category` int(10) unsigned NOT NULL,
  `wholesaleprice` int(10) unsigned NOT NULL,
  `retailprice` int(10) unsigned NOT NULL,
  `createdby` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  `stockedQTY` int(10) unsigned NOT NULL,
  `imageurl` varchar(200) NOT NULL,
  PRIMARY KEY (`idproducts`),
  KEY `FK_products_1` (`category`),
  KEY `FK_products_2` (`createdby`),
  KEY `FK_products_3` (`statusID`),
  CONSTRAINT `FK_products_1` FOREIGN KEY (`category`) REFERENCES `productcategory` (`idproductcategory`),
  CONSTRAINT `FK_products_2` FOREIGN KEY (`createdby`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_products_3` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;


--
-- Definition of table `producttransaction`
--

DROP TABLE IF EXISTS `producttransaction`;
CREATE TABLE `producttransaction` (
  `idproducttransaction` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `transactionID` int(10) unsigned NOT NULL,
  `productID` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idproducttransaction`),
  KEY `FK_producttransaction_1` (`statusID`),
  CONSTRAINT `FK_producttransaction_1` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producttransaction`
--

/*!40000 ALTER TABLE `producttransaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `producttransaction` ENABLE KEYS */;


--
-- Definition of table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `idstatus` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idstatus`),
  KEY `FK_status_1` (`createdBy`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`idstatus`,`name`,`description`,`createdBy`) VALUES 
 (1,'Active','the user is active',NULL);
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


--
-- Definition of table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `idtransactions` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `outletID` int(10) unsigned NOT NULL,
  `staffID` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  `wholesaleprice` int(10) unsigned NOT NULL,
  `retailprice` int(10) unsigned NOT NULL,
  `otherdetails` varchar(200) NOT NULL,
  PRIMARY KEY (`idtransactions`),
  KEY `FK_transactions_1` (`staffID`),
  KEY `FK_transactions_2` (`statusID`),
  KEY `FK_transactions_3` (`outletID`),
  CONSTRAINT `FK_transactions_1` FOREIGN KEY (`staffID`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_transactions_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`),
  CONSTRAINT `FK_transactions_3` FOREIGN KEY (`outletID`) REFERENCES `outlet` (`idoutlet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `idusers` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `pword` varchar(45) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `department` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` int(10) unsigned NOT NULL,
  `staffID` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idusers`) USING BTREE,
  KEY `FK_user_1` (`createdBy`),
  KEY `FK_user_2` (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idusers`,`username`,`pword`,`createdAt`,`lastLogin`,`department`,`groupID`,`name`,`email`,`phone`,`staffID`,`statusID`,`createdBy`) VALUES 
 (1,'pos','1234','2019-01-11 16:19:19','2019-01-11 16:19:19',1,1,'Amon','chepseron@gmail.com',728140544,1,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `idgroups` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `responsibilities` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idgroups`),
  KEY `FK_groups_1` (`statusID`),
  KEY `FK_groups_2` (`createdBy`),
  CONSTRAINT `FK_group_1` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_group_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usergroup`
--

/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
INSERT INTO `usergroup` (`idgroups`,`name`,`createdAt`,`createdBy`,`statusID`,`responsibilities`) VALUES 
 (1,'Admin','1000-01-01 00:00:00',NULL,1,'ALL'),
 (2,'Seller','2019-01-11 16:20:01',1,1,'ALL');
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
