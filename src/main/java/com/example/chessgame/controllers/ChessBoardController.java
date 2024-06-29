package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class ChessBoardController {


    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        ArrayList<Position> possibleMoves = chessBoardData.getChessBoard()[clickedRow][clickedCol].getPossibleMoves(chessBoardData);
        addGraphicalPossibleMoves((GridPane) mouseEvent.getSource(), possibleMoves);
    }

    private void addGraphicalPossibleMoves(GridPane gridPane, ArrayList<Position> possibleMoves) {
        ObservableList<Node> children = gridPane.getChildren();
        //First remove previous hints
        if (gridPane.getUserData() != null) {
            ArrayList<Position> hintLocations = (ArrayList<Position>) gridPane.getUserData(); //Expects the UserData is always that type might fix later
            for (Position move : hintLocations) {
                StackPane square = (StackPane) children.get(move.getRow() * 8 + move.getCol());
                square.getChildren().removeIf(item -> item instanceof Circle);
            }
        }

        gridPane.setUserData(possibleMoves); //Add information about the previous hint locations
        for (Position move : possibleMoves) {
            StackPane square = (StackPane) children.get(move.getRow() * 8 + move.getCol());
            Circle circle = new Circle(11.5, Color.rgb(0, 0, 0, 0.14));
            square.getChildren().add(circle);
        }
    }
}
