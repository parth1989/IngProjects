-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for payrolldb
CREATE DATABASE IF NOT EXISTS `payrolldb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `payrolldb`;

-- Dumping structure for table payrolldb.associate
CREATE TABLE IF NOT EXISTS `associate` (
  `associateId` int(11) NOT NULL AUTO_INCREMENT,
  `yearlyInvestmentUnder8oC` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `department` varchar(50) NOT NULL,
  `designation` varchar(50) NOT NULL,
  `pancard` varchar(50) NOT NULL,
  `emailId` varchar(50) NOT NULL,
  PRIMARY KEY (`associateId`)
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=latin1;

-- Dumping data for table payrolldb.associate: ~3 rows (approximately)
/*!40000 ALTER TABLE `associate` DISABLE KEYS */;
INSERT INTO `associate` (`associateId`, `yearlyInvestmentUnder8oC`, `firstName`, `lastName`, `department`, `designation`, `pancard`, `emailId`) VALUES
	(1005, 3453, 'natwar0', 'garg', 'ADM', 'S CON', 'AGHYR600J', 'n.g@gmail.com'),
	(1007, 3453, 'natwar0', 'garg', 'ADM', 'S CON', 'AGHYR600J', 'n.g@gmail.com'),
	(1009, 78999, 'NIlesh', 'Patil', 'ADM', 'Manager', 'ohhoh73763', 'nilesh.patil@capgemini.com');
/*!40000 ALTER TABLE `associate` ENABLE KEYS */;

-- Dumping structure for table payrolldb.bankdetails
CREATE TABLE IF NOT EXISTS `bankdetails` (
  `associateId` int(11) DEFAULT NULL,
  `accountNo` int(11) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `ifscCode` varchar(50) DEFAULT NULL,
  KEY `Associate_BankDetails_Fk` (`associateId`),
  CONSTRAINT `Associate_BankDetails_Fk` FOREIGN KEY (`associateId`) REFERENCES `associate` (`associateId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table payrolldb.bankdetails: ~4 rows (approximately)
/*!40000 ALTER TABLE `bankdetails` DISABLE KEYS */;
INSERT INTO `bankdetails` (`associateId`, `accountNo`, `bankName`, `ifscCode`) VALUES
	(1005, 67578909, 'ICICI', 'GHIJ00076'),
	(1007, 67578909, 'ICICI', 'GHIJ00076'),
	(1009, 645454, 'ICICI', 'ICICI8437');
/*!40000 ALTER TABLE `bankdetails` ENABLE KEYS */;

-- Dumping structure for table payrolldb.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `employeeId` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `basicSalary` int(11) NOT NULL,
  `totalSalary` int(11) NOT NULL,
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table payrolldb.employee: ~0 rows (approximately)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

-- Dumping structure for table payrolldb.salary
CREATE TABLE IF NOT EXISTS `salary` (
  `associateId` int(11) DEFAULT NULL,
  `basicSalary` int(11) DEFAULT NULL,
  `hra` int(11) DEFAULT NULL,
  `conveyanceAllowance` int(11) DEFAULT NULL,
  `otherAllowance` int(11) DEFAULT NULL,
  `personalAllowance` int(11) DEFAULT NULL,
  `monthlyTax` int(11) DEFAULT NULL,
  `epf` int(11) DEFAULT NULL,
  `companyPf` int(11) DEFAULT NULL,
  `gratuity` int(11) DEFAULT NULL,
  `grossSalary` int(11) DEFAULT NULL,
  `netSalary` int(11) DEFAULT NULL,
  KEY `Associate_Salary` (`associateId`),
  CONSTRAINT `Associate_Salary` FOREIGN KEY (`associateId`) REFERENCES `associate` (`associateId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table payrolldb.salary: ~3 rows (approximately)
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` (`associateId`, `basicSalary`, `hra`, `conveyanceAllowance`, `otherAllowance`, `personalAllowance`, `monthlyTax`, `epf`, `companyPf`, `gratuity`, `grossSalary`, `netSalary`) VALUES
	(1005, 6878789, 0, 0, 0, 0, 0, 2254, 3454, 0, 0, 0),
	(1007, 6878789, 0, 0, 0, 0, 0, 2254, 3454, 0, 0, 0),
	(1009, 30000, 0, 0, 0, 0, 0, 1800, 1800, 0, 0, 0);
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
