package com.damon.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-26 15:24
 */
public class Caretaker {

    public static void main(String[] args) {
        List<Memento> savedStates = new ArrayList();
        Originator originator = new Originator();
        originator.set("State1");
        originator.set("State2");
        savedStates.add(originator.saveToMemento());

        originator.set("State3");
        savedStates.add(originator.saveToMemento());

        originator.set("State4");
        originator.restoreFromMemento(savedStates.get(1));
    }
}
