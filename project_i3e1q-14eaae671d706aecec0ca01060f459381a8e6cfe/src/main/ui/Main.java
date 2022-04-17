package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;

// calls SatoshiTracker, main ui class for the app
public class Main {
    public static void main(String[] args) {
        //new SatoshiTracker();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString() + "\n");
                }
            }
        }, "Shutdown-thread"));
    }

    private static void createAndShowGUI() {
        new GUI();

    }
}
