package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(char color, int row, int col) {
        super(color, row, col);
        letter = 'B';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            boolean[] diagonalBlocked = {false, false, false, false};
            for (int i = 1; i < 8; i++) {
                if (!diagonalBlocked[0]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col - i, color)) {
                        diagonalBlocked[0] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row - i, col - i, color, this)) {
                            possibleMoves.add(new Position(row - i, col - i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col - i, color)) {
                            diagonalBlocked[0] = true;
                        }
                    }
                }
                if (!diagonalBlocked[1]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col + i, color)) {
                        diagonalBlocked[1] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row - i, col + i, color, this)) {
                            possibleMoves.add(new Position(row - i, col + i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col + i, color)) {
                            diagonalBlocked[1] = true;
                        }
                    }
                }
                if (!diagonalBlocked[2]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col - i, color)) {
                        diagonalBlocked[2] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row + i, col - i, color, this)) {
                            possibleMoves.add(new Position(row + i, col - i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col - i, color)) {
                            diagonalBlocked[2] = true;
                        }
                    }
                }
                if (!diagonalBlocked[3]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col + i, color)) {
                        diagonalBlocked[3] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row + i, col + i, color, this)) {
                            possibleMoves.add(new Position(row + i, col + i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col + i, color)) {
                            diagonalBlocked[3] = true;
                        }
                    }
                }

            }
        }
        return possibleMoves;
    }
}
