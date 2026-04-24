package tictactoe.observer;

import tictactoe.engine.GameEngine;

public interface GameObserver {
    void onGameUpdated(GameEngine engine, String message);
}
