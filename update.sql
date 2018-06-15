-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kbellfire
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kbellfire` ;

-- -----------------------------------------------------
-- Schema kbellfire
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kbellfire` DEFAULT CHARACTER SET utf8 ;
USE `kbellfire` ;

-- -----------------------------------------------------
-- Table `kbellfire`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`category` (
  `category_ID` SMALLINT(10) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`category_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`contact` (
  `contactid` SMALLINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`contactid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`contractor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`contractor` (
  `contractorid` SMALLINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `contact` VARCHAR(5) NULL DEFAULT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(10) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`contractorid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`job` (
  `jobid` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zip` VARCHAR(45) NOT NULL,
  `bidamount` INT(11) NULL DEFAULT '0',
  `status` VARCHAR(45) NOT NULL DEFAULT 'Active',
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`jobid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`user` (
  `userid` SMALLINT(10) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `name` VARCHAR(25) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`supplier` (
  `supplierid` SMALLINT(10) NOT NULL AUTO_INCREMENT,
  `companyname` VARCHAR(120) NOT NULL,
  `contact` VARCHAR(129) NOT NULL,
  `address1` VARCHAR(120) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(2) NOT NULL,
  `postalcode` VARCHAR(16) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `fax` VARCHAR(45) NOT NULL,
  `terms` VARCHAR(45) NOT NULL,
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`supplierid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`purchaseorder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`purchaseorder` (
  `orderid` INT(11) NOT NULL AUTO_INCREMENT,
  `supplier` SMALLINT(10) NOT NULL,
  `job` INT(11) NOT NULL,
  `expectedby` DATETIME NULL DEFAULT NULL,
  `contact` SMALLINT(10) NOT NULL,
  `quote` INT(11) NULL DEFAULT NULL,
  `quotedate` DATETIME NULL DEFAULT NULL,
  `subtotal` DECIMAL(10,2) NOT NULL DEFAULT '0.00',
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `createdby` SMALLINT(10) NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shipto` INT(11) NOT NULL,
  `bldg` VARCHAR(45) NULL DEFAULT ' ', 
  `status` VARCHAR(45) NOT NULL DEFAULT 'Pending',
  `currentTax` DECIMAL(10,2) NOT NULL,
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  INDEX `creator_idx` (`createdby` ASC),
  INDEX `supplier_idx` (`supplier` ASC),
  INDEX `pojob_idx` (`job` ASC),
  INDEX `shipjob_idx` (`shipto` ASC),
  INDEX `shipjob_id` (`shipto` ASC),
  INDEX `shipto_idx` (`shipto` ASC),
  INDEX `pojob_id` (`job` ASC),
  INDEX `contact_idx` (`contact` ASC),
  CONSTRAINT `contact`
    FOREIGN KEY (`contact`)
    REFERENCES `kbellfire`.`contact` (`contactid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `creator`
    FOREIGN KEY (`createdby`)
    REFERENCES `kbellfire`.`user` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pojob`
    FOREIGN KEY (`job`)
    REFERENCES `kbellfire`.`job` (`jobid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `supplier`
    FOREIGN KEY (`supplier`)
    REFERENCES `kbellfire`.`supplier` (`supplierid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5000
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`creditmemo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`creditmemo` (
  `memoid` INT(11) NOT NULL AUTO_INCREMENT,
  `poid` INT(11) NOT NULL,
  `supplier` SMALLINT(10) NOT NULL,
  `job` INT(11) NOT NULL,
  `subTotal` DECIMAL(10,2) NOT NULL,
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `createdby` SMALLINT(10) NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `currentTax` DECIMAL(10,2) NOT NULL,
  `status` VARCHAR(45) NULL DEFAULT 'Pending',
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`memoid`),
  INDEX `poid_id` (`poid` ASC),
  INDEX `poid_idcm` (`poid` ASC),
  INDEX `supplierid_idcm` (`supplier` ASC),
  INDEX `createdby_idcm` (`createdby` ASC),
  INDEX `cmjob_idx` (`job` ASC),
  CONSTRAINT `cmjob`
    FOREIGN KEY (`job`)
    REFERENCES `kbellfire`.`job` (`jobid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `createdby`
    FOREIGN KEY (`createdby`)
    REFERENCES `kbellfire`.`user` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `poid`
    FOREIGN KEY (`poid`)
    REFERENCES `kbellfire`.`purchaseorder` (`orderid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `supplierid`
    FOREIGN KEY (`supplier`)
    REFERENCES `kbellfire`.`supplier` (`supplierid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1000
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`productdescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`productdescription` (
  `pdescID` INT(10) NOT NULL AUTO_INCREMENT,
  `productDescription` VARCHAR(60) NOT NULL,
  `productsize` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`pdescID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `category_id` SMALLINT(10) NOT NULL,
  `description` INT(10) NOT NULL,
  `unitMeasure` VARCHAR(5) NOT NULL,
  `part_id` VARCHAR(15) NULL DEFAULT NULL,
  `manufacturer` VARCHAR(15) NULL DEFAULT NULL,
  `supplier` SMALLINT(10) NOT NULL,
  `price` DECIMAL(10,3) NOT NULL,
  `status` VARCHAR(45) NULL DEFAULT 'Active',
  `lastchange` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `category_id` (`category_id` ASC),
  INDEX `supplier_idx` (`supplier` ASC),
  INDEX `prodSupplier_idx` (`supplier` ASC),
  INDEX `prodDescript_idx` (`description` ASC),
  CONSTRAINT `prodDescript`
    FOREIGN KEY (`description`)
    REFERENCES `kbellfire`.`productdescription` (`pdescID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `prodSupplier`
    FOREIGN KEY (`supplier`)
    REFERENCES `kbellfire`.`supplier` (`supplierid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_ibfk_2`
    FOREIGN KEY (`category_id`)
    REFERENCES `kbellfire`.`category` (`category_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`creditmemodetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`creditmemodetail` (
  `detailid` INT(11) NOT NULL AUTO_INCREMENT,
  `creditmemoid` INT(11) NOT NULL,
  `product` INT(11) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `qty` INT(11) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`detailid`),
  INDEX `product_idx` (`product` ASC),
  INDEX `credit_idx` (`creditmemoid` ASC),
  CONSTRAINT `credit`
    FOREIGN KEY (`creditmemoid`)
    REFERENCES `kbellfire`.`creditmemo` (`memoid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `productcm`
    FOREIGN KEY (`product`)
    REFERENCES `kbellfire`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`purchaseorderdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`purchaseorderdetails` (
  `detailsid` INT(11) NOT NULL AUTO_INCREMENT,
  `orderid` INT(11) NOT NULL,
  `product` INT(11) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `orderqty` INT(11) NOT NULL,
  `receivedqty` INT(11) NOT NULL DEFAULT '0',
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`detailsid`),
  INDEX `product_idx` (`product` ASC),
  INDEX `poid_idx` (`orderid` ASC),
  CONSTRAINT `orderid`
    FOREIGN KEY (`orderid`)
    REFERENCES `kbellfire`.`purchaseorder` (`orderid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product`
    FOREIGN KEY (`product`)
    REFERENCES `kbellfire`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbellfire`.`tax`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbellfire`.`tax` (
  `tax` DECIMAL(10,2) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



-- Run this script to load jobs
LOAD DATA local INFILE 'C:/temp/job.csv'
 into table job
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (jobid, name, address, city, state, zip, bidamount, status);
 
 -- Run this script to load contacts
 LOAD DATA local INFILE 'C:/temp/contact.csv'
 into table contact
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (name,phone);

-- Run this script to load suppliers
LOAD DATA local INFILE 'C:/temp/supplier.csv'
 into table supplier
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (companyname, contact, address1, city, state, postalcode, phone, fax, terms);

-- Run this script to load categories
LOAD DATA local INFILE 'C:/temp/category2.csv'
 into table category
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (description);
 
 -- Run this for product descriptions
  LOAD DATA local INFILE 'C:/temp/prodDesc.csv'
 into table productdescription
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (pdescID,productDescription,productSize);

-- Run this script to load sample of products
LOAD DATA local INFILE 'C:/temp/fireprod.csv'
 into table product
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 ESCAPED BY ''
 LINES TERMINATED BY '\n'
 (category_id,description,unitMeasure,manufacturer,part_id,supplier,price,status);

