package tictactoe.state;

import tictactoe.engine.GameEngine;

public interface GameState {
    void handle(GameEngine engine);
}