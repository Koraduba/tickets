-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema final
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema final
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `final` DEFAULT CHARACTER SET utf8 ;
USE `final` ;

-- -----------------------------------------------------
-- Table `final`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `role` INT NULL,
  INDEX `fk_user_user_type1_idx` (`role` ASC),
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_user_type1`
    FOREIGN KEY (`role`)
    REFERENCES `final`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `date` BIGINT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_user1_idx` (`user` ASC),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user`)
    REFERENCES `final`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`venue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`venue` (
  `venue_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `capacity` INT NULL,
  `layout` VARCHAR(145) NULL,
  PRIMARY KEY (`venue_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`event` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `date` BIGINT NULL,
  `description` TEXT NULL,
  `image` VARCHAR(145) NULL,
  `venue` INT NULL,
  `owner` INT NOT NULL,
  PRIMARY KEY (`event_id`),
  INDEX `fk_event_venue1_idx` (`venue` ASC),
  INDEX `fk_event_user1_idx` (`owner` ASC),
  CONSTRAINT `fk_event_venue1`
    FOREIGN KEY (`venue`)
    REFERENCES `final`.`venue` (`venue_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user1`
    FOREIGN KEY (`owner`)
    REFERENCES `final`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `event` INT NULL,
  `price` DECIMAL NULL,
  `category` INT NULL,
  PRIMARY KEY (`ticket_id`),
  INDEX `fk_ticket_event1_idx` (`event` ASC),
  INDEX `fk_ticket_ticketcat1_idx` (`category` ASC),
  CONSTRAINT `fk_ticket_event1`
    FOREIGN KEY (`event`)
    REFERENCES `final`.`event` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_ticketcat1`
    FOREIGN KEY (`category`)
    REFERENCES `final`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final`.`order_line`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final`.`order_line` (
  `order_line_id` INT NOT NULL AUTO_INCREMENT,
  `ticket` INT NULL,
  `quantity` INT NULL,
  `order` INT NULL,
  PRIMARY KEY (`order_line_id`),
  INDEX `fk_order_line_ticket1_idx` (`ticket` ASC),
  INDEX `fk_order_line_order1_idx` (`order` ASC),
  CONSTRAINT `fk_order_line_ticket1`
    FOREIGN KEY (`ticket`)
    REFERENCES `final`.`ticket` (`ticket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_line_order1`
    FOREIGN KEY (`order`)
    REFERENCES `final`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
