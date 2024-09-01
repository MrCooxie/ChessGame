package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(char color, int row, int col) {
        super(color, row, col);
        letter = 'Q';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        chessBoard[row][col] = new Bishop(color, row, col);
        ArrayList<Position> diagonalMoves = chessBoard[row][col].getPossibleMoves(chessBoardData);
        chessBoard[row][col] = new Rook(color, row, col);
        ArrayList<Position> straightMoves = chessBoard[row][col].getPossibleMoves(chessBoardData);
        chessBoard[row][col] = this;
        diagonalMoves.addAll(straightMoves);
        return diagonalMoves;
    }
}
