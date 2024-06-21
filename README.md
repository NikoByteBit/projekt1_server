# Projekt1 - Zeiterfassungssystem

Dieses Projekt ist ein Zeiterfassungssystem, das die Arbeitszeiten und Pausen von Mitarbeitern erfasst und verwaltet. Die Anwendung bietet eine REST-API für die Ereignisverarbeitung und Zeitberechnung.

## Inhaltsverzeichnis

1. [Installation](#installation)
2. [Konfiguration](#konfiguration)
3. [API-Endpunkte](#api-endpunkte)
4. [Datenbankstruktur](#datenbankstruktur)
5. [Projektstruktur](#projektstruktur)
6. [Unterstützung](#unterstützung)

## Installation

### Voraussetzungen

- Java 17
- Maven
- MySQL

### Schritte

1. **Projekt klonen:**

   ```bash
   git clone <repository-url>
   cd MyProject
   ```
   
2. **Datenbank einrichten:**

Erstellen Sie eine MySQL-Datenbank mit dem Namen Arbeitszeiterfassung und führen Sie das schema.sql-Skript aus:

```bash
-- Skript zum Erstellen der Datenbankstrukturen für das Zeiterfassungssystem

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Arbeitszeiterfassung
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Arbeitszeiterfassung` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `Arbeitszeiterfassung` ;

-- -----------------------------------------------------
-- Table `Arbeitszeiterfassung`.`Mitarbeiter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Arbeitszeiterfassung`.`Mitarbeiter` ;

CREATE TABLE IF NOT EXISTS `Arbeitszeiterfassung`.`Mitarbeiter` (
                                                                    `mitarbeiter_id` INT NOT NULL AUTO_INCREMENT,
                                                                    `vorname` VARCHAR(50) NULL DEFAULT NULL,
    `nachname` VARCHAR(50) NULL DEFAULT NULL,
    `abteilung` VARCHAR(100) NULL DEFAULT NULL,
    `rolle` ENUM('Mitarbeiter', 'Abteilungsleiter') NOT NULL,
    PRIMARY KEY (`mitarbeiter_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- Modifying the Mitarbeiter table to add username and password columns
ALTER TABLE `Arbeitszeiterfassung`.`Mitarbeiter`
    ADD COLUMN `username` VARCHAR(50) NOT NULL,
ADD COLUMN `password` VARCHAR(100) NOT NULL,
ADD UNIQUE (`username`);

-- -----------------------------------------------------
-- Table `Arbeitszeiterfassung`.`Terminals`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Arbeitszeiterfassung`.`Terminals` ;

CREATE TABLE IF NOT EXISTS `Arbeitszeiterfassung`.`Terminals` (
                                                                  `terminal_id` INT NOT NULL AUTO_INCREMENT,
                                                                  `location` VARCHAR(255) NOT NULL,
    `description` TEXT NULL DEFAULT NULL,
    `connected_room_a` VARCHAR(255) NULL DEFAULT NULL,
    `type` ENUM('Eingang', 'Ausgang', 'Pausenraum', 'Zwischenraum') NOT NULL,
    `connected_room_b` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`terminal_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `Arbeitszeiterfassung`.`Ereignisse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Arbeitszeiterfassung`.`Ereignisse` ;

CREATE TABLE IF NOT EXISTS `Arbeitszeiterfassung`.`Ereignisse` (
                                                                   `ereignis_id` INT NOT NULL AUTO_INCREMENT,
                                                                   `mitarbeiter_id` INT NULL DEFAULT NULL,
                                                                   `terminal_id` INT NULL DEFAULT NULL,
                                                                   `event_type` ENUM('Eintritt', 'Austritt', 'Pausenbeginn', 'Pausenende') NULL DEFAULT NULL,
    `timestamp` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`ereignis_id`),
    INDEX `mitarbeiter_id` (`mitarbeiter_id` ASC) VISIBLE,
    INDEX `terminal_id` (`terminal_id` ASC) VISIBLE,
    CONSTRAINT `Ereignisse_ibfk_1`
    FOREIGN KEY (`mitarbeiter_id`)
    REFERENCES `Arbeitszeiterfassung`.`Mitarbeiter` (`mitarbeiter_id`),
    CONSTRAINT `Ereignisse_ibfk_2`
    FOREIGN KEY (`terminal_id`)
    REFERENCES `Arbeitszeiterfassung`.`Terminals` (`terminal_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `Arbeitszeiterfassung`.`Zeitberechnungen`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Arbeitszeiterfassung`.`Zeitberechnungen` ;

CREATE TABLE IF NOT EXISTS `Arbeitszeiterfassung`.`Zeitberechnungen` (
                                                                         `zeit_id` INT NOT NULL AUTO_INCREMENT,
                                                                         `mitarbeiter_id` INT NULL DEFAULT NULL,
                                                                         `arbeitsbeginn` DATETIME NULL DEFAULT NULL,
                                                                         `arbeitsende` DATETIME NULL DEFAULT NULL,
                                                                         `gesamtarbeitszeit` TIME NULL DEFAULT NULL,
                                                                         `gesamtpausenzeit` TIME NULL DEFAULT NULL,
                                                                         `datum` DATE NULL DEFAULT NULL,
                                                                         `pausenbeginn` DATETIME NULL DEFAULT NULL,
                                                                         `pausenende` DATETIME NULL DEFAULT NULL,
                                                                         PRIMARY KEY (`zeit_id`),
    INDEX `mitarbeiter_id` (`mitarbeiter_id` ASC) VISIBLE,
    CONSTRAINT `Zeitberechnungen_ibfk_1`
    FOREIGN KEY (`mitarbeiter_id`)
    REFERENCES `Arbeitszeiterfassung`.`Mitarbeiter` (`mitarbeiter_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
```

3. **Konfigurieren der Anwendung:**

```bash
Bearbeiten Sie die Datei application.properties mit Ihren Datenbankdetails:

spring.application.name=Projekt1
spring.datasource.url=jdbc:mysql://localhost:3306/Arbeitszeiterfassung
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.sql.init.mode=always
```
4. **Anwendung starten:**
```bash
   mvn spring-boot:run
```

# **API-Endpunkte**

**POST /api/events**

Verarbeitet ein Ereignis und speichert es in der Datenbank.

   * **Parameter**:
      * **mitarbeiterId** (Integer): Die ID des Mitarbeiters
      * **terminalId** (Integer): Die ID des Terminals
      * **eventType** (String): Der Typ des Ereignisses (**Eintritt**, **Austritt**, 
        **Pausenbeginn**, Pausenende)

   * **Beispiel**:
     ```bash
     curl -X POST "http://localhost:8080/api/events" -d "mitarbeiterId=1&terminalId=2&eventType=Eintritt"
     ```

## GET /api/arbeitszeiten/{mitarbeiterId}
Ruft die Arbeitszeitdetails eines Mitarbeiters ab.
   * **Parameter**:
        * **mitarbeiterId** (Integer): Die ID des Mitarbeiters
   * **Beispiel:**
     ```bash
     curl -X GET "http://localhost:8080/api/arbeitszeiten/1"
     ```

