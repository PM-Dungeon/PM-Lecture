---
type: lecture-cg
title: "Lambda-Ausdrücke und funktionale Interfaces"
menuTitle: "Lambda-Ausdrücke"
author: "Carsten Gips (FH Bielefeld)"
weight: 2
readings:
  - key: "Java-SE-tutorial"
    comment: "Learning the Java Language: Classes and Objects: Abschnitt Nested Classes, Abschnitt Anonymous Classes und Abschnitt Lambda Expressions"
  - key: "Urma2014"
    comment: "Kapitel 3: Lambda Expressions, Kapitel 5: Working with streams"
  - key: "Ullenboom2021"
    comment: "Kapitel 12: Lambda-Ausdrücke und funktionale Programmierung"
tldr: |
  Mit einer anonymen inneren Klasse erstellt man gewissermaßen ein Objekt einer "Wegwerf"-Klasse:
  Man leitet _on-the-fly_ von einem Interface ab oder erweitert eine Klasse und implementiert die
  benötigten Methoden und erzeugt von dieser Klasse sofort eine Instanz (Objekt). Diese neue Klasse
  ist im restlichen Code nicht sichtbar.

  Anonyme innere Klassen sind beispielsweise in Swing recht nützlich, wenn man einer Komponente einen
  Listener mitgeben will: Hier erzeugt man eine anonyme innere Klasse basierend auf dem passenden
  Listener-Interface, implementiert die entsprechenden Methoden und übergibt das mit dieser Klasse
  erzeugte Objekt als neuen Listener der Swing-Komponente.

  Mit Java 8 können unter gewissen Bedingungen diese anonymen inneren Klassen zu Lambda-Ausdrücken
  (und Methoden-Referenzen) vereinfacht werden. Dazu muss die anonyme innere Klasse ein sogenanntes
  **funktionales Interface** implementieren.

  Funktionale Interfaces sind Interfaces mit _genau einer abstrakten Methode_. Es können beliebig
  viele Default-Methoden im Interface enthalten sein, und es können `public` sichtbare abstrakte
  Methoden von `java.lang.Object` geerbt/überschrieben werden.

  Die Lambda-Ausdrücke entsprechen einer anonymen Methode: Die Parameter werden aufgelistet (in
  Klammern), und hinter einem Pfeil kommt entweder _ein_ Ausdruck (Wert - gleichzeitig Rückgabewert
  des Lambda-Ausdrucks) oder beliebig viele Anweisungen (in geschweiften Klammern, mit Semikolon):
  *   Form 1: `(parameters)  ->  expression`
  *   Form 2: `(parameters)  ->  { statements; }`

  Der Lambda-Ausdruck muss von der Signatur her genau der einen abstrakten Methode im unterliegenden
  funktionalen Interface entsprechen.
outcomes:
  - k2: "Funktionales Interfaces (Definition)"
  - k3: "Einsatz innerer und anonymer Klassen"
  - k3: "Erstellen eigener funktionaler Interfaces"
  - k3: "Einsatz von Lambda-Ausdrücken"
quizzes:
  - link: "https://www.fh-bielefeld.de/elearning/goto.php?target=tst_1087248&client_id=FH-Bielefeld"
    name: "Quiz Lambda-Ausdrücke und funktionale Interfaces (ILIAS)"
assignments:
  - topic: sheet08
youtube:
  - link: "https://youtu.be/Wd8KG7xtp4c"
    name: "VL Lambda-Ausdrücke und funktionale Interfaces"
  - link: "https://youtu.be/QEXpQwRYoYc"
    name: "Demo Anonyme innere Klasse"
  - link: "https://youtu.be/2LJIxsVw4pM"
    name: "Demo Lambda-Ausdruck"
  - link: "https://youtu.be/93O1oDL5_5c"
    name: "Demo Funktionales Interface"
fhmedia:
  - link: "https://www.fh-bielefeld.de/medienportal/m/d2398cc8e1279e6b6bf1df06bd30b731e714d04d10e15b87a5f89aa07cbaf97978bb12f82ea0c7eff8a3133eb65134521933218fb94856fb6e8a6dc187dded28"
    name: "VL Lambda-Ausdrücke und funktionale Interfaces"
---


## Problem: Sortieren einer Studi-Liste

