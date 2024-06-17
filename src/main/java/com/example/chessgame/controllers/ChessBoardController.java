package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import javafx.scene.input.MouseEvent;

public class ChessBoardController {

    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        chessBoardData.printChessBoard();
    }
}
