package com.module.alevel.taskwithexceptions;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Scanner;

public class InterruptedInput implements Runnable {

    public static String getCurrent() {
        return current;
    }

    public static String current = "";

    public void run() {
        System.out.println(Thread.interrupted());
        while (!Thread.interrupted()) {
            try {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(Channels.newInputStream((
                                new FileInputStream(FileDescriptor.in)).getChannel())));
                setCurrent(input.readLine());

            } catch (Throwable e) {
                System.out.println("You have losed");
            }
        }
    }

    public static void setCurrent(String current) {
        InterruptedInput.current = current;
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new InterruptedInput());
        thread.start();
        while (thread.isAlive()) {
            System.out.println("bamm");
            Thread.sleep(2000);
            System.out.println("Checking...");
            if (getCurrent().equals("")){ thread.interrupt(); break;}
                else setCurrent("");
        }
    }
}