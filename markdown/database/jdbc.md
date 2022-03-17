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

## Folie 2
- was ist jdbc
  - was ist SQL (ganz ganz ganz grob: Sprache der Datenbank)
- abbildung "java app -> jdbc api -> jdbc driver -> database
- converts data types
## Folie 3
- driver types
    - type 1: DBC-ODBC Bridge Driver
    - type 2: JDBC-Native API
    - type 3: JDBC-Net pure Java
    - type 4: Pure Java
## Folie 4
- connection mit database aufbauen
- database selecten
- connection schließen

## Folie 5
- Statement typen
  - basic: für (wenige) statische (hardoced) sql abfragen zur runtime. kann keine parameter
  - prepared statement: Für regelmäßige abfragen, kann parameter
  - callabale statement: Für database stored procedur (also abfragen die schon auf der DB "gespeichert" sind)
...

## Folie 6
- SQL abfragen senden
  - SELECT, INSERT, UPDATE (verweis das in DB dann mehr gelernt wird)
- results auswerten
  - resultset erklären (pointer in der db)
  - navigate, get und update

## Folie 7
- exceptions

## Folie 8
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
