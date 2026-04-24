package tictactoe.observer;

import tictactoe.engine.GameEngine;

public class ConsoleGameObserver implements GameObserver {
    @Override
    public void onGameUpdated(GameEngine engine, String message) {
        System.out.println(message);
    }
}
