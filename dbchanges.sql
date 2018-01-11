-- Changes made on 1/4/18 to kbell db
ALTER TABLE `kbell`.`purchaseorderdetails` 
CHANGE COLUMN `qty` `orderqty` INT(11) NOT NULL ,
ADD COLUMN `receivedqty` INT(11) NOT NULL DEFAULT 0 AFTER `orderqty`;

ALTER TABLE `kbell`.`supplier` 
ADD COLUMN `comments` VARCHAR(120) NULL DEFAULT NULL AFTER `terms`;

ALTER TABLE `kbell`.`user` 
DROP COLUMN `lastName`,
CHANGE COLUMN `firstName` `name` VARCHAR(25) NOT NULL ;

-- Auto increments

ALTER TABLE `kbell`.`purchaseorderdetails` 
CHANGE COLUMN `detailsid` `detailsid` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `kbell`.`purchaseorderdetails` 
DROP FOREIGN KEY `orderid`;
ALTER TABLE `kbell`.`purchaseorderdetails` 
DROP INDEX `oderid_idx` ;

ALTER TABLE `kbell`.`creditmemo` 
DROP FOREIGN KEY `poid`;
ALTER TABLE `kbell`.`creditmemo` 
DROP INDEX `poid_idx` ;

ALTER TABLE `kbell`.`purchaseorder` 
CHANGE COLUMN `orderid` `orderid` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `kbell`.`purchaseorderdetails` 
ADD CONSTRAINT `orderid`
  FOREIGN KEY (`orderid`)
  REFERENCES `kbell`.`purchaseorder` (`orderid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `kbell`.`creditmemo` 
ADD CONSTRAINT `poid`
  FOREIGN KEY (`poid`)
  REFERENCES `kbell`.`purchaseorder` (`orderid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `kbell`.`creditmemodetail` 
DROP FOREIGN KEY `credit`;
ALTER TABLE `kbell`.`creditmemodetail` 
DROP INDEX `creditmemoid_idx` ;

ALTER TABLE `kbell`.`creditmemo` 
CHANGE COLUMN `memoid` `memoid` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `kbell`.`creditmemodetail` 
ADD INDEX `credit_idx` (`creditmemoid` ASC);
ALTER TABLE `kbell`.`creditmemodetail` 
ADD CONSTRAINT `credit`
  FOREIGN KEY (`creditmemoid`)
  REFERENCES `kbell`.`creditmemo` (`memoid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- These changes were made to the startup sql script but not current working databases due to large number of FK constraints.  
-- ALTER TABLE `kbell`.`job` 
-- CHANGE COLUMN `jobid` `jobid` INT(11) NOT NULL AUTO_INCREMENT ;
-- ALTER TABLE `kbell`.`user` 
-- CHANGE COLUMN `userid` `userid` SMALLINT(10) NOT NULL AUTO_INCREMENT ;

-- Will need to change auto_incremements for tables when indexing is determined
-- ex. Alter 'table' auto_increment = 100;

-- 1/5/18
ALTER TABLE `kbell`.`product` 
CHANGE COLUMN `part_id` `part_id` VARCHAR(15) NULL ,
CHANGE COLUMN `manufacturer` `manufacturer` VARCHAR(15) NULL ;

ALTER TABLE `kbell`.`job` 
ADD COLUMN `comments` VARCHAR(120) NULL DEFAULT NULL AFTER `status`;


-- 1/7/18 Add indexing for job
ALTER TABLE `kbell`.`creditmemo` 
DROP FOREIGN KEY `jobcm`;
ALTER TABLE `kbell`.`creditmemo` 
DROP INDEX `jobcm_idx` ;

ALTER TABLE `kbell`.`purchaseorder` 
DROP FOREIGN KEY `pojob`;
ALTER TABLE `kbell`.`purchaseorder` 
DROP INDEX `pojob_idx` ;

ALTER TABLE `kbell`.`purchaseorder` 
DROP FOREIGN KEY `shipto`;
ALTER TABLE `kbell`.`purchaseorder` 
DROP INDEX `shipto_idx` ;

ALTER TABLE `kbell`.`job` 
CHANGE COLUMN `jobid` `jobid` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `kbell`.`creditmemo` 
ADD INDEX `cmjob_idx` (`job` ASC);
ALTER TABLE `kbell`.`creditmemo` 
ADD CONSTRAINT `cmjob`
  FOREIGN KEY (`job`)
  REFERENCES `kbell`.`job` (`jobid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `kbell`.`purchaseorder` 
ADD INDEX `pojob_id` (`job` ASC);
ALTER TABLE `kbell`.`purchaseorder` 
ADD CONSTRAINT `pojob`
  FOREIGN KEY (`job`)
  REFERENCES `kbell`.`job` (`jobid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `kbell`.`purchaseorder` 
ADD CONSTRAINT `poshipjob`
  FOREIGN KEY (`job`)
  REFERENCES `kbell`.`job` (`jobid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;  
  
-- 1/8/18
ALTER TABLE `kbell`.`product` 
CHANGE COLUMN `supplier` `supplier` SMALLINT(10) NOT NULL ,
ADD INDEX `prodSupplier_idx` (`supplier` ASC);
ALTER TABLE `kbell`.`product` 
ADD CONSTRAINT `prodSupplier`
  FOREIGN KEY (`supplier`)
  REFERENCES `kbell`.`supplier` (`supplierid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
-- 1/9/18
ALTER TABLE `kbell`.`product` 
ADD COLUMN `unitMeasure` VARCHAR(5) NOT NULL AFTER `description`;

LOAD DATA local INFILE 'C:/temp/contact.csv'
 into table contact
 FIELDS TERMINATED BY ','
 ENCLOSED BY '"'
 LINES TERMINATED BY '\n'
 (name,phone);
 
CREATE TABLE `kbell`.`productdescription` (
  `pdescID` INT(10) NOT NULL AUTO_INCREMENT,
  `productDescription` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pdescID`));
ALTER TABLE `kbell`.`productdescription` 
ADD COLUMN `productsize` VARCHAR(45) NULL AFTER `productDescription`;

-- 1/11/18  Added contact table/po information
CREATE TABLE `kbell`.`contact` (
  `contactid` SMALLINT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`contactid`));
  
ALTER TABLE `kbell`.`purchaseorder` 
CHANGE COLUMN `contact` `contact` SMALLINT(10) NOT NULL ,
ADD INDEX `contact_idx` (`contact` ASC);
ALTER TABLE `kbell`.`purchaseorder` 
ADD CONSTRAINT `contact`
  FOREIGN KEY (`contact`)
  REFERENCES `kbell`.`contact` (`contactid`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
