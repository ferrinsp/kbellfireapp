-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kbell
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kbell
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kbell` DEFAULT CHARACTER SET utf8 ;
USE `kbell` ;

-- -----------------------------------------------------
-- Table `kbell`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`category` (
  `category_ID` SMALLINT(10) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`category_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`user` (
  `userid` SMALLINT(10) NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `firstName` VARCHAR(25) NOT NULL,
  `lastName` VARCHAR(25) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`job` (
  `jobid` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zip` VARCHAR(45) NOT NULL,
  `bidamount` INT(11) NULL DEFAULT '0',
  `status` VARCHAR(45) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`jobid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`supplier` (
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
  PRIMARY KEY (`supplierid`))
ENGINE = InnoDB
AUTO_INCREMENT = 108
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`purchaseorder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`purchaseorder` (
  `orderid` INT(11) NOT NULL,
  `supplier` SMALLINT(10) NOT NULL,
  `job` INT(11) NOT NULL,
  `expectedby` DATETIME NULL DEFAULT NULL,
  `contact` VARCHAR(45) NULL DEFAULT NULL,
  `quote` INT(11) NULL DEFAULT NULL,
  `quotedate` DATETIME NULL DEFAULT NULL,
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `createdby` SMALLINT(10) NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shipto` INT(11) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'Pending',
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  INDEX `creator_idx` (`createdby` ASC),
  INDEX `supplier_idx` (`supplier` ASC),
  INDEX `shipto_idx` (`shipto` ASC),
  INDEX `pojob_idx` (`job` ASC),
  CONSTRAINT `creator`
    FOREIGN KEY (`createdby`)
    REFERENCES `kbell`.`user` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pojob`
    FOREIGN KEY (`job`)
    REFERENCES `kbell`.`job` (`jobid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shipto`
    FOREIGN KEY (`shipto`)
    REFERENCES `kbell`.`job` (`jobid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `supplier`
    FOREIGN KEY (`supplier`)
    REFERENCES `kbell`.`supplier` (`supplierid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`creditmemo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`creditmemo` (
  `memoid` INT(11) NOT NULL,
  `poid` INT(11) NOT NULL,
  `supplier` SMALLINT(10) NOT NULL,
  `job` INT(11) NOT NULL,
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `createdby` SMALLINT(10) NOT NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `comments` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`memoid`),
  INDEX `poid_idx` (`poid` ASC),
  INDEX `poid_id` (`poid` ASC),
  INDEX `poid_idcm` (`poid` ASC),
  INDEX `supplierid_idcm` (`supplier` ASC),
  INDEX `createdby_idcm` (`createdby` ASC),
  INDEX `jobcm_idx` (`job` ASC),
  CONSTRAINT `createdby`
    FOREIGN KEY (`createdby`)
    REFERENCES `kbell`.`user` (`userid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `jobcm`
    FOREIGN KEY (`job`)
    REFERENCES `kbell`.`job` (`jobid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `poid`
    FOREIGN KEY (`poid`)
    REFERENCES `kbell`.`purchaseorder` (`orderid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `supplierid`
    FOREIGN KEY (`supplier`)
    REFERENCES `kbell`.`supplier` (`supplierid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `category_id` SMALLINT(10) NOT NULL,
  `description` VARCHAR(64) NOT NULL,
  `part_id` VARCHAR(15) NOT NULL,
  `manufacturer` VARCHAR(15) NOT NULL,
  `supplier` VARCHAR(15) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `size` VARCHAR(25) NULL DEFAULT '1 Each',
  `lastchange` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `category_id` (`category_id` ASC),
  CONSTRAINT `product_ibfk_2`
    FOREIGN KEY (`category_id`)
    REFERENCES `kbell`.`category` (`category_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`creditmemodetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`creditmemodetail` (
  `detailid` INT(11) NOT NULL,
  `creditmemoid` INT(11) NOT NULL,
  `product` INT(11) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `qty` INT(11) NOT NULL,
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`detailid`),
  INDEX `creditmemoid_idx` (`creditmemoid` ASC),
  INDEX `product_idx` (`product` ASC),
  CONSTRAINT `credit`
    FOREIGN KEY (`creditmemoid`)
    REFERENCES `kbell`.`creditmemo` (`memoid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `productcm`
    FOREIGN KEY (`product`)
    REFERENCES `kbell`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kbell`.`purchaseorderdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kbell`.`purchaseorderdetails` (
  `detailsid` INT(11) NOT NULL,
  `orderid` INT(11) NOT NULL,
  `product` INT(11) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `qty` INT(11) NOT NULL,
  `tax` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`detailsid`),
  INDEX `oderid_idx` (`orderid` ASC),
  INDEX `product_idx` (`product` ASC),
  CONSTRAINT `orderid`
    FOREIGN KEY (`orderid`)
    REFERENCES `kbell`.`purchaseorder` (`orderid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product`
    FOREIGN KEY (`product`)
    REFERENCES `kbell`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
