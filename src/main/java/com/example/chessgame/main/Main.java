package com.example.chessgame.main;

import com.example.chessgame.graphics.ChessBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main application class for the chess game.
 * This class serves as the entry point for the JavaFX application
 * and sets up the initial game window.
 */
public class Main extends Application {
    /**
     * The main method that launches the JavaFX application.
     * JavaFX will call the start() method after initializing the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(); // Standard JavaFX launch method from Application class
    }

    /**
     * The primary entry point for all JavaFX applications.
     * This method is called after the application has been initialized.
     * It sets up the chess board, configures the stage (window), and displays it.
     *
     * @param stage The primary stage (window) for this application
     */
    @Override
    public void start(Stage stage) {
        // Create a new Scene containing the chess board with specified colors and square size
        Scene scene = new Scene(new ChessBoard(Color.rgb(119, 149, 86),  // Dark square color (medium green)
                Color.rgb(235, 236, 208), // Light square color (cream/beige)
                Color.rgb(115, 149, 82),  // Highlight color for dark squares (slightly different green)
                Color.rgb(235, 236, 208), // Highlight color for light squares (same as light squares)
                92                        // Size of each chess square in pixels
        ).createChessBoard());

        // Configure and display the stage (window)
        stage.setScene(scene);        // Set the scene to display
        stage.setResizable(false);    // Prevent the window from being resized
        stage.show();                 // Display the window
    }
}