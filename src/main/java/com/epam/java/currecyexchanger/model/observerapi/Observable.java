package com.epam.java.currecyexchanger.model.observerapi;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}
