package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(char color, int row, int col) {
        super(color, row, col);
        letter = 'P';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            int moveDirection = (color == 'b') ? 1 : -1;
            Piece[][] chessBoard = chessBoardData.getChessBoard();
            if (CheckSquares.squareInBoardAndEmpty(chessBoard, row + moveDirection, col)) {
                if (!CheckSquares.moveCausesCheck(chessBoardData, row + moveDirection, col, color, this)) {
                    possibleMoves.add(new Position(row + moveDirection, col));
                }
                if (!(hasMoved) && CheckSquares.squareInBoardAndEmpty(chessBoard, row + 2 * moveDirection, col) && !CheckSquares.moveCausesCheck(chessBoardData, row + 2 * moveDirection, col, color, this)) {
                    possibleMoves.add(new Position(row + 2 * moveDirection, col));
                }
            }
            if (CheckSquares.squareOppositeColor(chessBoard, row + moveDirection, col + 1, color) && !CheckSquares.moveCausesCheck(chessBoardData, row + moveDirection, col + 1, color, this))
                possibleMoves.add(new Position(row + moveDirection, col + 1));
            if (CheckSquares.squareOppositeColor(chessBoard, row + moveDirection, col - 1, color) && !CheckSquares.moveCausesCheck(chessBoardData, row + moveDirection, col - 1, color, this))
                possibleMoves.add(new Position(row + moveDirection, col - 1));
        }
        return possibleMoves;
    }
}
