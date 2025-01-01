package com.example.chessgame.controllers;

import com.example.chessgame.data.GameResult;
import com.example.chessgame.graphics.ChessBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class GameOverController {

    public void createMatchOverScreen(GameResult gameResult, GridPane gridPane) {
        Scene scene = gridPane.getScene();
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(gridPane);

        scene.setRoot(stackPane);

        try {
            VBox vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/example/chessgame/fxml/GameResultScreen.fxml")));
            vBox.setUserData(gridPane);
            addResultText(gameResult, vBox);
            stackPane.getChildren().add(vBox);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void addResultText(GameResult gameResult, VBox vBox) {
        Text text = (Text) vBox.lookup("#result");
        switch (gameResult) {
            case WHITE_WIN -> text.setText("Valge võitis!");
            case BLACK_WIN -> text.setText("Must võitis!");
            case STALEMATE -> text.setText("Mäng lõppes viigiga!");

        }

    }

    @FXML
    private void onButtonClickEvent(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Scene scene = button.getScene();
        scene.setRoot(new ChessBoard().createChessBoard());


    }

    @FXML
    private void onButtonHoverEvent(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color:rgb(163, 209, 96); -fx-background-radius: 10");

    }

    @FXML
    private void onButtonExitEvent(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: rgb(129, 182, 76); -fx-background-radius: 10");


    }
}
