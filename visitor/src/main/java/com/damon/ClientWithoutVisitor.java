package com.damon;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：非访问者模式
 *
 * @author Damon
 * @since 2019-01-07 16:21
 */
@Slf4j
public class ClientWithoutVisitor {

    public static void main(String[] args) {
        ElementStructure structure = new ElementStructure();
        structure.addElement(new Wheel("front left"));
        structure.addElement(new Wheel("front right"));
        structure.addElement(new Wheel("back left"));
        structure.addElement(new Wheel("back right"));
        structure.addElement(new Body());
        structure.addElement(new Engine());
        structure.addElement(new Car());

        structure.getList().forEach(e -> {
            if (e instanceof Body) {
                log.info("Moving my body");
            } else if (e instanceof Engine) {
                log.info("Starting my engine");
            } else if (e instanceof Car) {
                log.info("Starting my car");
            } else if (e instanceof Wheel) {
                log.info("Kicking my " + ((Wheel)e).getName() + " wheel");
            }
        });
    }

}
