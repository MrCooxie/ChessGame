package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;

import java.util.ArrayList;

public abstract class Piece {
    protected final char color;
    protected int row;
    protected int col;
    protected char letter;

    protected boolean hasMoved = false;

    public Piece(char color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public char getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public int getCol() {
        return col;
    }

    public char getLetter() {
        return letter;
    }

    public void move(int row, int col, ChessBoardData chessBoardData, boolean isCastling, boolean isEnPassant){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if(isCastling){
            ((King) this).castle(chessBoard, row, col);
        }
        if(isEnPassant){
            assert this instanceof Pawn;
            ((Pawn) this).enPassant(chessBoard);

        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(chessBoard[i][j] instanceof Pawn){
                    ((Pawn) chessBoard[i][j]).setMovedFar(false);
                }
            }
        }
        if(this instanceof Pawn && Math.abs(this.row - row) == 2){
            ((Pawn) this).setMovedFar(true);

        }
        chessBoard[this.row][this.col] = null;
        this.row = row;
        this.col = col;
        chessBoard[row][col] = this;

        chessBoardData.nextTurn();
        hasMoved = true;
    }

    public abstract ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData);

    @Override
    public String toString() {
        return "Piece{" + "color=" + color + ", row=" + row + ", col=" + col + ", letter=" + letter + '}';
    }
}