```java
List<Studi> sl = new ArrayList<>();

// Liste sortieren?
sl.sort(???);  // Parameter: java.util.Comparator<Studi>
```

\pause
\bigskip

```java
public class MyCompare implements Comparator<Studi> {
    @Override  public int compare(Studi o1, Studi o2) {
        return o1.getCredits() - o2.getCredits();
    }
}
````

```java
// Liste sortieren?
MyCompare mc = new MyCompare();
sl.sort(mc);
```

::: notes
Da `Comparator<T>` ein Interface ist, muss man eine extra Klasse anlegen, die die
abstrakte Methode aus dem Interface implementiert und ein Objekt von dieser Klasse
erzeugen und dieses dann der `sort()`-Methode übergeben.

Die Klasse bekommt wie in Java üblich eine eigene Datei und ist damit in der
Package-Struktur offen sichtbar und "verstopft" mir damit die Strukturen: Diese
Klasse ist doch nur eine Hilfsklasse ... Noch schlimmer: Ich brauche einen Namen
für diese Klasse!

Den ersten Punkt könnte man über verschachtelte Klassen lösen: Die Hilfsklasse
wird innerhalb der Klasse definiert, die das Objekt benötigt. Für den zweiten
Punkt brauchen wir mehr Anlauf ...
:::


::::::::: notes
## Erinnerung: Verschachtelte Klassen ("_Nested Classes_")

Man kann Klassen innerhalb von Klassen definieren: Verschachtelte Klassen.

*   Implizite Referenz auf Instanz der äußeren Klasse, Zugriff auf **alle** Elemente
*   **Begriffe**:
    *   "normale" innere Klassen: "_inner classes_"
    *   statische innere Klassen: "_static nested classes_"
*   Einsatzzweck:
    *   Hilfsklassen: Zusätzliche Funktionalität kapseln; Nutzung **nur** in äußerer Klasse
    *   Kapselung von Rückgabewerten

Sichtbarkeit: Wird u.U. von äußerer Klasse "überstimmt"

### Innere Klassen ("_Inner Classes_")

*   Objekt der äußeren Klasse muss existieren
*   Innere Klasse ist normales Member der äußeren Klasse
*   Implizite Referenz auf Instanz äußerer Klasse
*   Zugriff auf **alle** Elemente der äußeren Klasse
*   Sonderfall: Definition innerhalb von Methoden ("local classes")
    *   Nur innerhalb der Methode sichtbar
    *   Kennt zusätzlich `final` Attribute der Methode

Beispiel:

```java
public class Outer {
    ...
    private class Inner {
        ...
    }

    Outer.Inner inner = new Outer().new Inner();
}
```

[Beispiel mit Iterator als innere Klasse: [nested.StudiListNested](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/modern-java/src/nested/StudiListNested.java)]{.bsp}

### Statische innere Klassen ("_Static Nested Classes_")

*   Keine implizite Referenz auf Objekt
*   Nur Zugriff auf Klassenmethoden und -attribute

Beispiel:

```java
class Outer {
    ...
    static class StaticNested {
        ...
    }
}

Outer.StaticNested nested = new Outer.StaticNested();
```
:::::::::


## Lösung: Comparator als anonyme innere Klasse

```java
List<Studi> sl = new ArrayList<>();

// Parametrisierung mit anonymer Klasse
sl.sort(
        new Comparator<Studi>() {
            @Override
            public int compare(Studi o1, Studi o2) {
                return o1.getCredits() - o2.getCredits();
            }
        });  // Semikolon nicht vergessen!!!
```

::: notes
=> Instanz einer anonymen inneren Klasse, die das Interface `Comparator<Studi>` implementiert

*   Für spezielle, einmalige Aufgabe: nur eine Instanz möglich
*   Kein Name, kein Konstruktor, oft nur eine Methode
*   Müssen Interface implementieren oder andere Klasse erweitern
    *   Achtung Schreibweise: ohne `implements` oder `extends`!
*   Konstruktor kann auch Parameter aufweisen
*   Zugriff auf alle Attribute der äußeren Klasse plus alle `final` lokalen
    Variablen
*   Nutzung typischerweise bei GUIs: Event-Handler etc.
:::

[Demo: [nested.DemoAnonymousInnerClass](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/modern-java/src/nested/DemoAnonymousInnerClass.java)]{.bsp}


## Vereinfachung mit Lambda-Ausdruck

```java
List<Studi> sl = new ArrayList<>();

