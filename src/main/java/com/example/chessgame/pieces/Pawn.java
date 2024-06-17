package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public class Pawn extends Piece {

    private final boolean hasMoved = false;

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
            if (chessBoard[row + moveDirection][col] == null) {
                possibleMoves.add(new Position(row + moveDirection, col));
                if (!(hasMoved) && chessBoard[row + moveDirection * 2][col] == null) {
                    possibleMoves.add(new Position(row + 2 * moveDirection, col));
                }
            }
            if ( chessBoard[row + moveDirection][col + 1] != null && !(chessBoard[row + moveDirection][col + 1].getColor() == color))
                possibleMoves.add(new Position(row + moveDirection, col + 1));
            if (chessBoard[row + moveDirection][col - 1] != null && !(chessBoard[row + moveDirection][col - 1].getColor() == color))
                possibleMoves.add(new Position(row + moveDirection, col - 1));

        }
        return possibleMoves;
    }
}
