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
            Piece[][] chessBoard = chessBoardData.getChessBoard();
            if (CheckSquares.squareEmpty(chessBoard, row + 2, col - 1) || CheckSquares.squareOppositeColor(chessBoard, row + 2, col - 1, color))
                possibleMoves.add(new Position(row + 2, col - 1));
            if (CheckSquares.squareEmpty(chessBoard, row + 2, col + 1) || CheckSquares.squareOppositeColor(chessBoard, row + 2, col + 1, color))
                possibleMoves.add(new Position(row + 2, col + 1));
            if (CheckSquares.squareEmpty(chessBoard, row - 2, col - 1) || CheckSquares.squareOppositeColor(chessBoard, row - 2, col - 1, color))
                possibleMoves.add(new Position(row - 2, col - 1));
            if (CheckSquares.squareEmpty(chessBoard, row - 2, col + 1) || CheckSquares.squareOppositeColor(chessBoard, row - 2, col + 1, color))
                possibleMoves.add(new Position(row - 2, col + 1));
            if (CheckSquares.squareEmpty(chessBoard, row + 1, col + 2) || CheckSquares.squareOppositeColor(chessBoard, row + 1, col + 2, color))
                possibleMoves.add(new Position(row + 1, col + 2));
            if (CheckSquares.squareEmpty(chessBoard, row - 1, col + 2) || CheckSquares.squareOppositeColor(chessBoard, row - 1, col + 2, color))
                possibleMoves.add(new Position(row - 1, col + 2));
            if (CheckSquares.squareEmpty(chessBoard, row + 1, col - 2) || CheckSquares.squareOppositeColor(chessBoard, row + 1, col - 2, color))
                possibleMoves.add(new Position(row + 1, col - 2));
            if (CheckSquares.squareEmpty(chessBoard, row - 1, col - 2) || CheckSquares.squareOppositeColor(chessBoard, row - 1, col - 2, color))
                possibleMoves.add(new Position(row - 1, col - 2));

        }
        return possibleMoves;
    }
}
