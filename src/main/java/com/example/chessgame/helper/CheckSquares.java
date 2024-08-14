package com.example.chessgame.helper;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.pieces.King;
import com.example.chessgame.pieces.Knight;
import com.example.chessgame.pieces.Piece;

import java.util.ArrayList;

public class CheckSquares {
    private CheckSquares() {

    }

    public static boolean squareEmpty(Piece[][] chessBoard, int row, int col) {
        return isWithInBoard(row, col) && chessBoard[row][col] == null;
    }

    public static boolean squareOppositeColor(Piece[][] chessBoard, int row, int col, char color) {
        return (isWithInBoard(row, col) && chessBoard[row][col] != null && !(chessBoard[row][col].getColor() == color));
    }

    private static boolean isWithInBoard(int row, int col) {
        return (row >= 0 && row <= 7) && (col >= 0 && col <= 7);
    }

    public static ArrayList<Position> getAllDiagonalMoves(Piece[][] chessBoard, int row, int col, char color) {
        ArrayList<Position> allPossibleMoves = new ArrayList<>();
        boolean[] diagonalBlocked = {false, false, false, false}; //0 -> top left, 1 -> top right -> 2 bot left, 3 -> bot left
        for (int i = 1; i < 8; i++) {
            //I don't think there is no need to add funky math here, but maybe later
            if (!diagonalBlocked[0]) {
                if (squareEmpty(chessBoard, row - i, col - i)) {
                    allPossibleMoves.add(new Position(row - i, col - i));
                } else if (squareOppositeColor(chessBoard, row - i, col - i, color)) {
                    allPossibleMoves.add(new Position(row - i, col - i));
                    diagonalBlocked[0] = true;
                } else {
                    diagonalBlocked[0] = true;
                }
            }
            if (!diagonalBlocked[1]) {
                if (squareEmpty(chessBoard, row - i, col + i)) {
                    allPossibleMoves.add(new Position(row - i, col + i));
                } else if (squareOppositeColor(chessBoard, row - i, col + i, color)) {
                    allPossibleMoves.add(new Position(row - i, col + i));
                    diagonalBlocked[1] = true;
                } else {
                    diagonalBlocked[1] = true;
                }
            }
            if (!diagonalBlocked[2]) {
                if (squareEmpty(chessBoard, row + i, col - i)) {
                    allPossibleMoves.add(new Position(row + i, col - i));
                } else if (squareOppositeColor(chessBoard, row + i, col - i, color)) {
                    allPossibleMoves.add(new Position(row + i, col - i));
                    diagonalBlocked[2] = true;
                } else {
                    diagonalBlocked[2] = true;
                }
            }
            if (!diagonalBlocked[3]) {
                if (squareEmpty(chessBoard, row + i, col + i)) {
                    allPossibleMoves.add(new Position(row + i, col + i));
                } else if (squareOppositeColor(chessBoard, row + i, col + i, color)) {
                    allPossibleMoves.add(new Position(row + i, col + i));
                    diagonalBlocked[3] = true;
                } else {
                    diagonalBlocked[3] = true;
                }
            }

        }
        return allPossibleMoves;
    }

    public static ArrayList<Position> getAllStraightMoves(Piece[][] chessBoard, int row, int col, char color) {
        ArrayList<Position> allPossibleMoves = new ArrayList<>();
        boolean[] straightBlocked = {false, false, false, false}; //0 -> top, 1 -> bot -> 2 ->  left, 3 -> right
        for (int i = 1; i < 8; i++) {
            //is kinda similar to getAllDiagonalMoves, but I don't really want to combine them to avoid confusion.
            if (!straightBlocked[0]) {
                if (squareEmpty(chessBoard, row - i, col)) {
                    allPossibleMoves.add(new Position(row - i, col));
                } else if (squareOppositeColor(chessBoard, row - i, col, color)) {
                    allPossibleMoves.add(new Position(row - i, col));
                    straightBlocked[0] = true;
                } else {
                    straightBlocked[0] = true;
                }
            }
            if (!straightBlocked[1]) {
                if (squareEmpty(chessBoard, row + i, col)) {
                    allPossibleMoves.add(new Position(row + i, col));
                } else if (squareOppositeColor(chessBoard, row + i, col, color)) {
                    allPossibleMoves.add(new Position(row + i, col));
                    straightBlocked[1] = true;
                } else {
                    straightBlocked[1] = true;
                }
            }
            if (!straightBlocked[2]) {
                if (squareEmpty(chessBoard, row, col - i)) {
                    allPossibleMoves.add(new Position(row, col - i));
                } else if (squareOppositeColor(chessBoard, row, col - i, color)) {
                    allPossibleMoves.add(new Position(row, col - i));
                    straightBlocked[2] = true;
                } else {
                    straightBlocked[2] = true;
                }
            }
            if (!straightBlocked[3]) {
                if (squareEmpty(chessBoard, row, col + i)) {
                    allPossibleMoves.add(new Position(row, col + i));
                } else if (squareOppositeColor(chessBoard, row, col + i, color)) {
                    allPossibleMoves.add(new Position(row, col + i));
                    straightBlocked[3] = true;
                } else {
                    straightBlocked[3] = true;
                }
            }
        }
        return allPossibleMoves;
    }

    public static ArrayList<Position> getStraightAndDiagonalMoves(Piece[][] chessBoard, int row, int col, char color) {
        ArrayList<Position> allPossibleMoves = new ArrayList<>();
        allPossibleMoves.addAll(getAllDiagonalMoves(chessBoard, row, col, color));
        allPossibleMoves.addAll(getAllStraightMoves(chessBoard, row, col, color));
        return allPossibleMoves;
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
                        System.out.println("Not check");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
