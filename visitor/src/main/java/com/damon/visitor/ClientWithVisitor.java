package com.damon.visitor;

/**
 * 功能：使用访问者模式的客户端
 *
 * @author Damon
 * @since 2019-01-07 16:21
 */
public class ClientWithVisitor {

    public static void main(String[] args) {
        ElementStructure structure = new ElementStructure();
        structure.addElement(new Wheel("front left"));
        structure.addElement(new Wheel("front right"));
        structure.addElement(new Wheel("back left"));
        structure.addElement(new Wheel("back right"));
        structure.addElement(new Body());
        structure.addElement(new Engine());
        structure.addElement(new Car());

        structure.accept(new DoElementVisitor());
    }
}
