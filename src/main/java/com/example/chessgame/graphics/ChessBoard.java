package com.example.chessgame.graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ChessBoard {
    private final Color primaryColor;
    private final Color secondaryColor;
    private final int sizeOfSquare;
    private final Color primaryTextColor;
    private final Color secondaryTextColor;
    public ChessBoard(Color primaryColor, Color secondaryColor, Color primaryTextColor, Color secondaryTextColor, int sizeOfSquare){
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.primaryTextColor = primaryTextColor;
        this.secondaryTextColor = secondaryTextColor;
        this.sizeOfSquare = sizeOfSquare;
    }
    public GridPane createChessBoard(){
        GridPane gridPane = new GridPane();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                StackPane stackPane = new StackPane();
                Color rectangleColor;
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j%2 == 1)){
                    rectangleColor = secondaryColor;
                } else {
                    rectangleColor = primaryColor;
                }

                Rectangle rectangle = new Rectangle(sizeOfSquare,sizeOfSquare, rectangleColor);
                stackPane.getChildren().add(rectangle);
                if( i== 7 || j == 0) {
                    //Here happened a problem, where you can't actually add padding directly to the Text element, because it isn't a Region class, so I would have to use a container instead
                    StackPane textContainer = new StackPane();
                    Text text = new Text();
                    text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                    if(!(j == 0 && i == 7)) {
                        if (j == 0) {
                            textContainer.setPadding(new Insets(5, 0, 0, 5));
                            textContainer.setAlignment(Pos.TOP_LEFT);
                            Color textColor = (i % 2 == 0) ? primaryTextColor : secondaryTextColor;
                            text.setText(String.valueOf(8 - i));
                            text.setFill(textColor);
                        } else {
                            textContainer.setPadding(new Insets(0, 5, 5, 0));
                            textContainer.setAlignment(Pos.BOTTOM_RIGHT);
                            Color textColor = (j % 2 == 0) ? secondaryTextColor : primaryTextColor;
                            text.setText(String.valueOf((char) (j + 97)));
                            text.setFill(textColor);
                        }
                        textContainer.getChildren().add(text);
                        stackPane.getChildren().add(textContainer);
                    } else {
                        //Manually add the square with collapsing text, might fix later

                        StackPane textContainer1 = new StackPane();
                        textContainer1.setPadding(new Insets(5, 0, 0, 5));
                        textContainer1.setAlignment(Pos.TOP_LEFT);

                        StackPane textContainer2 = new StackPane();
                        textContainer2.setPadding(new Insets(0, 5, 5, 0));
                        textContainer2.setAlignment(Pos.BOTTOM_RIGHT);

                        Text text1 = new Text(String.valueOf(1));
                        text1.setFill(secondaryColor);
                        text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

                        Text text2 = new Text("a");
                        text2.setFill(secondaryColor);
                        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

                        textContainer1.getChildren().add(text1);
                        textContainer2.getChildren().add(text2);
                        stackPane.getChildren().addAll(textContainer1, textContainer2);
                    }

                }
                gridPane.add(stackPane, j, i);
            }
        }
        return gridPane;
    }
}
