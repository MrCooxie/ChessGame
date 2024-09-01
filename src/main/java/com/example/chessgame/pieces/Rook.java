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
            possibleMoves.addAll(oneDirectionAllPossibleMoves(+1, 0, chessBoardData));
            possibleMoves.addAll(oneDirectionAllPossibleMoves(-1, 0, chessBoardData));
            possibleMoves.addAll(oneDirectionAllPossibleMoves(0, +1, chessBoardData));
            possibleMoves.addAll(oneDirectionAllPossibleMoves(0, -1, chessBoardData));


        }
        return possibleMoves;
    }
    private ArrayList<Position> oneDirectionAllPossibleMoves(int rowIncrement, int colIncrement, ChessBoardData chessBoardData) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        ArrayList<Position> possibleMoves = new ArrayList<>();
        boolean blocked = false;
        for (int i = 1; i < 8; i++) {
            if (!blocked) {
                if (CheckSquares.isWithInBoard(row + i * rowIncrement, col + i * colIncrement)) {
                    if (CheckSquares.squareEmpty(chessBoard, row + i * rowIncrement, col + i * colIncrement)) {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row + i * rowIncrement, col + i * colIncrement, color, this)) {
                            possibleMoves.add(new Position(row + i * rowIncrement, col + i * colIncrement));
                        }
                    } else if (CheckSquares.squareOppositeColor(chessBoard, row + i * rowIncrement, col + i * colIncrement, color)) {
                        if (!CheckSquares.moveCausesCheck(chessBoardData, row + i * rowIncrement, col + i * colIncrement, color, this)) {
                            possibleMoves.add(new Position(row + i * rowIncrement, col + i * colIncrement));
                        }
                        blocked = true;
                    } else {
                        blocked = true;
                    }
                } else {
                    blocked = true;
                }
            }
        }
        return possibleMoves;
    }
}
