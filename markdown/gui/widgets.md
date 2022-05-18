---
type: lecture-cg
title: "Swing: Nützliche Widgets"
menuTitle: "Swing: Widgets"
author: "Carsten Gips (FH Bielefeld)"
weight: 3
readings:
  - key: "Java-SE-Tutorial"
    comment: "Creating Graphical User Interfaces > Creating a GUI With Swing"
  - key: "Ullenboom2021"
    comment: "Kap. 18: Einführung in grafische Oberflächen"
tldr: |
  TODO
    Nützliche Swing-Komponenten:

    *   Scroll-Bars
    *   Panel mit Tabs
    *   Kontextmenü
    *   Radiobuttons
outcomes:
  - k3: "Umgang mit komplexeren Swing-Komponenten: JRadioButton, JFileChooser, JTabbedPane, JScrollPane, JPopupMenu"
  - k3: "Nutzung von ActionListener, MouseListener, KeyListener, FocusListener"
quizzes:
  - link: "XYZ"
    name: "Quiz XXX (ILIAS)"
youtube:
  - link: ""
    name: "VL Swing Widgets"
  - link: ""
    name: "Demo JRadioButton"
  - link: ""
    name: "Demo JFileChooser"
  - link: ""
    name: "Demo JTabbedPane und JScrollPane"
  - link: ""
    name: "Demo JPopupMenu"
fhmedia:
  - link: ""
    name: "VL Swing Widgets"
---


## Radiobuttons: _JRadioButton_

\bigskip

![](images/screenshot-radiobuttons.png){width="50%"}

\bigskip

::: notes
*   Erzeugen einen neuen "Knopf" (rund)
    -   vergleiche `JCheckBox` => eckiger "Knopf"
*   Parameter: Beschriftung und Aktivierung
*   Reagieren mit `ItemListener`
:::

\bigskip

*   **Logische Gruppierung der Buttons**: `ButtonGroup`
    *   `JRadioButton` sind **unabhängige** Objekte
    *   Normalerweise nur ein Button aktiviert
    *   Aktivierung eines Buttons => vormals aktivierter Button deaktiviert

    \smallskip

    ```{.java size=footnotesize}
    JRadioButton b1 = new JRadioButton("Button 1", true);
    JRadioButton b2 = new JRadioButton("Button 2", false);

    ButtonGroup radioGroup = new ButtonGroup();
    radioGroup.add(b1);    radioGroup.add(b2);
    ```

[Demo: [widgets.RadioButtonDemo](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/widgets/RadioButtonDemo.java)]{.bsp}


## Dateien oder Verzeichnisse auswählen: _JFileChooser_

![](images/screenshot-filechooser.png){width="40%"}

\bigskip

```{.java size=footnotesize}
JFileChooser fc = new JFileChooser("Startverzeichnis");
fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
if (fc.showOpenDialog() == JFileChooser.APPROVE_OPTION)
    fc.getSelectedFile()
```

::: notes
*   `fc.setFileSelectionMode()`: Dateien, Ordner oder beides auswählbar
*   Anzeigen mit `fc.showOpenDialog()`
*   Rückgabewert vergleichen mit `JFileChooser.APPROVE_OPTION`:
    Datei/Ordner wurde ausgewählt => Prüfen!
*   Selektierte Datei als `File` bekommen: `fc.getSelectedFile()`

**Filtern der Anzeige**: `FileFilter`

*   Setzen mit `JFileChooser.setFileFilter()`
*   Überschreiben von
    *   `boolean accept(File f)`
    *   `String getDescription()`
:::

[Demo: [widgets.FileChooserDemo](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/widgets/FileChooserDemo.java)]{.bsp}


## TabbedPane und Scroll-Bars

\bigskip

*   **TabbedPane**: `JTabbedPane`
    *   Container für weitere Komponenten
    *   Methode zum Hinzufügen anderer Swing-Komponenten:

        \smallskip

        ```{.java size=footnotesize}
        public void addTab(String title, Icon icon, Component component, String tip)
        ```

\bigskip

*   **Scroll-Bars**: `JScrollPane`
    *   Container für weitere Komponenten
    *   Scroll-Bars werden bei Bedarf sichtbar
    *   Hinzufügen einer Komponente:

        \smallskip

        ```{.java size=footnotesize}
        JPanel panel = new JPanel();
        JTextArea text = new JTextArea(5, 10);

        JScrollPane scrollText = new JScrollPane(text);
        panel.add(scrollText);
        ```

<!-- XXX
*   Zusammenbauen der Komponenten und Container am Beispiel zeigen/erklären
*   Wirkung der Optionen (als Tooltips) zeigen
*   Wirkung der Scrollpane zeigen (letzter Tab)
-->

[Demo: [widgets.TabbedPaneDemo](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/widgets/TabbedPaneDemo.java)]{.bsp}


## Kontextmenü mit _JPopupMenu_

*   Menü kann über anderen Komponenten angezeigt werden

*   Einträge vom Typ `JMenuItem` hinzufügen (beispielsweise `JRadioButtonMenuItem`)

    ```{.java size=footnotesize}
    public JMenuItem add(JMenuItem menuItem)
    ```

*   Menü über der aufrufenden Komponente "`invoker`" anzeigen

    ```{.java size=footnotesize}
    public void show(Component invoker, int x, int y)
    ```

::: notes
### Details zu _JMenuItem_

*   Erweitert `AbstractButton`
*   Reagiert auf `ActionEvent`
    => `ActionListener` implementieren für Reaktion auf Menüauswahl

### Details zum Kontextmenü

**Triggern der Anzeige eines `JPopupMenu`**

*   Beispielsweise über `MouseListener` einer (anderen!) Komponente
*   Darin Reaktion auf `MouseEvent.isPopupTrigger()`
    => `JPopupMenu.show()` aufrufen

```java
JFrame myFrame = new JFrame();
JPopupMenu kontextMenu = new JPopupMenu();

myFrame.addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            kontextMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
});
```
:::

[Demo: [widgets.PopupDemo](https://github.com/PM-Dungeon/PM-Lecture/blob/master/markdown/gui/src/widgets/PopupDemo.java)]{.bsp}


## Wrap-Up

Nützliche Swing-Komponenten:

*   Scroll-Bars
*   Panel mit Tabs
*   Kontextmenü
*   Radiobuttons







<!-- DO NOT REMOVE - THIS IS A LAST SLIDE TO INDICATE THE LICENSE AND POSSIBLE EXCEPTIONS (IMAGES, ...). -->
::: slides
## LICENSE
![](https://licensebuttons.net/l/by-sa/4.0/88x31.png)

Unless otherwise noted, this work is licensed under CC BY-SA 4.0.
:::