// Parametrisierung mit anonymer Klasse
sl.sort(
        new Comparator<Studi>() {
            @Override
            public int compare(Studi o1, Studi o2) {
                return o1.getCredits() - o2.getCredits();
            }
        });  // Semikolon nicht vergessen!!!


// Parametrisierung mit Lambda-Ausdruck
sl.sort( (Studi o1, Studi o2) -> o1.getCredits() - o2.getCredits() );
```

[[Hinweis auf **funktionales Interface**]{.bsp}]{.slides}

::: notes
**Anmerkung**: Damit für den Parameter alternativ auch ein Lambda-Ausdruck verwendet
werden kann, muss der erwartete Parameter vom Typ her ein "**funktionales Interface**"
(s.u.) sein!
:::

[Demo: [nested.DemoLambda](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/modern-java/src/nested/DemoLambda.java)]{.bsp}


## Syntax für Lambdas

```java
(Studi o1, Studi o2)  ->  o1.getCredits() - o2.getCredits()
```

::: notes
Ein Lambda-Ausdruck ist eine Funktion ohne Namen und besteht aus drei Teilen:

1.  Parameterliste (in runden Klammern),
2.  Pfeil
3.  Funktionskörper (rechte Seite)

Falls es _genau einen_ Parameter gibt, _können_ die runden Klammern um den Parameter
entfallen.

Dabei kann der Funktionskörper aus _einem Ausdruck_ ("_expression_") bestehen oder
einer _Menge von Anweisungen_ ("_statements"_), die dann in geschweifte Klammern
eingeschlossen werden müssen (Block mit Anweisungen).

Der Wert des Ausdrucks ist zugleich der Rückgabewert des Lambda-Ausdrucks.
:::

\bigskip
\bigskip

Varianten:

*   **`(parameters)  ->  expression`**

\smallskip

*   **`(parameters)  ->  { statements; }`**


## Quiz: Welches sind keine gültigen Lambda-Ausdrücke?

1.  `() -> {}`
2.  `() -> "wuppie"`
3.  `() -> { return "fluppie"; }`
4.  `(Integer i) -> return i + 42;`
5.  `(String s) -> { "foo"; }`
6.  `(String s) -> s.length()`
7.  `(Studi s) -> s.getCredits() > 300`
8.  `(List<Studi> sl) -> sl.isEmpty()`
9.  `(int x, int y) -> { System.out.println("Erg: "); System.out.println(x+y); }`
10. `() -> new Studi()`
11. `s -> s.getCps() > 100 && s.getCps() < 300`
12. `s -> { return s.getCps() > 100 && s.getCps() < 300; }`

:::::: notes
::: showme
Auflösung:
(4) und (5): `return` ist eine Anweisung, d.h. bei (4) fehlen die geschweiften
Klammern. `"foo"` ist ein String und als solcher ein Ausdruck, d.h. hier sind
die geschweiften Klammern zu viel (oder man ergänze den String mit einem `return`,
also `return "foo";` ...).
:::
::::::


## Definition "Funktionales Interface" ("_functional interfaces_")

```java
@FunctionalInterface
public interface Wuppie<T> {
    int wuppie(T obj);
    boolean equals(Object obj);
    default int fluppie() { return 42; }
}
```

\bigskip

`Wuppie<T>` ist ein [**funktionales Interface**]{.alert}
("_functional interface_") [(seit Java 8)]{.notes}

*   Hat **genau _eine_ abstrakte Methode**
*   Hat evtl. weitere Default-Methoden
*   Hat evtl. weitere abstrakte Methoden, die `public` Methoden von
    `java.lang.Object` überschreiben

::: notes
Die Annotation `@FunctionalInterface` selbst ist nur für den Compiler: Falls
das Interface _kein_ funktionales Interface ist, würde er beim Vorhandensein
dieser Annotation einen Fehler werfen. Oder anders herum: Allein durch das
Annotieren mit `@FunctionalInterface` wird aus einem Interface noch kein
funktionales Interface! Vergleichbar mit `@Override` ...

[**Während man für eine anonyme Klasse lediglich ein "normales" Interface
 (oder eine Klasse) benötigt, braucht man für Lambda-Ausdrücke zwingend ein
 passendes funktionales Interface!**]{.alert}

_Anmerkung_: Es scheint keine einheitliche deutsche Übersetzung für den Begriff
_functional interface_ zu geben. Es wird häufig mit "funktionales Interface",
manchmal aber auch mit "Funktionsinterface" übersetzt.

Das in den obigen Beispielen eingesetzte Interface `java.util.Comparator<T>`
ist also ein funktionales Interface: Es hat nur _eine_ eigene abstrakte Methode
`int compare(T o1, T o2);`.

Im Package [java.util.function](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/package-summary.html)
sind einige wichtige funktionale Interfaces bereits vordefiniert, beispielsweise
`Predicate` (Test, ob eine Bedingung erfüllt ist) und `Function` (verarbeite
einen Wert und liefere einen passenden Ergebniswert). Diese kann man auch in
eigenen Projekten nutzen!
:::


## Quiz: Welches ist kein funktionales Interface?

```java
public interface Wuppie {
    int wuppie(int a);
}

