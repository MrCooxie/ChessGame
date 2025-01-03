package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class Pawn extends Piece {

    private boolean movedFar = false;

    public Pawn(char color, int row, int col) {
        super(color, row, col);
        letter = 'P';
    }

    public boolean isMovedFar() {
        return movedFar;
    }

    public void setMovedFar(boolean movedFar) {
        this.movedFar = movedFar;
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            int moveDirection = (color == 'b') ? 1 : -1;
            Piece[][] chessBoard = chessBoardData.getChessBoard();
            if (CheckSquares.squareInBoardAndEmpty(chessBoard, row + moveDirection, col)) {
                if (CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col, color, this)) {
                    if (pawnToOtherSide(row + moveDirection)) {
                        possibleMoves.add(new Position(row + moveDirection, col, Move.PROMOTE));
                    } else {
                        possibleMoves.add(new Position(row + moveDirection, col));
                    }
                    possibleMoves.add(new Position(row + moveDirection, col));
                }
                if (!(hasMoved) && CheckSquares.squareInBoardAndEmpty(chessBoard, row + 2 * moveDirection, col) && CheckSquares.moveNotCheck(chessBoardData, row + 2 * moveDirection, col, color, this)) {
                    possibleMoves.add(new Position(row + 2 * moveDirection, col));
                }
            }
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + moveDirection, col + 1, color) && CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col + 1, color, this)) {
                if (pawnToOtherSide(row + moveDirection)) {
                    possibleMoves.add(new Position(row + moveDirection, col + 1, Move.PROMOTE_TAKING));
                } else {
                    possibleMoves.add(new Position(row + moveDirection, col + 1, Move.TAKING));
                }

            }
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + moveDirection, col - 1, color) && CheckSquares.moveNotCheck(chessBoardData, row + moveDirection, col - 1, color, this)) {
                if (pawnToOtherSide(row + moveDirection)) {
                    possibleMoves.add(new Position(row + moveDirection, col - 1, Move.PROMOTE_TAKING));

                } else {
                    possibleMoves.add(new Position(row + moveDirection, col - 1, Move.TAKING));

                }
            }

            //En Passant
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col - 1, color) && chessBoard[row][col - 1] instanceof Pawn && ((Pawn) chessBoard[row][col - 1]).isMovedFar()) {
                possibleMoves.add(new Position(row + moveDirection, col - 1, Move.EN_PASSANT));
            }
            if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col + 1, color) && chessBoard[row][col + 1] instanceof Pawn && ((Pawn) chessBoard[row][col + 1]).isMovedFar()) {
                possibleMoves.add(new Position(row + moveDirection, col + 1, Move.EN_PASSANT));
            }
        }
        return possibleMoves;
    }

    private boolean pawnToOtherSide(int row) {
        return (row == 0 && color == 'w') || (row == 7 && color == 'b');
    }

    public void enPassant(Piece[][] chessBoard) {
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col + 1, color) && chessBoard[row][col + 1] instanceof Pawn && ((Pawn) chessBoard[row][col + 1]).isMovedFar()) {
            chessBoard[row][col + 1] = null;

        } else {
            chessBoard[row][col - 1] = null;

        }
    }

    public void promotePiece(Piece[][] chessBoard, String piece) {
        char promotedPieceColor = piece.charAt(0);
        char pieceToPromoteTO = piece.charAt(1);
        switch (pieceToPromoteTO) {
            case 'q' -> chessBoard[this.row][this.col] = new Queen(promotedPieceColor, this.row, this.col);
            case 'r' -> chessBoard[this.row][this.col] = new Rook(promotedPieceColor, this.row, this.col);
            case 'b' -> chessBoard[this.row][this.col] = new Bishop(promotedPieceColor, this.row, this.col);
            case 'n' -> chessBoard[this.row][this.col] = new Knight(promotedPieceColor, this.row, this.col);
        }
    }
}
