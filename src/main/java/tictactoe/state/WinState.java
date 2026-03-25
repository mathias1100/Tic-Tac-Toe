package tictactoe.state;

import tictactoe.engine.GameEngine;

public class WinState implements GameState {
    public void handle(GameEngine engine) {
        System.out.println("Game won!");
    }
}