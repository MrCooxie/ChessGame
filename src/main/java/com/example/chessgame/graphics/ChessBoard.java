package com.example.chessgame.graphics;

import com.example.chessgame.controllers.ChessBoardController;
import com.example.chessgame.data.ChessBoardData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * Represents the visual chess board using JavaFX components.
 * Handles the creation and styling of the chess board including squares, pieces, and coordinate labels.
 */
public class ChessBoard {
    // Static fields for board styling
    private static Color primaryColor = null;      // Main color for board squares
    private static Color secondaryColor = null;    // Alternate color for board squares
    private static int sizeOfSquare = 0;          // Size of each square in pixels
    private static Color primaryTextColor = null;  // Main color for coordinate labels
    private static Color secondaryTextColor = null; // Alternate color for coordinate labels

    /**
     * Constructor to initialize the chess board's visual properties
     */
    public ChessBoard(Color primaryColor, Color secondaryColor, Color primaryTextColor, Color secondaryTextColor, int sizeOfSquare) {
        ChessBoard.primaryColor = primaryColor;
        ChessBoard.secondaryColor = secondaryColor;
        ChessBoard.primaryTextColor = primaryTextColor;
        ChessBoard.secondaryTextColor = secondaryTextColor;
        ChessBoard.sizeOfSquare = sizeOfSquare;
    }

    public ChessBoard() {
        // Default constructor
    }

    // Getter methods for board colors
    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static Color getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * Creates and returns a complete chess board as a JavaFX GridPane
     *
     * @return GridPane containing the chess board with pieces and coordinates
     */
    public GridPane createChessBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        ChessBoardData chessBoardData = new ChessBoardData();

        // Set up mouse click event handling
        gridPane.setOnMouseClicked(event -> new ChessBoardController().handleMouseClick(event, sizeOfSquare, chessBoardData));

        // Create the 8x8 board grid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane rectangleParent = new StackPane();

                // Determine square color based on position
                Color rectangleColor;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    rectangleColor = secondaryColor;
                } else {
                    rectangleColor = primaryColor;
                }

                // Create and add the square
                Rectangle rectangle = new Rectangle(sizeOfSquare, sizeOfSquare, rectangleColor);
                rectangleParent.getChildren().add(rectangle);

                // Add coordinate labels for the bottom row and leftmost column
                if (i == 7 || j == 0) {
                    StackPane textContainer1 = new StackPane();
                    StackPane textContainer2 = new StackPane();

                    // Set padding and alignment for coordinate labels
                    textContainer1.setPadding(new Insets(5, 0, 0, 5));
                    textContainer2.setPadding(new Insets(0, 5, 5, 0));
                    textContainer1.setAlignment(Pos.TOP_LEFT);
                    textContainer2.setAlignment(Pos.BOTTOM_RIGHT);

                    // Add row numbers (8-1)
                    if (j == 0) {
                        Text text = new Text();
                        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                        text.setFill((i % 2 == 0) ? primaryTextColor : secondaryTextColor);
                        text.setText(String.valueOf(8 - i));
                        textContainer1.getChildren().add(text);
                        rectangleParent.getChildren().add(textContainer1);
                    }

                    // Add column letters (a-h)
                    if (i == 7) {
                        Text text = new Text();
                        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                        text.setText(String.valueOf((char) (j + 97)));
                        text.setFill((j % 2 == 0) ? secondaryTextColor : primaryTextColor);
                        textContainer2.getChildren().add(text);
                        rectangleParent.getChildren().add(textContainer2);
                    }
                }

                // Place major pieces on first and last rows
                if (i == 0 || i == 7) {
                    char color = (i == 0) ? 'b' : 'w'; // 'b' for black pieces, 'w' for white pieces
                    // Determine piece type based on column
                    char piece = switch (j) {
                        case 0, 7 -> 'r'; // Rook
                        case 1, 6 -> 'n'; // Knight
                        case 2, 5 -> 'b'; // Bishop
                        case 3 -> 'q';    // Queen
                        case 4 -> 'k';    // King
                        default -> '0';   // Should never occur
                    };
                    // Load and add piece image
                    ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" + color + piece + ".png"))));
                    img.setFitHeight(sizeOfSquare);
                    img.setFitWidth(sizeOfSquare);
                    rectangleParent.getChildren().add(img);
                }

                // Place pawns on second and seventh rows
                if (i == 1 || i == 6) {
                    char color = (i == 1) ? 'b' : 'w'; // 'b' for black pawns, 'w' for white pawns
                    ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" + color + "p.png"))));
                    img.setFitHeight(sizeOfSquare);
                    img.setFitWidth(sizeOfSquare);
                    rectangleParent.getChildren().add(img);
                }

                // Add the square with all its components to the grid
                gridPane.add(rectangleParent, j, i);
            }
        }
        return gridPane;
    }
}