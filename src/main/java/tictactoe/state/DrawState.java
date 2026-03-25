package tictactoe.state;

import tictactoe.engine.GameEngine;

public class DrawState implements GameState {
    public void handle(GameEngine engine) {
        System.out.println("Draw!");
    }
}