package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
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

            checkKnightMoveToEmptySquare(chessBoardData, row + 2, col - 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 2, col + 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 2, col - 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 2, col + 1, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 1, col + 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 1, col + 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row + 1, col - 2, color, this, possibleMoves);
            checkKnightMoveToEmptySquare(chessBoardData, row - 1, col - 2, color, this, possibleMoves);

            checkKnightTakePiece(chessBoardData, row + 2, col - 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 2, col + 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 2, col - 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 2, col + 1, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 1, col + 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 1, col + 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row + 1, col - 2, color, this, possibleMoves);
            checkKnightTakePiece(chessBoardData, row - 1, col - 2, color, this, possibleMoves);

        }
        return possibleMoves;
    }

    private void checkKnightMoveToEmptySquare(ChessBoardData chessBoardData, int row, int col, char color, Piece piece, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if ((CheckSquares.squareInBoardAndEmpty(chessBoard, row, col)) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, piece)) {
            possibleMoves.add(new Position(row, col));
        }
    }

    private void checkKnightTakePiece(ChessBoardData chessBoardData, int row, int col, char color, Piece piece, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col, color) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, piece)) {
            possibleMoves.add(new Position(row, col, Move.TAKING));
        }
    }
}
