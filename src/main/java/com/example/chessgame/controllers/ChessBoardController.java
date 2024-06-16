package com.example.chessgame.controllers;

import javafx.scene.input.MouseEvent;

public class ChessBoardController {
    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare) {
        calculatePosition(mouseEvent,sizeOfSquare);
    }
    private void calculatePosition(MouseEvent mouseEvent, int sizeOfSquare){
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        int row = (int) y /sizeOfSquare;
        int col = (int) x/sizeOfSquare;
        System.out.println(row + " " + col);
    }
}
