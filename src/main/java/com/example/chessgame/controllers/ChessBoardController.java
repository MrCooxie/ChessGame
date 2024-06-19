package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ChessBoardController {

    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        chessBoardData.removePieceFromBoard(6, 6);
        chessBoardData.removePieceFromBoard(6, 4);
        chessBoardData.printChessBoard();
        ArrayList<Position> possibleMoves = chessBoardData.getChessBoard()[clickedRow][clickedCol].getPossibleMoves(chessBoardData);
    }
}
