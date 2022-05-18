---
type: lecture-cg
title: "Swing 101: Basics"
menuTitle: "Swing: Basics"
author: "Carsten Gips (FH Bielefeld)"
weight: 1
readings:
  - key: "Java-SE-Tutorial"
    comment: "Creating Graphical User Interfaces > Creating a GUI With Swing"
  - key: "Ullenboom2021"
    comment: "Kap. 18: Einführung in grafische Oberflächen"
tldr: |
  TODO

*   Swing-Fenster haben Top-Level-Komponenten: `JFrame`, ...
*   Atomare Komponenten wie Buttons, Label, ... können gruppiert werden
*   Fenster müssen explizit sichtbar gemacht werden
*   Nach Schließen des Fensters läuft die Applikation weiter (Default)
*   Swing-Events werden durch den _Event Dispatch Thread_ (EDT) verarbeitet
    => Aufpassen mit Multithreading!

outcomes:
  - k2: "Unterschied und Zusammenhang zwischen Swing und AWT"
youtube:
  - link: ""
    name: "VL Swing 101"
  - link: ""
    name: "Demo Einfaches Fenster"
fhmedia:
  - link: ""
    name: "VL Swing 101"
---


## Wiederholung GUI in Java

*   **AWT**: `abstract window toolkit`
    *   Älteres Framework ("Legacy")
    *   "Schwergewichtig": plattformangepasst
    *   Paket `java.awt`

*   **Swing**
    *   Nutzt AWT
    *   "Leichtgewichtig": rein in Java implementiert
    *   Paket `javax.swing`

*   **JavaFX**
    *   Soll als Ersatz für Swing dienen
        *   Community eher verhalten
        *   Weiterentwicklung immer wieder unklar
        *   Nicht mehr im JDK/Java SE Plattform enthalten
    *   Vergleichsweise komplexes Framework, auch ohne Java programmierbar
        (Skriptsprache FXML)

::: notes
_Anmerkung_: In Swing reimplementierte Klassen aus AWT:
Präfix "J": `java.awt.Button` (AWT) => `javax.swing.JButton` (Swing)
:::


## Graphische Komponenten einer GUI

*   Top-Level Komponenten
    *   Darstellung direkt auf Benutzeroberfläche des Betriebssystems
    *   Beispiele: Fenster, Dialoge

*   Atomare Komponenten
    *   Enthalten i.d.R. keine weiteren Komponenten
    *   Beispiele: Label, Buttons, Bilder

*   Gruppierende Komponenten
    *   Bündeln und gruppieren andere Komponenten
    *   Beispiele: JPanel

\bigskip

[Achtung:]{.alert}
Unterteilung nicht im API ausgedrückt: Alle Swing-Bausteine leiten von
Klasse `javax.swing.JComponent` ab!

=> Nutzung "falscher" Methoden führt zu Laufzeitfehlern.


## Ein einfaches Fenster

```java
public class FirstWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World :)");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
```

::: notes
### Elemente

Es wird ein neuer Frame angelegt als Top-Level-Komponente. Der Fenstertitel wird auf "Hello World :)"
gesetzt.

Zusätzlich wird spezifiziert, dass sich das Programm durch Schließen des Fensters beenden soll.
Anderenfalls würde man zwar das sichtbare Fenster schließen, aber das Programm würde weiter laufen.

Mit der Swing-Methode `pack()` werden alle Komponenten berechnet und die Fenstergröße bestimmt, so dass
alle Komponenten Platz haben. Bis dahin ist das Fenster aber unsichtbar und wird erst über den Aufruf
von `setVisible(true)` auch dargestellt.

### Swing und Multithreading: Event Dispatch Thread

Leider ist die Welt nicht ganz so einfach. In Swing werden Events wie das Drücken eines Buttons
durch den _Event Dispatch Thread_ (EDT) abgearbeitet. (Zum Thema Events in Swing siehe Einheit
`["Swing Events"]({{< ref "/gui/swing2_events" >}})`{=markdown}.) Der EDT wird mit dem Erzeugen
der visuellen Komponenten für die Swing-Objekte durch den Aufruf der Swing-Methoden `show()`,
`setVisible()` und `pack()` erstellt. Bereits beim Realisieren der Komponenten könnten diese
Events auslösen, die dann durch den EDT verarbeitet werden und an mögliche Listener verteilt
werden. Dummerweise wird das `main()` von der JVM aber in einem eigenen Thread abgearbeitet - es
könnten also zwei Threads parallel durch die hier erzeugte Swing-GUI laufen, und Swing ist
**nicht Thread-safe**! Komponenten dürfen nicht durch verschiedene Threads manipuliert werden.

Die Lösung ist, die Realisierung der Komponenten als Job für den EDT zu "verpacken":

```java
SwingUtilities.invokeLater(
        new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Hello World :)");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();
                frame.setVisible(true);
            }
        });
```

Mit `new Runnable()` wird ein neues Objekt vom Typ `Runnable` anlegt - im Prinzip ein neuer, noch nicht
gestarteter Thread mit der Hauptmethode `run()`. Dieses Runnable wird mit `SwingUtilities.invokeLater()`
dem EDT zu Ausführung übergeben. Wir werden uns das Thema Erzeugen und Starten von Threads in der Einheit
`["Einführung in die nebenläufige Programmierung mit Threads"]({{< ref "/threads/intro" >}})`{=markdown}
genauer ansehen.

Siehe auch ["Concurrency in Swing"](https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html).

[Beispiel: [basics.FirstWindow](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/basics/FirstWindow.java)]{.bsp}
:::

[Demo: [basics.SecondWindow](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/basics/SecondWindow.java)]{.bsp}


## Wrap-Up

*   Swing-Fenster haben Top-Level-Komponenten: `JFrame`, ...
*   Atomare Komponenten wie Buttons, Label, ... können gruppiert werden
*   Fenster müssen explizit sichtbar gemacht werden
*   Nach Schließen des Fensters läuft die Applikation weiter (Default)
*   Swing-Events werden durch den _Event Dispatch Thread_ (EDT) verarbeitet
    => Aufpassen mit Multithreading!







<!-- DO NOT REMOVE - THIS IS A LAST SLIDE TO INDICATE THE LICENSE AND POSSIBLE EXCEPTIONS (IMAGES, ...). -->
::: slides
## LICENSE
![](https://licensebuttons.net/l/by-sa/4.0/88x31.png)

Unless otherwise noted, this work is licensed under CC BY-SA 4.0.
:::