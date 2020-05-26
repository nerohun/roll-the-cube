package rollthecube.javafx.controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import rollthecube.results.GameResult;
import rollthecube.results.GameResultDao;
import rollthecube.state.Map;
import rollthecube.state.RolltheCubeState;


import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static rollthecube.state.Map.*;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    private String playerName;
    private RolltheCubeState gameState;
    //private Map gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private List<Image> cubeImages;

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    Map m = new Map();
    //int [][] map = getMap();
    public int currX= getCurrX();
    public int currY= getCurrY();
    @FXML
    public void initialize() {

        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                gameResultDao.persist(createGameResult());
                stopWatchTimeline.stop();

            }
        });

        resetGame();

    }

    private void resetGame() {

        Map.resetMap();
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText("Good luck, " + playerName + "!"));
    }



    private void displayGameState() {
        log.debug("MAP: {}",map);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

                Pane pane = (Pane) gameGrid.getChildren().get(i*7+j);
                pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                if (map[j][i]==1) {
                    //Not playable
                    pane.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if (map[j][i]==2) {
                    //Finish line
                    pane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 255, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                }else if (map[j][i]==3) {
                    //Player Position
                    pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                    pane.setBorder(new Border(new BorderStroke(Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    
                }else{
                    pane.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255),CornerRadii.EMPTY,Insets.EMPTY)));
                }
            }
        }


    }



    public void handleClickOnCube(MouseEvent mouseEvent) {

        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());

        log.debug("Cube ({}, {}) is pressed", row, col);

        m.isFree(col,row);

        steps.set(lepes);
        stepsLabel.textProperty().bind(steps.asString());
        displayGameState();
        if (m.isSix()){
            log.info("Rosszra fordult a kocka ! :)");
            gameOver.setValue(true);
            log.info("Player {} has rolled the cube to the wrong side ", playerName, steps.get());
            messageLabel.setText("Better luck next time, " + playerName + "!");
            resetButton.setDisable(true);
            giveUpButton.setText("Finish");

        }
        if (m.isWin()){
            log.info("Nyertél");
            gameOver.setValue(true);
            log.info("Player {} has solved the game in {} steps", playerName, steps.get());
            messageLabel.setText("Congratulations, " + playerName + "!");
            resetButton.setDisable(true);
            giveUpButton.setText("Finish");
        }

    }


    public void handleResetButton(ActionEvent actionEvent)  {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        resetGame();
    }

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(true);
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private GameResult createGameResult() {
        Map map = new Map();
        GameResult result = GameResult.builder()
                .player(playerName)
                .solved(map.isWin())
                .duration(Duration.between(startTime, Instant.now()))
                .steps(steps.get())
                .build();
        return result;
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();

    }

}
