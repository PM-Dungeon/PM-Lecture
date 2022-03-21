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

- **J**ava **D**ata**b**ase **C**onnectivity (JDBC) ist eine Java-API um auf Datenbanke zuzugreifen
- Damit können Verbindungen zu Datenbank hergestellt und SQL-Statements ausgeführt werden.
- JDBC konvertiert die SQL-Datentypen in Java-Datentypen und umgedreht.
- Die JDBC API ist universal und Datenbanksystem unabhängig
- Die einzelen Datenbanksystem-Hersteller stellen JDBC-Treiber zur verfügung.
- Was machen die Treiber? Impelementieren Schnittstelle damit JDBC sie nutzen kann. **todo formulieren**
- Der JDBC Driver Manager läd den Datenbansystem spezifischen Treiber in die Anwendung.


**todo image einfügen**

## Treiber Registrieren

Für unterschiedliche Datenbanksysteme gibt es unterschiedliche Treiber. Diese müssen in der Java-Anwendung registriert werden, um mithilfe von jdbc eine Verbindung zur Datenbank aufzubauen.

Möglichkeit 1: Dynamsch zur Laufzeit `Class.forName()`
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


Möglichkeit 2: Statisch `DriverManager.registerDriver()`
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

## Verbindung aufbauen

**todo: URL Aufbau, ist das unser Thema?**

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

- Mit `Statement`s werden SQL-Befehle erstellt, die dann an die Datenbank gesendet werden können.

Statement erstellen mithilfe des `Connection`-Objekts
```java
Statement st= connection.createStatement();
```

Datensätze aus der Datenbank abfragen:

```java
String sql= "SELECT * FROM USER";
ResultSet rs = st.executeQuery(sql);

while(rs.next){
    System.out.println("ID:" + rs.getInt("id"));
    System.out.println("Username:" + rs.getString("name"));
    System.out.println("Age:" + rs.getInt("age"));
}
rs.close();
```

Datensätze in der Datenbank hinzufügen:

```java
String sql="INSERT INTO User VALUES ('Wuppi Fluppi',22)";
st.executeUpdate(sql);
sql="INSERT INTO User VALUES ('Tutti Frutti ',100)";
st.executeUpdate(sql);
```

Datensätze in der Datenbank ändern:

```java
String sql="UPDATE User SET age = 20 WHERE id in (100,110)";
ResultSet rs= st.executeUpdate(sql);
while(rs.next){
    System.out.println("ID:" + rs.getInt("id"));
    System.out.println("Username:" + rs.getString("name"));
    System.out.println("Age:" + rs.getInt("age"));
}
rs.close();
```


`PreparedStatments` für wiederholte oder gesammelte abfragen:

**todo besseres beispiel**
```java
public void giveMoney(HashMap <String, Integer> hashmap){
    String sql= "UPDATE User SET money=? WHERE name = ?"
        PreparedStatement pst= connection.prepareStatement(sql);
    connection.setAutoCommit(false);
    for (Map.Entry<String, Integer> e : hashmap.entrySet()) {
        pst.setInt(1,e.getValue().intValue());
        pst.setString(2, e.getKey());
        pst.executeUpdate();

        todo: zweite abfragen

        connection.commit();

    }

}

```

**todo: Vielleicht zu tief?**- callabale statement: Für database stored procedur (also abfragen die schon auf der DB "gespeichert" sind)
...

## `ResultSet`
- results auswerten
  - resultset erklären (pointer in der db)
  - navigate, get und update

## SQL-Exceptions
Auch mit JDBC kann es zu Fehlern/Probleme kommen.
    - Fehlerhafte Statements
    - Verbindungsprobleme
    - Fehler in den Treibern oder der Datenbank selber
Daher ist Exceptionhandling besonders wichtig.
Jedes
```java
try {
    // do something
}
catch (Excpetion e){
    //ups
    e.printStackTrace();
}
finally{
    connection.close();
}

```

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
