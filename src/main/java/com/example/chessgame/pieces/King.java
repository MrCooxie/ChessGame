package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class King extends Piece {
    public King(char color, int row, int col) {
        super(color, row, col);
        letter = 'K';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {

        }
        return possibleMoves;
    }

    public boolean isUnderCheck(Piece[][] chessBoard) {
        return canKnightAttack(chessBoard);
    }

    private boolean canKnightAttack(Piece[][] chessBoard) {
        return (CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col + 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col + 2, color));
    }
}
