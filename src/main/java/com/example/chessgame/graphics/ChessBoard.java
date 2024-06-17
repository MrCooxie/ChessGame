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

public class ChessBoard {
    private final Color primaryColor;
    private final Color secondaryColor;
    private final int sizeOfSquare;
    private final Color primaryTextColor;
    private final Color secondaryTextColor;

    public ChessBoard(Color primaryColor, Color secondaryColor, Color primaryTextColor, Color secondaryTextColor, int sizeOfSquare) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.primaryTextColor = primaryTextColor;
        this.secondaryTextColor = secondaryTextColor;
        this.sizeOfSquare = sizeOfSquare;
    }

    public GridPane createChessBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setOnMouseClicked(event -> new ChessBoardController().handleMouseClick(event, sizeOfSquare, new ChessBoardData()));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane rectangleParent = new StackPane();
                Color rectangleColor;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    rectangleColor = secondaryColor;
                } else {
                    rectangleColor = primaryColor;
                }

                Rectangle rectangle = new Rectangle(sizeOfSquare, sizeOfSquare, rectangleColor);
                rectangleParent.getChildren().add(rectangle);
                if (i == 7 || j == 0) {
                    //Here happened a problem, where you can't actually add padding directly to the Text element, because it isn't a Region class, so I would have to use a container instead

                    StackPane textContainer1 = new StackPane();
                    StackPane textContainer2 = new StackPane();


                    textContainer1.setPadding(new Insets(5, 0, 0, 5));
                    textContainer2.setPadding(new Insets(0, 5, 5, 0));

                    textContainer1.setAlignment(Pos.TOP_LEFT);
                    textContainer2.setAlignment(Pos.BOTTOM_RIGHT);

                    if (j == 0) {
                        Text text = new Text();
                        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                        text.setFill((i % 2 == 0) ? primaryTextColor : secondaryTextColor);
                        text.setText(String.valueOf(8 - i));
                        textContainer1.getChildren().add(text);
                        rectangleParent.getChildren().add(textContainer1);
                    }
                    if (i == 7) {
                        Text text = new Text();
                        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                        text.setText(String.valueOf((char) (j + 97)));
                        text.setFill((j % 2 == 0) ? secondaryTextColor : primaryTextColor);
                        textContainer2.getChildren().add(text);
                        rectangleParent.getChildren().add(textContainer2);
                    }

                }
                if (i == 0 || i == 7) {
                    //Can be improved
                    char color = (i == 0) ? 'b' : 'w';
                    char piece = switch (j) {
                        case 0, 7 -> 'r';
                        case 1, 6 -> 'n';
                        case 2, 5 -> 'b';
                        case 3 -> 'q';
                        case 4 -> 'k';
                        default -> '0'; //Magic number
                    };
                    ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" + color + piece + ".png"))));
                    img.setFitHeight(sizeOfSquare);
                    img.setFitWidth(sizeOfSquare);
                    rectangleParent.getChildren().add(img);
                }
                if (i == 1 || i == 6) {
                    char color = (i == 1) ? 'b' : 'w';
                    ImageView img = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/chessgame/images/chess-pieces/" + color + "p.png"))));
                    img.setFitHeight(sizeOfSquare);
                    img.setFitWidth(sizeOfSquare);
                    rectangleParent.getChildren().add(img);
                }
                gridPane.add(rectangleParent, j, i);
            }
        }
        return gridPane;
    }
}
