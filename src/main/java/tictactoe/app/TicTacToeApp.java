package tictactoe.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoe.engine.GameEngine;
import tictactoe.facade.GameFacade;
import tictactoe.model.Board;
import tictactoe.model.Mark;
import tictactoe.model.MatchResult;
import tictactoe.model.Position;
import tictactoe.observer.GameObserver;
import tictactoe.service.GameMode;

import java.util.List;

public class TicTacToeApp extends Application implements GameObserver {
    private static final double BOARD_VISUAL_SIZE = 720;
    private static final double BOARD_CLICKABLE_SIZE = 520;

    private final Button[][] buttons = new Button[Board.SIZE][Board.SIZE];
    private final Label statusLabel = new Label("Choose a mode and start playing.");
    private final ListView<String> historyList = new ListView<>();
    private final ComboBox<GameMode> modeSelector = new ComboBox<>();

    private final GameFacade gameFacade = new GameFacade();
    private GameEngine engine;

    @Override
    public void start(Stage stage) {
        modeSelector.getItems().addAll(GameMode.HUMAN_VS_HUMAN, GameMode.HUMAN_VS_AI);
        modeSelector.setValue(GameMode.HUMAN_VS_HUMAN);
        modeSelector.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GameMode item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getLabel());
            }
        });
        modeSelector.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(GameMode item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getLabel());
            }
        });

        StackPane boardPane = buildBoardPane();

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> initializeEngine(modeSelector.getValue()));

        Label titleLabel = new Label("Tic-Tac-Toe");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        controlsStyle(newGameButton, modeSelector, statusLabel, historyList);

        Label modeLabel = new Label("Mode:");
        modeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        HBox controls = new HBox(10, modeLabel, modeSelector, newGameButton);
        controls.setAlignment(Pos.CENTER_LEFT);

        VBox topPanel = new VBox(12, titleLabel, controls);
        topPanel.setPadding(new Insets(16));
        topPanel.setMaxWidth(Double.MAX_VALUE);
        topPanel.setStyle(panelStyle());

        Label historyLabel = new Label("Recent Matches");
        historyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        VBox rightPanel = new VBox(10, historyLabel, historyList);
        rightPanel.setPadding(new Insets(16));
        rightPanel.setPrefWidth(320);
        rightPanel.setMinWidth(300);
        rightPanel.setStyle(panelStyle());
        VBox.setVgrow(historyList, Priority.ALWAYS);

        BorderPane content = new BorderPane();
        content.setPadding(new Insets(18));
        content.setTop(topPanel);
        content.setCenter(boardPane);
        content.setRight(rightPanel);
        content.setBottom(statusLabel);
        BorderPane.setMargin(topPanel, new Insets(0, 0, 18, 0));
        BorderPane.setMargin(boardPane, new Insets(0, 18, 0, 0));
        BorderPane.setMargin(statusLabel, new Insets(18, 0, 0, 0));

        StackPane root = new StackPane(content);
        root.setPadding(new Insets(10));
        root.setBackground(new Background(new BackgroundFill(Color.web("#0d2c78"), CornerRadii.EMPTY, Insets.EMPTY)));

        initializeEngine(modeSelector.getValue());
        refreshHistory();

        Scene scene = new Scene(root, 1220, 860);
        stage.setTitle("Tic-Tac-Toe JavaFX");
        stage.setMinWidth(1180);
        stage.setMinHeight(840);
        stage.setScene(scene);
        stage.show();
    }

    private String panelStyle() {
        return "-fx-background-color: rgba(9, 25, 76, 0.74);"
                + "-fx-background-radius: 18;"
                + "-fx-border-color: rgba(255,255,255,0.18);"
                + "-fx-border-radius: 18;";
    }

    private void controlsStyle(Button newGameButton, ComboBox<GameMode> modeSelector, Label statusLabel, ListView<String> historyList) {
        newGameButton.setStyle(
                "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 10;"
                        + "-fx-padding: 10 18 10 18;"
        );
        modeSelector.setStyle("-fx-font-size: 15px; -fx-background-radius: 10;");
        statusLabel.setStyle(
                "-fx-text-fill: white;"
                        + "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-color: rgba(9,25,76,0.82);"
                        + "-fx-background-radius: 16;"
                        + "-fx-padding: 14;"
        );
        historyList.setStyle(
                "-fx-control-inner-background: rgba(255,255,255,0.95);"
                        + "-fx-background-color: rgba(255,255,255,0.95);"
                        + "-fx-background-radius: 12;"
        );
    }

    private StackPane buildBoardPane() {
        Image background = new Image(getClass().getResourceAsStream("/tictactoe-background.png"));
        ImageView boardImage = new ImageView(background);
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(BOARD_VISUAL_SIZE);
        boardImage.setFitHeight(BOARD_VISUAL_SIZE);
        boardImage.setSmooth(true);

        GridPane clickGrid = new GridPane();
        clickGrid.setHgap(12);
        clickGrid.setVgap(12);
        clickGrid.setAlignment(Pos.CENTER);
        clickGrid.setMaxSize(BOARD_CLICKABLE_SIZE, BOARD_CLICKABLE_SIZE);
        clickGrid.setPrefSize(BOARD_CLICKABLE_SIZE, BOARD_CLICKABLE_SIZE);

        double buttonSize = (BOARD_CLICKABLE_SIZE - 24) / 3.0;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Button button = new Button(" ");
                button.setMinSize(buttonSize, buttonSize);
                button.setPrefSize(buttonSize, buttonSize);
                button.setMaxSize(buttonSize, buttonSize);
                button.setStyle(cellStyle());
                int currentRow = row;
                int currentCol = col;
                button.setOnAction(event -> onBoardClicked(currentRow, currentCol));
                buttons[row][col] = button;
                clickGrid.add(button, col, row);
            }
        }

        StackPane boardStack = new StackPane(boardImage, clickGrid);
        boardStack.setAlignment(Pos.CENTER);
        boardStack.setMinSize(BOARD_VISUAL_SIZE, BOARD_VISUAL_SIZE);
        boardStack.setPrefSize(BOARD_VISUAL_SIZE, BOARD_VISUAL_SIZE);
        boardStack.setMaxSize(BOARD_VISUAL_SIZE, BOARD_VISUAL_SIZE);

        StackPane holder = new StackPane(boardStack);
        holder.setAlignment(Pos.CENTER);
        holder.setPadding(new Insets(8));
        return holder;
    }

    private String cellStyle() {
        return "-fx-font-size: 60px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: rgba(255,255,255,0.10);"
                + "-fx-background-radius: 22;"
                + "-fx-border-color: rgba(255,255,255,0.18);"
                + "-fx-border-radius: 22;"
                + "-fx-border-width: 1.2;";
    }

    private void initializeEngine(GameMode mode) {
        gameFacade.startNewGame(mode, this);
        engine = gameFacade.getEngine();
        renderBoard();
        statusLabel.setText("New " + mode.getLabel() + " game. Turn: " + engine.getCurrentPlayer().getName());
        if (engine.getCurrentPlayer().isAi()) {
            engine.performAiMoveIfNeeded();
            renderBoard();
        }
    }

    private void onBoardClicked(int row, int col) {
        if (engine == null || engine.getCurrentPlayer().isAi() || engine.getState().isTerminal()) {
            return;
        }

        boolean moved = engine.submitHumanMove(new Position(row, col));
        renderBoard();

        if (moved && !engine.getState().isTerminal() && engine.getCurrentPlayer().isAi()) {
            engine.performAiMoveIfNeeded();
            renderBoard();
        }

        if (engine.getState().isTerminal()) {
            refreshHistory();
        }
    }

    private void renderBoard() {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark mark = engine.getBoard().getMarkAt(new Position(row, col));
                Button button = buttons[row][col];
                button.setText(mark == Mark.EMPTY ? "" : mark.name());
                button.setDisable(mark != Mark.EMPTY || engine.getState().isTerminal() || engine.getCurrentPlayer().isAi());
                button.setStyle(cellStyle());
            }
        }
    }

    private void refreshHistory() {
        historyList.getItems().clear();
        List<MatchResult> matches = gameFacade.getRecentMatches(10);
        for (MatchResult match : matches) {
            historyList.getItems().add(match.playedAt() + " | " + match.mode() + " | Winner: " + match.winner());
        }
    }

    @Override
    public void onGameUpdated(GameEngine engine, String message) {
        statusLabel.setText(message);
        renderBoard();
        if (engine.getState().isTerminal()) {
            refreshHistory();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
