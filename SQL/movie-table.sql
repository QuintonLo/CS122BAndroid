CREATE DATABASE moviedb;
USE moviedb;

CREATE TABLE `moviedb`.`movies` (
  `id` VARCHAR(10) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `year` INT NOT NULL,
  `director` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`stars` (
  `id` VARCHAR(10) NOT NULL,  
  `name` VARCHAR(100) NOT NULL,
  `birthYear` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`stars_in_movies` (
  `starId` VARCHAR(10) NOT NULL,
  `movieId` VARCHAR(10) NOT NULL,
  FOREIGN KEY (`starId`) REFERENCES stars (`id`),
  FOREIGN KEY (`movieId`) REFERENCES movies (`id`));

CREATE TABLE `moviedb`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`genres_in_movies` (
  `genreId` INT NOT NULL,
  `movieId` VARCHAR(10) NOT NULL,
  FOREIGN KEY (`genreId`) REFERENCES genres (`id`),
  FOREIGN KEY (`movieId`) REFERENCES movies (`id`));

CREATE TABLE `moviedb`.`creditcards` (
  `id` VARCHAR(20) NOT NULL,
  `firstName` VARCHAR(50) NOT NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `expiration`DATE NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(50) NOT NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `ccId` VARCHAR(20) NOT NULL,
  `address` VARCHAR(200) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`ccId`) REFERENCES creditcards (`id`));

CREATE TABLE `moviedb`.`sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customerId` INT NOT NULL,
  `movieId` VARCHAR(10) NOT NULL,
  `saleDate` DATE NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (customerId) REFERENCES customers (`id`),
  FOREIGN KEY (movieId) REFERENCES movies (`id`));

CREATE TABLE `moviedb`.`ratings` (
  `movieId` VARCHAR(10) NOT NULL,
  `rating` FLOAT(3,1) NOT NULL,
  `numVotes` INT NOT NULL);