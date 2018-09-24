package com.module.alevel.arraylist;

import java.util.Arrays;

public class ArrayList<T> {
    private T[] data;
    private int currentPosition;

    ArrayList() {
        this(10);
    }

    ArrayList(int amount) {
        this.data = (T[]) new Object[amount];
        this.currentPosition = 0;
    }

    public int add(T dataElement) {
        if (currentPosition == size()) {
            T[] temporaryMatrix = (T[]) new Object[size() + (size() / 2) + 1];
            temporaryMatrix = Arrays.copyOf(this.data, size());
            this.data = temporaryMatrix;
        }
        this.data[currentPosition] = dataElement;
        currentPosition++;
        return (currentPosition-1);
    }

    public int size() {
        return this.data.length;
    }

    public void printList() {
        for (T element : this.data) {
            if (element == null) {
                break;
            }
            System.out.println(element.toString());
        }
    }

    public boolean remove(int index) {
        boolean erased = false;
        if ((index < size()) && (index >= 0) && (this.data[index] != null)) {
            for (int i = index; i < size() - 1; i++) {
                if (this.data[index] == null) break;
                this.data[i] = this.data[i + 1];
                erased = true;
            }
        }
        return erased;
    }

    public void removeAll() {
        int length = size();
        this.data = (T[]) new Object[length];
    }

    public T get(int index){
        if(((index < size()) && (index >= 0)) ) {
            return this.data[index];
        }
        else{
            return null;
        }
    }
}
