package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(char color, int row, int col) {
        super(color, row, col);
        letter = 'N';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            if (checkKnightMove(chessBoardData, row + 2, col - 1, color, this))
                possibleMoves.add(new Position(row + 2, col - 1));
            if (checkKnightMove(chessBoardData, row + 2, col + 1, color, this))
                possibleMoves.add(new Position(row + 2, col + 1));
            if (checkKnightMove(chessBoardData, row - 2, col - 1, color, this))
                possibleMoves.add(new Position(row - 2, col - 1));
            if (checkKnightMove(chessBoardData, row - 2, col + 1, color, this))
                possibleMoves.add(new Position(row - 2, col + 1));
            if (checkKnightMove(chessBoardData, row + 1, col + 2, color, this))
                possibleMoves.add(new Position(row + 1, col + 2));
            if (checkKnightMove(chessBoardData, row - 1, col + 2, color, this))
                possibleMoves.add(new Position(row - 1, col + 2));
            if (checkKnightMove(chessBoardData, row + 1, col - 2, color, this))
                possibleMoves.add(new Position(row + 1, col - 2));
            if (checkKnightMove(chessBoardData, row - 1, col - 2, color, this))
                possibleMoves.add(new Position(row - 1, col - 2));
        }
        return possibleMoves;
    }

    private boolean checkKnightMove(ChessBoardData chessBoardData, int row, int col, char color, Piece piece) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        return (((CheckSquares.squareInBoardAndEmpty(chessBoard, row, col)) || CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col, color)) && !CheckSquares.moveCausesCheck(chessBoardData, row, col, color, piece));
    }
}
