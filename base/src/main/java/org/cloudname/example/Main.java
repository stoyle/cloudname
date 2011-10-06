package org.cloudname.example;

import org.cloudname.con.HttpConsole;
import org.cloudname.mon.Counter;

/**
 * This class contains various examples of how Base utilities are
 * used.
 *
 * @author borud
 */
public class Main {
    private static final int HTTPCONSOLE_PORT = 4601;
    private final static Counter fooBarCounter = Counter.getCounter("example.foobar.count");
    private static volatile boolean keepRunning = true;

    public static void main(String[] args) throws Exception {
        HttpConsole console = HttpConsole.create(HTTPCONSOLE_PORT).start();

        // Spin off a Thread which just ups the counter
        new Thread(new Runnable() {
                public void run() {
                    try {
                        while(keepRunning) {
                            Thread.sleep(200);
                            fooBarCounter.inc();
                        }
                    } catch (Exception e) {
                        return;
                    }
                }}).start();

        // Print message on console
        System.out.println("\nPlease go to: http://localhost:" + console.getPort() + "/");
        System.out.println("\nPress ENTER to quit");

        // Block while waiting for keypress
        System.in.read();
        keepRunning = false;

        // Shut down console
        console.shutdown();
    }
}