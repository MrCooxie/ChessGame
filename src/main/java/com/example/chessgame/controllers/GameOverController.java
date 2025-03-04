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

/**
 * Controller class responsible for handling the game over screen
 * and its related user interactions in the chess application.
 */
public class GameOverController {

    /**
     * Creates and displays the game over screen when a chess match ends.
     *
     * @param gameResult The result of the completed game (WHITE_WIN, BLACK_WIN, or STALEMATE)
     * @param gridPane   The current GridPane containing the chess board
     */
    public void createMatchOverScreen(GameResult gameResult, GridPane gridPane) {
        // Get the current scene from the grid pane
        Scene scene = gridPane.getScene();

        // Create a new StackPane to overlay the game over screen on top of the chess board
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        // Add the current chess board to the stack pane as the bottom layer
        stackPane.getChildren().add(gridPane);

        // Replace the scene's root with the stack pane
        scene.setRoot(stackPane);

        try {
            // Load the game result screen layout from FXML file
            VBox vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/example/chessgame/fxml/GameResultScreen.fxml")));

            // Store reference to the grid pane for potential later use
            vBox.setUserData(gridPane);

            // Add the appropriate result text based on game outcome
            addResultText(gameResult, vBox);

            // Add the game result screen as the top layer in the stack pane
            stackPane.getChildren().add(vBox);

        } catch (IOException e) {
            // Handle any errors that occur during FXML loading
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the appropriate text on the result screen based on game outcome.
     *
     * @param gameResult The result of the game (WHITE_WIN, BLACK_WIN, or STALEMATE)
     * @param vBox       The VBox containing the result text element
     */
    private void addResultText(GameResult gameResult, VBox vBox) {
        // Find the Text element with id "result" in the VBox
        Text text = (Text) vBox.lookup("#result");

        // Set appropriate text based on game result (in Estonian)
        switch (gameResult) {
            case WHITE_WIN -> text.setText("Valge võitis!"); // "White won!"
            case BLACK_WIN -> text.setText("Must võitis!"); // "Black won!"
            case STALEMATE -> text.setText("Mäng lõppes viigiga!"); // "Game ended in a draw!"
        }
    }

    /**
     * Handles click events on buttons in the game over screen.
     * Starts a new chess game when clicked.
     *
     * @param mouseEvent The mouse click event
     */
    @FXML
    private void onButtonClickEvent(MouseEvent mouseEvent) {
        // Get the button that was clicked
        Button button = (Button) mouseEvent.getSource();

        // Get the current scene
        Scene scene = button.getScene();

        // Create a new chess board and set it as the root of the scene to start a new game
        scene.setRoot(new ChessBoard().createChessBoard());
    }

    /**
     * Handles hover events on buttons in the game over screen.
     * Changes button color when mouse hovers over it.
     *
     * @param mouseEvent The mouse hover event
     */
    @FXML
    private void onButtonHoverEvent(MouseEvent mouseEvent) {
        // Get the button that was hovered over
        Button button = (Button) mouseEvent.getSource();

        // Change the button's background color to a lighter green with rounded corners
        button.setStyle("-fx-background-color:rgb(163, 209, 96); -fx-background-radius: 10");
    }

    /**
     * Handles mouse exit events on buttons in the game over screen.
     * Reverts button color when mouse leaves the button.
     *
     * @param mouseEvent The mouse exit event
     */
    @FXML
    private void onButtonExitEvent(MouseEvent mouseEvent) {
        // Get the button that the mouse exited from
        Button button = (Button) mouseEvent.getSource();

        // Revert the button's background color to the default green with rounded corners
        button.setStyle("-fx-background-color: rgb(129, 182, 76); -fx-background-radius: 10");
    }
}