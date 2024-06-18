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

-- Erstellen der Terminals Tabelle
CREATE TABLE Terminals (
    terminal_id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    type ENUM('Eingang', 'Ausgang', 'Pausenraum') NOT NULL,
    description TEXT
);

-- Erstellen der Mitarbeiter Tabelle
CREATE TABLE Mitarbeiter (
    mitarbeiter_id INT AUTO_INCREMENT PRIMARY KEY,
    vorname VARCHAR(50),
    nachname VARCHAR(50),
    abteilung VARCHAR(100),
    rolle ENUM('Mitarbeiter', 'Abteilungsleiter') NOT NULL
);

-- Erstellen der Ereignisse Tabelle
CREATE TABLE Ereignisse (
    ereignis_id INT AUTO_INCREMENT PRIMARY KEY,
    mitarbeiter_id INT,
    terminal_id INT,
    event_type ENUM('Eintritt', 'Austritt', 'Pausenbeginn', 'Pausenende'),
    timestamp DATETIME,
    FOREIGN KEY (mitarbeiter_id) REFERENCES Mitarbeiter(mitarbeiter_id),
    FOREIGN KEY (terminal_id) REFERENCES Terminals(terminal_id)
);

-- Erstellen der Zeitberechnungen Tabelle
CREATE TABLE Zeitberechnungen (
    zeit_id INT AUTO_INCREMENT PRIMARY KEY,
    mitarbeiter_id INT,
    arbeitsbeginn DATETIME,
    arbeitsende DATETIME,
    gesamtarbeitszeit TIME,
    gesamtpausenzeit TIME,
    datum DATE,
    pausenbeginn DATETIME,
    pausenende DATETIME,
    FOREIGN KEY (mitarbeiter_id) REFERENCES Mitarbeiter(mitarbeiter_id)
);
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

##     Terminals
Spalte	Typ	Beschreibung
terminal_id	INT AUTO_INCREMENT	Primärschlüssel
location	VARCHAR(255)	Standort des Terminals
type	ENUM	Typ des Terminals
description	TEXT	Beschreibung
