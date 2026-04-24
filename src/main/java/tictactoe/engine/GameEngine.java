package tictactoe.engine;

import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.MatchResult;
import tictactoe.model.Position;
import tictactoe.observer.GameObserver;
import tictactoe.player.HumanPlayer;
import tictactoe.player.Player;
import tictactoe.repository.MatchHistoryRepository;
import tictactoe.service.GameMode;
import tictactoe.state.DrawState;
import tictactoe.state.GameState;
import tictactoe.state.InProgressState;
import tictactoe.state.WinState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private final MatchHistoryRepository historyRepository;
    private final GameMode mode;
    private final List<GameObserver> observers;

    private Player currentPlayer;
    private GameState state;
    private boolean resultSaved;

    public GameEngine(Player playerX, Player playerO, MatchHistoryRepository historyRepository, GameMode mode) {
        this.board = new Board();
        this.playerX = playerX;
        this.playerO = playerO;
        this.historyRepository = historyRepository;
        this.mode = mode;
        this.observers = new ArrayList<>();
        this.currentPlayer = playerX;
        this.state = new InProgressState();
        this.resultSaved = false;
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void detach(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.onGameUpdated(this, message);
        }
    }

    public boolean submitHumanMove(Position position) {
        if (state.isTerminal()) {
            notifyObservers("The game is already over.");
            return false;
        }
        if (!(currentPlayer instanceof HumanPlayer humanPlayer)) {
            notifyObservers("It is not a human player's turn.");
            return false;
        }

        humanPlayer.getHumanStrategy().setMove(position);
        return executeTurn();
    }

    public boolean performAiMoveIfNeeded() {
        if (!state.isTerminal() && currentPlayer.isAi()) {
            return executeTurn();
        }
        return false;
    }

    private boolean executeTurn() {
        try {
            Position move = currentPlayer.makeMove(board);
            board.placeMark(move, currentPlayer.getMark());
            notifyObservers(currentPlayer.getName() + " placed " + currentPlayer.getMark() + " at " + move + ".");
            evaluateGameState();
            if (!state.isTerminal()) {
                switchPlayer();
                notifyObservers("Next turn: " + currentPlayer.getName() + " (" + currentPlayer.getMark() + ")");
            }
            return true;
        } catch (RuntimeException ex) {
            notifyObservers("Invalid move: " + ex.getMessage());
            return false;
        }
    }

    private void evaluateGameState() {
        if (board.hasWinner(currentPlayer.getMark())) {
            state = new WinState(currentPlayer.getMark());
            notifyObservers(state.getStatusMessage());
            saveResultIfNeeded(currentPlayer.getMark().name());
        } else if (board.isFull()) {
            state = new DrawState();
            notifyObservers(state.getStatusMessage());
            saveResultIfNeeded("DRAW");
        } else {
            state = new InProgressState();
        }
    }

    private void saveResultIfNeeded(String winner) {
        if (resultSaved) {
            return;
        }
        historyRepository.save(new MatchResult(
                playerX.getName(),
                playerO.getName(),
                winner,
                mode.getLabel(),
                LocalDateTime.now().toString()
        ));
        resultSaved = true;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == playerX ? playerO : playerX;
    }

    public void reset() {
        board.clear();
        currentPlayer = playerX;
        state = new InProgressState();
        resultSaved = false;
        notifyObservers("New game started.");
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public GameState getState() {
        return state;
    }

    public GameMode getMode() {
        return mode;
    }
}
