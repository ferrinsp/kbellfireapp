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



