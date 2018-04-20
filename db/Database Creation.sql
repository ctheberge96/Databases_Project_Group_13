-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Creator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Creator` (
  `CreatorID` INT UNSIGNED NOT NULL,
  `CreatorTotalPlays` INT(12) NULL,
  `CreatorBankRouting` VARCHAR(9) NULL,
  `CreatorBankAccountNumber` VARCHAR(16) NULL,
  PRIMARY KEY (`CreatorID`),
  UNIQUE INDEX `CreatorID_UNIQUE` (`CreatorID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Media` (
  `MediaTitle` VARCHAR(45) NOT NULL,
  `MediaType` CHAR(1) NOT NULL,
  `MediaFileName` VARCHAR(50) NOT NULL,
  `MediaLastOpened` DATE NULL,
  `MediaDateCreated` DATE NOT NULL,
  `MediaCreatorID` INT UNSIGNED NOT NULL,
  `MediaViews` INT(9) NULL DEFAULT 0,
  PRIMARY KEY (`MediaTitle`),
  INDEX `MediaCreatorID_idx` (`MediaCreatorID` ASC),
  UNIQUE INDEX `MediaTitle_UNIQUE` (`MediaTitle` ASC),
  CONSTRAINT `MediaCreatorID`
    FOREIGN KEY (`MediaCreatorID`)
    REFERENCES `mydb`.`Creator` (`CreatorID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Music`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Music` (
  `MusicID` INT NOT NULL,
  `MusicRuntime` DECIMAL(8,2) NOT NULL,
  `MusicAlbumTitle` VARCHAR(45) NULL,
  PRIMARY KEY (`MusicID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Movie` (
  `MovieID` INT NOT NULL,
  `MovieLength` DECIMAL(8,2) NULL,
  `MovieDimensions` VARCHAR(11) NOT NULL,
  `MovieFrameRate` INT(3) NULL,
  PRIMARY KEY (`MovieID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Image` (
  `ImageID` INT NOT NULL,
  `Dimensions` VARCHAR(11) NULL,
  PRIMARY KEY (`ImageID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Tag` (
  `TagName` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`TagName`),
  UNIQUE INDEX `TagName_UNIQUE` (`TagName` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TaggedMedia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TaggedMedia` (
  `FK_FT_MediaTitle` VARCHAR(45) NOT NULL,
  `FK_FT_TagName` VARCHAR(20) NOT NULL,
  INDEX `TagName_idx` (`FK_FT_TagName` ASC),
  PRIMARY KEY (`FK_FT_TagName`, `FK_FT_MediaTitle`),
  INDEX `FK_MediaTitle_idx` (`FK_FT_MediaTitle` ASC),
  CONSTRAINT `FK_TagName`
    FOREIGN KEY (`FK_FT_TagName`)
    REFERENCES `mydb`.`Tag` (`TagName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_MediaTitle`
    FOREIGN KEY (`FK_FT_MediaTitle`)
    REFERENCES `mydb`.`Media` (`MediaTitle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `UserID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(30) NOT NULL,
  `UserPassword` VARCHAR(30) NOT NULL,
  `UserCreatorStatus` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `UserID_UNIQUE` (`UserID` ASC),
  UNIQUE INDEX `UserName_UNIQUE` (`UserName` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FavoritedMedia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`FavoritedMedia` (
  `FK_FM_MediaID` INT UNSIGNED NOT NULL,
  `FK_FM_UserID` INT UNSIGNED NOT NULL,
  INDEX `UserID_idx` (`FK_FM_UserID` ASC),
  PRIMARY KEY (`FK_FM_MediaID`, `FK_FM_UserID`),
  CONSTRAINT `FK_FM_UserID`
    FOREIGN KEY (`FK_FM_UserID`)
    REFERENCES `mydb`.`User` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FollowedTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`FollowedTag` (
  `FK_FT_TagName` VARCHAR(20) NOT NULL,
  `FK_FT_UserID` INT UNSIGNED NOT NULL,
  INDEX `UserID_idx` (`FK_FT_UserID` ASC),
  INDEX `TagName_idx` (`FK_FT_TagName` ASC),
  UNIQUE INDEX `FK_TagName_UNIQUE` (`FK_FT_TagName` ASC),
  UNIQUE INDEX `FK_UserID_UNIQUE` (`FK_FT_UserID` ASC),
  PRIMARY KEY (`FK_FT_TagName`, `FK_FT_UserID`),
  CONSTRAINT `FK_FT_TagName`
    FOREIGN KEY (`FK_FT_TagName`)
    REFERENCES `mydb`.`Tag` (`TagName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_FT_UserID`
    FOREIGN KEY (`FK_FT_UserID`)
    REFERENCES `mydb`.`User` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