public interface Fluppie extends Wuppie {
    int wuppie(double a);
}

public interface Foo {
}

public interface Bar extends Wuppie {
    default int bar() { return 42; }
}
```

:::::: notes
::: showme
Auflösung:

*   `Wuppie` hat _genau eine_ abstrakte Methode => funktionales Interface
*   `Fluppie` hat zwei abstrakte Methoden => **kein** funktionales Interface
*   `Foo` hat gar keine abstrakte Methode => **kein** funktionales Interface
*   `Bar` hat _genau eine_ abstrakte Methode (und eine Default-Methode) => funktionales Interface
:::
::::::


## Lambdas und funktionale Interfaces: Typprüfung

```java
interface java.util.Comparator<T> {
    int compare(T o1, T o2);    // abstrakte Methode
}
```

\bigskip

```java
// Verwendung ohne weitere Typinferenz
Comparator<Studi> c1 = (Studi o1, Studi o2) -> o1.getCredits() - o2.getCredits();

// Verwendung mit Typinferenz
Comparator<Studi> c2 = (o1, o2) -> o1.getCredits() - o2.getCredits();
```

::: notes
Der Compiler prüft in etwa folgende Schritte, wenn er über einen Lambda-Ausdruck stolpert:

1.  In welchem Kontext habe ich den Lambda-Ausdruck gesehen?
2.  OK, der Zieltyp ist hier `Comparator<Studi>`.
3.  Wie lautet die **eine** abstrakte Methode im `Comparator<T>`-Interface?
4.  OK, das ist `int compare(T o1, T o2);`
5.  Da `T` hier an `Studi` gebunden ist, muss der Lambda-Ausdruck
    der Methode `int compare(Studi o1, Studi o2);` entsprechen:
    2x `Studi` als Parameter und als Ergebnis ein `int`
6.  Ergebnis:
    a)  Cool, passt zum Lambda-Ausdruck `c1`. Fertig.
    b)  D.h. in `c2` müssen `o1` und `o2` vom Typ `Studi` sein.
        Cool, passt zum Lambda-Ausdruck `c2`. Fertig.
:::


## Wrap-Up

*   Anonyme Klassen: "Wegwerf"-Innere Klassen
    *   Müssen Interface implementieren oder Klasse erweitern

\smallskip

*   Java8: **Lambda-Ausdrücke** statt anonymer Klassen (**funktionales Interface nötig**)
    *   Zwei mögliche Formen:
        *   Form 1: `(parameters)  ->  expression`
        *   Form 2: `(parameters)  ->  { statements; }`
    *   Im jeweiligen Kontext muss ein **funktionales Interface** verwendet werden,
        d.h. ein Interface mit **genau** einer abstrakten Methode
    *   Der Lambda-Ausdruck muss von der Signatur her dieser einen abstrakten Methode
        entsprechen







<!-- DO NOT REMOVE - THIS IS A LAST SLIDE TO INDICATE THE LICENSE AND POSSIBLE EXCEPTIONS (IMAGES, ...). -->
::: slides
## LICENSE
![](https://licensebuttons.net/l/by-sa/4.0/88x31.png)

Unless otherwise noted, this work is licensed under CC BY-SA 4.0.
:::
