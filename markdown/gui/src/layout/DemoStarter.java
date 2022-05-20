package layout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Rahmen für die Layout-Demos */
public final class DemoStarter {
    /** Erzeuge die Komponenten in neuem EDT-Job statt im main()-Tread */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        JFrame frame = new JFrame("Widget Demo");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        frame.add(Flow.newFlow());
                        // frame.add(Border.newBorder());
                        // frame.add(Grid.newGrid());
                        // frame.add(GridBag.newGridBag());

                        frame.pack();
                        frame.setVisible(true);
                    }
                });
    }
}