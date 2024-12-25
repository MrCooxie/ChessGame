package com.example.chessgame.pieces;

import com.example.chessgame.controllers.GameOverController;
import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.data.GameResult;
import javafx.scene.layout.GridPane;

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

    public void move(int row, int col, ChessBoardData chessBoardData, boolean isCastling, boolean isEnPassant, GridPane gridPane){
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

        checkForGameEnd(chessBoardData, gridPane);


    }

    public abstract ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData);

    private void checkForGameEnd(ChessBoardData chessBoardData, GridPane gridPane){
        switch (getGameResult(chessBoardData)){
            case BLACK_WIN -> {
                new GameOverController().createMatchOverScreen(GameResult.BLACK_WIN, gridPane);
            }
            case WHITE_WIN -> {
                new GameOverController().createMatchOverScreen(GameResult.WHITE_WIN, gridPane);
            }
            case PLAY_ON -> {

            }
            case STALEMATE -> {
                new GameOverController().createMatchOverScreen(GameResult.STALEMATE, gridPane);
            }
        }
    }
    private GameResult getGameResult(ChessBoardData chessBoardData){
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        //Check if opponent can make any move
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Piece checkingPiece = chessBoard[row][col];
                if(checkingPiece != null && checkingPiece.getColor() == chessBoardData.getTurn()){
                    if(!checkingPiece.getPossibleMoves(chessBoardData).isEmpty()){
                        return GameResult.PLAY_ON;
                    }
                }
            }
        }

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(chessBoard[row][col] != null && chessBoard[row][col] instanceof King && chessBoard[row][col].getColor() == chessBoardData.getTurn()){
                    if(((King) chessBoard[row][col]).isUnderCheck(chessBoard)){
                        if(chessBoardData.getTurn() == 'b'){
                            return GameResult.WHITE_WIN;
                        } else {
                            return GameResult.BLACK_WIN;

                        }
                        //Stalemate and stuff
                    }
                }
            }
        }
        return GameResult.STALEMATE;
    }
    @Override
    public String toString() {
        return "Piece{" + "color=" + color + ", row=" + row + ", col=" + col + ", letter=" + letter + '}';
    }
}
