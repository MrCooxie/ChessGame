package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
    public King(char color, int row, int col) {
        super(color, row, col);
        letter = 'K';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
          //if(canMoveToSquare(chessBoardData, row + 1, col +1)) possibleMoves.add(new Position(row + 1, col + 1));
            if(canMoveToSquare(chessBoardData, row + 1, col)) possibleMoves.add(new Position(row + 1, col));
            if(canMoveToSquare(chessBoardData, row + 1, col - 1)) possibleMoves.add(new Position(row + 1, col - 1));

            if(canMoveToSquare(chessBoardData, row , col +1)) possibleMoves.add(new Position(row, col + 1));
            if(canMoveToSquare(chessBoardData, row, col - 1)) possibleMoves.add(new Position(row, col - 1));

            if(canMoveToSquare(chessBoardData, row - 1, col +1)) possibleMoves.add(new Position(row - 1, col + 1));
            if(canMoveToSquare(chessBoardData, row - 1, col)) possibleMoves.add(new Position(row - 1, col));
            if(canMoveToSquare(chessBoardData, row - 1, col - 1)) possibleMoves.add(new Position(row - 1, col - 1));
        }
        return possibleMoves;
    }
    private boolean canMoveToSquare(ChessBoardData chessBoardData, int row, int col){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        //System.out.print(row + ", " + col);
        //if(CheckSquares.isWithInBoard(row, col) && ((CheckSquares.squareEmpty(chessBoard, row, col) || (!CheckSquares.squareEmpty(chessBoard, row, col) && CheckSquares.squareOppositeColor(chessBoard, row, col, color))))) System.out.println(!CheckSquares.moveCausesCheck(chessBoardData, row, col, color, this));
        return CheckSquares.isWithInBoard(row, col) && ((CheckSquares.squareEmpty(chessBoard, row, col) || (!CheckSquares.squareEmpty(chessBoard, row, col) && CheckSquares.squareOppositeColor(chessBoard, row, col, color))) && !CheckSquares.moveCausesCheck(chessBoardData, row, col, color, this));

    }

    public boolean isUnderCheck(Piece[][] chessBoard) {
        return canKnightAttack(chessBoard) || canDiagonalAttack(chessBoard) || canStraightAttack(chessBoard);
    }

    private boolean canKnightAttack(Piece[][] chessBoard) {
        return (CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col + 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col + 2, color));
    }

    private boolean canDiagonalAttack(Piece[][] chessBoard) {
        return canDirectionAttack(1, 1, chessBoard) || canDirectionAttack(-1, -1, chessBoard) || canDirectionAttack(-1, 1, chessBoard) || canDirectionAttack(1, -1, chessBoard);
    }

    private boolean canStraightAttack(Piece[][] chessBoard) {
        return canDirectionAttack(1, 0, chessBoard) || canDirectionAttack(-1, 0, chessBoard) || canDirectionAttack(0, 1, chessBoard) || canDirectionAttack(0, -1, chessBoard);
    }

    private boolean canDirectionAttack(int rowIncrement, int colIncrement, Piece[][] chessBoard) {
        for (int i = 1; i < 8; i++) {
            if (CheckSquares.isWithInBoard(row + i * rowIncrement, col + i * colIncrement)) {
                if (CheckSquares.squareEmpty(chessBoard, row + i * rowIncrement, col + i * colIncrement)) {
                    continue;
                } else if (CheckSquares.squareOppositeColor(chessBoard, row + i * rowIncrement, col + i * colIncrement, color)) {
                    Piece piece = chessBoard[row + i * rowIncrement][col + i * colIncrement];
                    if ((rowIncrement == 0 || colIncrement == 0) && (piece instanceof Rook || piece instanceof Queen)) {//means can attack straight
                        return true;
                    }
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
