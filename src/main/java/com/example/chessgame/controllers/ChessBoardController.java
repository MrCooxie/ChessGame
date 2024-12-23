package com.example.chessgame.controllers;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;
import com.example.chessgame.pieces.Pawn;
import com.example.chessgame.pieces.Piece;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Stack;

public class ChessBoardController {


    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        GridPane gridPane = (GridPane) mouseEvent.getSource();
        handleMoveLogic(clickedRow,clickedCol, gridPane, chessBoardData);

    }
    private void handleMoveLogic(int clickedRow, int clickedCol, GridPane gridPane, ChessBoardData chessBoardData){
        //Remove previous hints.
        Position previousClick = (Position) gridPane.getUserData();
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if(previousClick == null || chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()] == null){
            //No hints to remove so just add new hints
            if(chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane);
            }
            gridPane.setUserData(new Position(clickedRow,clickedCol));
        } else {
            removeHints(chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData),gridPane);
            if(chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane);
            }
            ArrayList<Position> legalMoves = chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData);
            for(Position move : legalMoves){
                if(move.getRow() == clickedRow && move.getCol() == clickedCol) {
                    //Was a legal move make changes in graphics and logic.
                    Piece movingPiece = chessBoard[previousClick.getRow()][previousClick.getCol()];

                    if(move.isEnPassant()){
                        if(CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, previousClick.getRow(), previousClick.getCol() + 1, movingPiece.getColor()) && chessBoard[previousClick.getRow()][previousClick.getCol() + 1] instanceof Pawn && ((Pawn) chessBoard[previousClick.getRow()][previousClick.getCol() + 1]).isMovedFar()){
                            StackPane stackPane = ((StackPane)gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() + 1));
                            for(Node item : stackPane.getChildren()){
                                if(item instanceof ImageView imageView) {
                                    stackPane.getChildren().remove(imageView);
                                    break;
                                }
                            }
                        } else {
                            StackPane stackPane = ((StackPane)gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() - 1));
                            for(Node item : stackPane.getChildren()){
                                if(item instanceof ImageView imageView) {
                                    stackPane.getChildren().remove(imageView);
                                    break;
                                }
                            }
                        }
                    }
                    if(move.isCastling()){
                        //Need to move the rook
                        if(clickedCol > previousClick.getCol()){
                            StackPane rookStackPane;
                            int index;
                            if(movingPiece.getColor() == 'b'){
                                rookStackPane = (StackPane) gridPane.getChildren().get(7);
                                index = 7;
                            } else {
                                rookStackPane = (StackPane) gridPane.getChildren().get(63);
                                index = 63;
                            }
                            for(Node children : rookStackPane.getChildren()){
                                if(children instanceof ImageView temporary){
                                    rookStackPane.getChildren().remove(temporary);
                                    ((StackPane) gridPane.getChildren().get(index - 2)).getChildren().add(temporary);
                                    break;
                                }
                            }
                        } else {
                            StackPane rookStackPane;
                            int index;
                            if(movingPiece.getColor() == 'b'){
                                rookStackPane = (StackPane) gridPane.getChildren().get(0);
                                index = 0;
                            } else {
                                rookStackPane = (StackPane) gridPane.getChildren().get(56);
                                index = 56;
                            }
                            for(Node children : rookStackPane.getChildren()){
                                if(children instanceof ImageView temporary){
                                    rookStackPane.getChildren().remove(temporary);
                                    ((StackPane) gridPane.getChildren().get(index + 3)).getChildren().add(temporary);
                                    break;
                                }
                            }
                        }

                    }
                    movingPiece.move(clickedRow,clickedCol,chessBoardData, move.isCastling(), move.isEnPassant());
                    StackPane square = (StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol());
                    for(Node children : square.getChildren()){
                        if(children instanceof ImageView){
                            StackPane targetSquare = (StackPane) gridPane.getChildren().get(clickedRow * 8 + clickedCol);
                                for(Node targetChildren : targetSquare.getChildren()){
                                    if(targetChildren instanceof ImageView){
                                        targetSquare.getChildren().remove(targetChildren);
                                        break;
                                    }
                                }
                                targetSquare.getChildren().add(children);
                                break;
                        }
                    }

                //
                }
            }
            gridPane.setUserData(new Position(clickedRow,clickedCol));
        }


    }

    private void addHints(ArrayList<Position> placesToAddHints, GridPane gridPane){
        for(Position place : placesToAddHints){
            StackPane square = (StackPane) gridPane.getChildren().get(place.getRow() * 8 + place.getCol());
            Circle circle = new Circle(11.5, Color.rgb(0, 0, 0, 0.14));
            square.getChildren().add(circle);
        }
    }
    private void removeHints(ArrayList<Position> placesToRemoveHints, GridPane gridPane){
        for (Position move : placesToRemoveHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(move.getRow() * 8 + move.getCol());
            square.getChildren().removeIf(item -> item instanceof Circle);
        }
    }

}
