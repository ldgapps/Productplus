DROP DATABASE IF EXISTS `product`;
CREATE DATABASE `product`;
CREATE TABLE `product`.`viveres`( 
	`codigo` VARCHAR(25) NOT NULL,
	`producto` VARCHAR(30) NOT NULL ,
	`marca` VARCHAR(40) NOT NULL,
	`cantidad` FLOAT(25) NOT NULL,
	`costo` FLOAT(45) NOT NULL,
	`precio` FLOAT(45) NOT NULL,
	`costo total` FLOAT(25) NOT NULL,
	`precio total` FLOAT(25) NOT NULL,
	PRIMARY KEY (`codigo`)) ENGINE = InnoDB;
CREATE TABLE `product`.`charcuteria`( 
	`codigo` VARCHAR(25) NOT NULL,
	`producto` VARCHAR(30) NOT NULL ,
	`marca` VARCHAR(40) NOT NULL,
	`peso` FLOAT(25) NOT NULL,
	`costo` FLOAT(45) NOT NULL,
	`precio` FLOAT(45) NOT NULL,
	`costo total` FLOAT(25) NOT NULL,
	`precio total` FLOAT(25) NOT NULL,
	PRIMARY KEY (`codigo`)) ENGINE = InnoDB;
CREATE TABLE `product`.`usuarios`(
	`id` INT(25) NOT NULL AUTO_INCREMENT ,
	`nombre` VARCHAR(25) NOT NULL UNIQUE,
	`pass` VARCHAR(30) NOT NULL,
	`tipo` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT=1;
CREATE TABLE `product`.`ventas` (
	`codigo` VARCHAR(100) NOT NULL ,
	`tipo` VARCHAR(100) NOT NULL ,
	`productos` VARCHAR(600) NOT NULL,
	`cantidad` VARCHAR(100) NOT NULL,
	`precio` VARCHAR(100) NOT NULL,
	`precio t` VARCHAR(100) NOT NULL,
	`total` VARCHAR(100) NOT NULL,
	`tipo de pago`  VARCHAR(50)  NOT NULL,
		`cliente`  VARCHAR(50)  NOT NULL,
	`ci` VARCHAR(30) NOT NULL,
	`referencia`  VARCHAR(50)  NOT NULL,
	`fecha` DATE NOT NULL,
	`hora` TIME NOT NULL) ENGINE = InnoDB;
CREATE TABLE `product`.`proveedores`(
	`id` INT(50) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(40) NOT NULL , 
	`rif` VARCHAR(25) NOT NULL,
	`telefono` VARCHAR(20) NOT NULL,
	`ciudad` VARCHAR(25) NOT NULL,
	`email` VARCHAR(30) NOT NULL,
	`banco` VARCHAR(25) NOT NULL,
	`tipo de cuenta` VARCHAR(50) NOT NULL,
	`cuenta` VARCHAR(50) NOT NULL,
	`productos` VARCHAR(400) NOT NULL,
	PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT=1;
CREATE TABLE `product`.`cuentas por pagar` (
	`itm` INT(15) NOT NULL AUTO_INCREMENT,
 	`codigo` VARCHAR(100) NOT NULL ,
 	`tipo` VARCHAR(100) NOT NULL ,
 	`producto` VARCHAR(600) NOT NULL,
 	`cantidad` VARCHAR(100) NOT NULL,
 	`precio u` VARCHAR(100) NOT NULL,
 	`precio t` VARCHAR(100) NOT NULL,
 	`total` VARCHAR(45) NOT NULL,
 	`abono` VARCHAR(45) NOT NULL,
 	`resta` VARCHAR(45) NOT NULL,
 	`proveedor`  VARCHAR(50)  NOT NULL,
 	`fecha` DATE NOT NULL,
 	`hora` TIME NOT NULL,
 	`fecha tope` VARCHAR(30) NOT NULL,
 	`factura` VARCHAR(15) UNIQUE NOT NULL ,
 	`forma de pago` VARCHAR(45) NOT NULL,
 	`referencia` VARCHAR(50) NOT NULL,
 	PRIMARY KEY (`itm`)) ENGINE = InnoDB AUTO_INCREMENT=1;
CREATE TABLE `product`.`cuentas por cobrar` (
	`itm` INT(15) NOT NULL AUTO_INCREMENT,
	`codigo` VARCHAR(100) NOT NULL ,
	`tipo` VARCHAR(100) NOT NULL ,
	`productos` VARCHAR(600) NOT NULL,
	`cantidad` VARCHAR(100) NOT NULL,
	`precio` VARCHAR(100) NOT NULL,
	`precio t` VARCHAR(100) NOT NULL,
	`total` VARCHAR(100) NOT NULL,
	`abono` VARCHAR(45) NOT NULL,
 	`resta` VARCHAR(45) NOT NULL,
	`tipo de pago`  VARCHAR(50)  NOT NULL,
	`cliente`  VARCHAR(50)  NOT NULL,
	`ci` VARCHAR(30) NOT NULL,
	`referencia`  VARCHAR(50)  NOT NULL,
	`fecha` DATE NOT NULL,
	`hora` TIME NOT NULL,
	`fecha tope` VARCHAR(30) NOT NULL,
	PRIMARY KEY (`itm`)) ENGINE = InnoDB AUTO_INCREMENT=1;
CREATE TABLE `product`.`productos`(
	`codigo` VARCHAR(25) NOT NULL,
	`producto` VARCHAR(30) NOT NULL, 
	`marca` VARCHAR(40) NOT NULL,
	`tipo` VARCHAR(25) NOT NULL,
	PRIMARY KEY (`codigo`)) ENGINE = InnoDB;
CREATE TABLE `product`.`nombre`(
	`empresa` VARCHAR(45) NOT NULL) ENGINE = InnoDB;
CREATE TABLE `product`.`consumo interno` (
	`codigo` VARCHAR(25) NOT NULL,
	`producto` VARCHAR(60) NOT NULL,
	`cantidad` INT(15) NOT NULL,
	`costo` FLOAT(45) NOT NULL,
	`costo total` FLOAT(45) NOT NULL,
	`fecha` DATE NOT NULL,
	`hora` TIME NOT NULL) ENGINE = InnoDB;
CREATE TABLE `product`.`perdidas` (
	`codigo` VARCHAR(50) NOT NULL,
	`producto` VARCHAR(60) NOT NULL,
	`cantidad` INT(15) NOT NULL,
	`costo` FLOAT(45) NOT NULL,
	`causa` VARCHAR(45) NOT NULL,
	`costo total` FLOAT(45) NOT NULL,
	`fecha` DATE NOT NULL) ENGINE = InnoDB;
INSERT INTO `product`.`usuarios` (`nombre`, `pass`,`tipo`) VALUES ('QnUSjnGQVJY=', 'jllNPAhT46s=', 'admin');
INSERT INTO `product`.`nombre` (`empresa`) VALUES ('LDGApps');