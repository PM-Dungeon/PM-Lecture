---
type: lecture-cg
title: "Java und Datenbanken: JDBC"
menuTitle: "JDBC"
author: "Carsten Gips (FH Bielefeld)"
weight: 1
readings:
  - key: "Ullenboom2016"
tldr: |
  hier kommt eine tolle inline-zusammenfassung!
  Formatierung _könnte_ auch **gehen**?
outcomes:
  - k1: "**wuppie**"
  - k2: "*foo*"
  - k3: "fluppie"
quizzes:
  - link: "XYZ"
    name: "Quiz XXX (ILIAS)"
assignments:
  - topic: sheet01
youtube:
  - link: ""
    name: "VL "
  - link: ""
    name: "Demo "
  - link: ""
    name: "Demo "
fhmedia:
  - link: ""
    name: "VL "
sketch: true

hidden: true
_build:
  render: never
  list: never
  publishResources: false
---


## Motivation
- Mit Datenbanken interagieren, daten senden und abfragen

## JDBC
- was ist jdbc
  - was ist SQL (ganz ganz ganz grob: Sprache der Datenbank)
- abbildung "java app -> jdbc api -> jdbc driver -> database
- converts data types

## Treiber Typen
- driver types
    - type 1: DBC-ODBC Bridge Driver
    - type 2: JDBC-Native API
    - type 3: JDBC-Net pure Java
    - type 4: Pure Java

## Treiber Registrieren

Für unterschiedliche Datenbanek gibt es unterschiedliche Treiber. Diese müssen in der Java-Anwendung registriert werden, um mithilfe von jdbc eine Verbindung zur Datenbank aufzubauen.

Möglichkeit 1: `Class.forName()`
```java
  Class.forName("{datenbanktreiber}")
```

Beispiel:

Oracle:
```java
  Class.forName("oracle.jdbc.driver.OracleDriver");
```
MySQL:
```java
  Class.forName("com.mysql.jdbc.Driver");
```
Microsoft:
```java
  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")`
```

Möglichkeit 2: `DriverManager.registerDriver()`
```java
  Driver myDriver = new {driver}();
  DriverManager.registerDriver( myDriver );
```

Beispiel:

Oracle:
```java
  Driver myDriver = new oracle.jdbc.driver.OracleDriver();
  DriverManager.registerDriver(myDriver);
```
MySQL:
```java
  Driver myDriver = com.mysql.jdbc.Driver();
  DriverManager.registerDriver(myDriver);
```
Microsoft:
```java
  Driver myDriver = com.microsoft.sqlserver.jdbc.SQLServerDriver();
  DriverManager.registerDriver(myDriver);
```


## Verbindung aufbauen

Mit drei Parametern.
```java
  String URL="jdbc:database"; //todo checken of das so richtig ist
  String USER= "Admin";
  String PASSWORD = "admin123"
  Connection connection = DriverManager.getConnection(URL,USER,PASSWORD)
```

Mit einem Paramter. Username und Passwort werden in der URL angegeben.
```java
  String URL="jdbc:admin/admin123@database";  //todo checken of das so richtig ist
  Connection connection = DriverManager.getConnection(URL)
```

Mit Properties um Username und Passwort anzugeben.
 ```java
  String URL="jdbc:database";  //todo checken of das so richtig ist
  Properties login = new Properties();
  login.put("user","Admin");
  login.put("password","admin123");
  Connection connection = DriverManager.getConnection(URL,login)
```

Am Ende muss die Verbindung zur Datenbank geschlossen werden.
```java
  connection.close();
```

## Statements
- Statement typen
  - basic: für (wenige) statische (hardoced) sql abfragen zur runtime. kann keine parameter
  - prepared statement: Für regelmäßige abfragen, kann parameter
  - callabale statement: Für database stored procedur (also abfragen die schon auf der DB "gespeichert" sind)
...

## SQL
- SQL abfragen senden
  - SELECT, INSERT, UPDATE (verweis das in DB dann mehr gelernt wird)
- results auswerten
  - resultset erklären (pointer in der db)
  - navigate, get und update

## SQL-Exceptions
- exceptions

## Ausblick
- ausblick was noch geht
    - transactions/roll backs
    - data streaming
    - batch processing

## Wrap-Up
- jdbc um mit dantebanken zu interagieren
- gibt unterschiedliche treiber
- how to connection aufbauen
- how to statement senden
- how to result auswerten
...







<!-- DO NOT REMOVE - THIS IS A LAST SLIDE TO INDICATE THE LICENSE AND POSSIBLE EXCEPTIONS (IMAGES, ...). -->
::: slides
## LICENSE
![](https://licensebuttons.net/l/by-sa/4.0/88x31.png)

Unless otherwise noted, this work is licensed under CC BY-SA 4.0.

\bigskip

### Exceptions
*   TODO (what, where, license)
:::
