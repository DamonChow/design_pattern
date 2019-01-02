package com.damon.simple;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-02 17:13
 */
public class ConcreteAggregate<E> implements Aggregate<E> {

    private Object[] elements;

    private int size = 0;

    public ConcreteAggregate() {
        elements = new Object[16];
    }

    public int getSize() {
        return size;
    }

    public void add(E e) {
        elements[size++] = e;
    }

    @Override
    public Iterator<E> iterator() {
        return new ConcreteIterator<E>();
    }

    private class ConcreteIterator<E> implements Iterator<E> {

        int cursor;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            if (cursor >= size) {
                return null;
            }
            return (E) elements[cursor++];
        }

    }
}
