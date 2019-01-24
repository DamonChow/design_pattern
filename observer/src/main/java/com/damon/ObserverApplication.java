package com.damon;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Scanner;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-24 11:42
 */
@Slf4j
public class ObserverApplication {

    public static void main(String[] args) {
        log.info("Enter Text: ");
        EventSource eventSource = new EventSource();

        eventSource.addObserver((obj, arg) -> {
            log.info("Received response: {}", arg);
        });

        eventSource.addObserver((obj, arg) -> {
            log.info("Received response2: {},,{}", arg, obj
            );
        });

        new Thread(eventSource).start();
    }
}

class EventSource extends Observable implements Runnable {
    public void run() {
        while (true) {
            String response = new Scanner(System.in).next();
            setChanged();
            notifyObservers(response);
        }
    }
}
