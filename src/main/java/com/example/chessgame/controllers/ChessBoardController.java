package com.example.chessgame.controllers;

import com.example.chessgame.DTO.PawnPromoteDTO;
import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.graphics.ChessBoard;
import com.example.chessgame.helper.CheckSquares;
import com.example.chessgame.pieces.Pawn;
import com.example.chessgame.pieces.Piece;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;

public class ChessBoardController {


    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        GridPane gridPane = (GridPane) mouseEvent.getSource();
        handleMoveLogic(clickedRow, clickedCol, gridPane, chessBoardData);

    }

    private void handleMoveLogic(int clickedRow, int clickedCol, GridPane gridPane, ChessBoardData chessBoardData) {
        //Remove previous hints.
        Position previousClick = (Position) gridPane.getUserData();
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (previousClick == null || chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()] == null) {
            //No hints to remove so just add new hints
            if (chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane, clickedRow, clickedCol, chessBoardData);
            }
            gridPane.setUserData(new Position(clickedRow, clickedCol));
        } else {
            removeHints(chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData), gridPane, previousClick.getRow(), previousClick.getCol());
            if (chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane, clickedRow, clickedCol, chessBoardData);
            }
            ArrayList<Position> legalMoves = chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData);
            for (Position move : legalMoves) {
                if (move.getRow() == clickedRow && move.getCol() == clickedCol) {
                    removePreviousMovePositions(gridPane);
                    //Was a legal move make changes in graphics and logic.
                    Piece movingPiece = chessBoard[previousClick.getRow()][previousClick.getCol()];
                    if (move.getSpecialMove() != null) {
                        if (move.getSpecialMove().equals(Move.EN_PASSANT)) {
                            moveIsEnPassant(gridPane, chessBoard, previousClick, movingPiece);
                        }
                        if (move.getSpecialMove().equals(Move.CASTLING)) {
                            moveIsCastling(gridPane, clickedCol, previousClick, movingPiece);
                        }
                        if (move.getSpecialMove().equals(Move.PROMOTE) || move.getSpecialMove().equals(Move.PROMOTE_TAKING)) {
                            StackPane stackPane = new StackPane();
                            Scene scene = gridPane.getScene();
                            stackPane.getChildren().add(gridPane);
                            try {
                                VBox vBox = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/chessgame/fxml/PawnPromoteScreenWhite.fxml"));
                                vBox.setUserData(new PawnPromoteDTO(gridPane, clickedRow, clickedCol, chessBoardData));
                                if (chessBoardData.getTurn() == 'w') {
                                    StackPane.setAlignment(vBox, Pos.TOP_LEFT);
                                    StackPane.setMargin(vBox, new Insets(0, 0, 0, clickedCol * 92));

                                } else {
                                    vBox = FXMLLoader.load(getClass().getClassLoader().getResource("com/example/chessgame/fxml/PawnPromoteScreenBlack.fxml"));
                                    StackPane.setAlignment(vBox, Pos.BOTTOM_LEFT);
                                    StackPane.setMargin(vBox, new Insets(0, 0, 0, clickedCol * 92));
                                    vBox.setUserData(new PawnPromoteDTO(gridPane, clickedRow, clickedCol, chessBoardData));
                                }
                                stackPane.getChildren().add(vBox);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            scene.setRoot(stackPane);


                        }
                    }
                    movingPiece.move(clickedRow, clickedCol, chessBoardData, move.getSpecialMove(), gridPane);
                    StackPane square = (StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol());
                    for (Node children : square.getChildren()) {
                        if (children instanceof ImageView) {
                            StackPane targetSquare = (StackPane) gridPane.getChildren().get(clickedRow * 8 + clickedCol);
                            for (Node targetChildren : targetSquare.getChildren()) {
                                if (targetChildren instanceof ImageView) {
                                    targetSquare.getChildren().remove(targetChildren);
                                    break;
                                }
                            }
                            targetSquare.getChildren().add(children);
                            break;
                        }
                    }
                    addPreviousMovePositions(previousClick.getRow(), previousClick.getCol(), clickedRow, clickedCol, gridPane);
                    break;
                }
            }
            gridPane.setUserData(new Position(clickedRow, clickedCol));
        }


    }

    private void removePreviousMovePositions(GridPane gridPane) {
        //Very slow method, but works

        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            StackPane squares = (StackPane) gridPane.getChildren().get(i);
            for (Node square : squares.getChildren()) {
                if (square instanceof Rectangle) {
                    int row = i / 8;
                    int col = i % 8;
                        if (((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1))) {

                            ((Rectangle) square).setFill(ChessBoard.getSecondaryColor());

                        } else {
                            ((Rectangle) square).setFill(ChessBoard.getPrimaryColor());

                        }
                }
            }
        }
    }

    private void addPreviousMovePositions(int startRow, int startCol, int endRow, int endCol, GridPane gridPane) {
        StackPane startSquare = (StackPane) gridPane.getChildren().get(startRow * 8 + startCol);
        for (Node square : startSquare.getChildren()) {
            if (square instanceof Rectangle) {
                if ((startRow % 2 == 0 && startCol % 2 == 0) || (startRow % 2 == 1 && startCol % 2 == 1)) {

                    ((Rectangle) square).setFill(Color.rgb(245, 246, 130));

                } else {
                    ((Rectangle) square).setFill(Color.rgb(185, 202, 67));

                }
                break;
            }
        }
        StackPane endSquare = (StackPane) gridPane.getChildren().get(endRow * 8 + endCol);
        for (Node square : endSquare.getChildren()) {
            if (square instanceof Rectangle) {
                if ((endRow % 2 == 0 && endCol % 2 == 0) || (endRow % 2 == 1 && endCol % 2 == 1)) {
                    ((Rectangle) square).setFill(Color.rgb(245, 246, 130));

                } else {
                    ((Rectangle) square).setFill(Color.rgb(185, 202, 67));

                }
                break;
            }
        }
    }

    private void moveIsEnPassant(GridPane gridPane, Piece[][] chessBoard, Position previousClick, Piece movingPiece) {
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, previousClick.getRow(), previousClick.getCol() + 1, movingPiece.getColor()) && chessBoard[previousClick.getRow()][previousClick.getCol() + 1] instanceof Pawn && ((Pawn) chessBoard[previousClick.getRow()][previousClick.getCol() + 1]).isMovedFar()) {
            StackPane stackPane = ((StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() + 1));
            for (Node item : stackPane.getChildren()) {
                if (item instanceof ImageView imageView) {
                    stackPane.getChildren().remove(imageView);
                    break;
                }
            }
        } else {
            StackPane stackPane = ((StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() - 1));
            for (Node item : stackPane.getChildren()) {
                if (item instanceof ImageView imageView) {
                    stackPane.getChildren().remove(imageView);
                    break;
                }
            }
        }
    }

    private void moveIsCastling(GridPane gridPane, int clickedCol, Position previousClick, Piece movingPiece) {
        //Need to move the rook
        if (clickedCol > previousClick.getCol()) {
            StackPane rookStackPane;
            int index;
            if (movingPiece.getColor() == 'b') {
                rookStackPane = (StackPane) gridPane.getChildren().get(7);
                index = 7;
            } else {
                rookStackPane = (StackPane) gridPane.getChildren().get(63);
                index = 63;
            }
            for (Node children : rookStackPane.getChildren()) {
                if (children instanceof ImageView temporary) {
                    rookStackPane.getChildren().remove(temporary);
                    ((StackPane) gridPane.getChildren().get(index - 2)).getChildren().add(temporary);
                    break;
                }
            }
        } else {
            StackPane rookStackPane;
            int index;
            if (movingPiece.getColor() == 'b') {
                rookStackPane = (StackPane) gridPane.getChildren().get(0);
                index = 0;
            } else {
                rookStackPane = (StackPane) gridPane.getChildren().get(56);
                index = 56;
            }
            for (Node children : rookStackPane.getChildren()) {
                if (children instanceof ImageView temporary) {
                    rookStackPane.getChildren().remove(temporary);
                    ((StackPane) gridPane.getChildren().get(index + 3)).getChildren().add(temporary);
                    break;
                }
            }
        }
    }

    private void addHints(ArrayList<Position> placesToAddHints, GridPane gridPane, int row, int col, ChessBoardData chessBoardData) {
        for (Position place : placesToAddHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(place.getRow() * 8 + place.getCol());
            if (place.getSpecialMove() == null) {
                Circle circle = new Circle(11.5, Color.rgb(0, 0, 0, 0.14));
                square.getChildren().add(circle);
            } else if (place.getSpecialMove().equals(Move.TAKING) || place.getSpecialMove().equals(Move.PROMOTE_TAKING)) {
                //Custom shape
                Circle outerCircle = new Circle(45, Color.rgb(0, 0, 0, 0.14));

                Circle innerCircle = new Circle(35);

                Shape donut = Shape.subtract(outerCircle, innerCircle);

                donut.setUserData("Hint");


                donut.setFill(Color.rgb(0, 0, 0, 0.14));
                square.getChildren().add(donut);
            }
        }
        if (chessBoardData.getTurn() == chessBoardData.getChessBoard()[row][col].getColor()) {
            StackPane square = (StackPane) gridPane.getChildren().get(row * 8 + col);
            for (Node item : square.getChildren()) {
                if (item instanceof Rectangle) {
                    if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {

                        ((Rectangle) item).setFill(Color.rgb(245, 246, 130));

                    } else {
                        ((Rectangle) item).setFill(Color.rgb(185, 202, 67));

                    }
                    break;
                }
            }
        }

    }

    // rgb(245, 246, 130), rgb(185, 202, 67)
    private void removeHints(ArrayList<Position> placesToRemoveHints, GridPane gridPane, int row, int col) {
        for (Position move : placesToRemoveHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(move.getRow() * 8 + move.getCol());
            for (Node item : square.getChildren()) {
                if (item instanceof Circle) {
                    square.getChildren().remove(item);
                    break;
                }
                if (item instanceof Shape) {
                    if (item.getUserData() != null && item.getUserData().equals("Hint")) {
                        square.getChildren().remove(item);
                        break;
                    }
                }
            }
        }
        if (!placesToRemoveHints.isEmpty()) {
            StackPane square = (StackPane) gridPane.getChildren().get(row * 8 + col);
            for (Node item : square.getChildren()) {
                if (item instanceof Rectangle) {
                    if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                        ((Rectangle) item).setFill(ChessBoard.getSecondaryColor());
                    } else {
                        ((Rectangle) item).setFill(ChessBoard.getPrimaryColor());

                    }
                    break;
                }
            }
        }
    }
}
