package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ChessBoardController {

    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        ArrayList<Position> possibleMoves = chessBoardData.getChessBoard()[clickedRow][clickedCol].getPossibleMoves(chessBoardData);
        System.out.print(possibleMoves);
    }
}
