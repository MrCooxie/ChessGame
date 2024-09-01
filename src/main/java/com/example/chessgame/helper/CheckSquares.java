package com.example.chessgame.helper;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.pieces.King;
import com.example.chessgame.pieces.Knight;
import com.example.chessgame.pieces.Piece;

public class CheckSquares {
    private CheckSquares() {

    }

    public static boolean squareEmptyAndNoCheck(ChessBoardData chessBoardData, int row, int col, char color, Piece piece) {
        return (isWithInBoard(row, col) && chessBoardData.getChessBoard()[row][col] == null && !moveCausesCheck(chessBoardData, row, col, color, piece));
    }

    public static boolean squareOppositeColorAndNoCheck(ChessBoardData chessBoardData, int row, int col, char color, Piece piece) {
        return (isWithInBoard(row, col) && chessBoardData.getChessBoard()[row][col] != null && !(chessBoardData.getChessBoard()[row][col].getColor() == color) && !moveCausesCheck(chessBoardData, row, col, color, piece));
    }

    public static boolean squareEmpty(Piece[][] chessBoard, int row, int col) {
        return chessBoard[row][col] == null;
    }
    public static boolean isWithInBoard(int row, int col) {
        return (row >= 0 && row <= 7) && (col >= 0 && col <= 7);
    }
    public static boolean squareInBoardAndEmpty(Piece[][] chessboard, int row, int col){
        return isWithInBoard(row,col) && squareEmpty(chessboard,row,col);
    }
    public static boolean squareOppositeColor(Piece[][] chessBoard, int row, int col, char color) {
        return !(chessBoard[row][col].getColor() == color);
    }
    public static boolean squareInBoardNotEmptyOppositeColor(Piece[][] chessBoard, int row, int col, char color){
        return (isWithInBoard(row, col) && chessBoard[row][col] != null && squareOppositeColor(chessBoard,row,col,color));
    }




    public static boolean isOppositeColorKnight(Piece[][] chessBoard, int row, int col, char color) {
        if (isWithInBoard(row, col) && !squareEmpty(chessBoard, row, col) && squareOppositeColor(chessBoard, row, col, color)) {
            return chessBoard[row][col] instanceof Knight;
        }
        return false;
    }

    public static boolean moveCausesCheck(ChessBoardData chessBoardData, int row, int col, char color, Piece piece) {
        //TODO: Might introduce a static variable of the position of the kings.
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j] != null && chessBoard[i][j] instanceof King king && chessBoard[i][j].getColor() == color) {
                    //King located

                    Piece takenPiece = chessBoard[row][col];

                    chessBoard[piece.getRow()][piece.getCol()] = null;
                    chessBoard[row][col] = piece;
                    //Can't use piece.move() because then turn changes and hasMoved property as well.
                    if (king.isUnderCheck(chessBoard)) {
                        chessBoard[row][col] = takenPiece;
                        chessBoard[piece.getRow()][piece.getCol()] = piece;
                        System.out.println("Check");
                        return true;
                    } else {
                        chessBoard[row][col] = takenPiece;
                        chessBoard[piece.getRow()][piece.getCol()] = piece;
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
