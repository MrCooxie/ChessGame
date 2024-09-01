package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(char color, int row, int col) {
        super(color, row, col);
        letter = 'R';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            boolean[] directionBlocked = {false, false, false, false};
            for (int i = 1; i < 8; i++) {
                if (!directionBlocked[0]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col, color)) {
                        directionBlocked[0] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row - i, col, color, this)) {
                            possibleMoves.add(new Position(row - i, col));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row - i, col, color)) {
                            directionBlocked[0] = true;
                        }
                    }
                }
                if (!directionBlocked[1]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col, color)) {
                        directionBlocked[1] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row + i, col, color, this)) {
                            possibleMoves.add(new Position(row + i, col));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row + i, col, color)) {
                            directionBlocked[1] = true;
                        }
                    }
                }
                if (!directionBlocked[2]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row, col - i, color)) {
                        directionBlocked[2] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row, col - i, color, this)) {
                            possibleMoves.add(new Position(row, col - i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row, col - i, color)) {
                            directionBlocked[2] = true;
                        }
                    }
                }
                if (!directionBlocked[3]) {
                    if (!CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row, col + i, color)) {
                        directionBlocked[3] = true;
                    } else {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row, col + i, color, this)) {
                            possibleMoves.add(new Position(row, col + i));
                        }
                        if (CheckSquares.squareOppositeColor(chessBoardData.getChessBoard(), row, col + i, color)) {
                            directionBlocked[3] = true;
                        }
                    }
                }

            }
        }
        return possibleMoves;
    }
}
