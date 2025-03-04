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
import java.util.Objects;

/**
 * Controller class for the chess board UI.
 * Handles user interactions with the chess board, such as selecting pieces and making moves.
 */
public class ChessBoardController {

    /**
     * Handles mouse click events on the chess board.
     * Calculates which square was clicked and passes the information to the move logic handler.
     *
     * @param mouseEvent     The mouse event that triggered this method
     * @param sizeOfSquare   The size of each square on the chess board in pixels
     * @param chessBoardData The data model representing the current state of the chess game
     */
    public void handleMouseClick(MouseEvent mouseEvent, int sizeOfSquare, ChessBoardData chessBoardData) {
        // Calculate which row and column was clicked based on mouse position
        int clickedRow = (int) (mouseEvent.getY() / sizeOfSquare);
        int clickedCol = (int) (mouseEvent.getX() / sizeOfSquare);
        GridPane gridPane = (GridPane) mouseEvent.getSource();
        handleMoveLogic(clickedRow, clickedCol, gridPane, chessBoardData);
    }

    /**
     * Handles the logic for selecting pieces and making moves.
     * If no piece was previously selected, this selects a piece and shows possible moves.
     * If a piece was previously selected, this either moves the piece to the clicked square
     * or selects a different piece.
     *
     * @param clickedRow     The row that was clicked
     * @param clickedCol     The column that was clicked
     * @param gridPane       The GridPane representing the chess board
     * @param chessBoardData The data model for the chess game
     */
    private void handleMoveLogic(int clickedRow, int clickedCol, GridPane gridPane, ChessBoardData chessBoardData) {
        // Get the previously clicked position (if any) from the gridPane's userData
        Position previousClick = (Position) gridPane.getUserData();
        Piece[][] chessBoard = chessBoardData.getChessBoard();

        // If no previous click or the previous click was on an empty square
        if (previousClick == null || chessBoardData.getChessBoard()[previousClick.getRow()][previousClick.getCol()] == null) {
            // If the user clicked on a piece, show possible moves
            if (chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane, clickedRow, clickedCol, chessBoardData);
            }
            // Store the current click position for next time
            gridPane.setUserData(new Position(clickedRow, clickedCol));
        } else {
            // Remove hints from the previously selected piece
            removeHints(chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData), gridPane, previousClick.getRow(), previousClick.getCol(), chessBoardData);

            // If the user clicked on a piece, show its possible moves
            if (chessBoard[clickedRow][clickedCol] != null) {
                addHints(chessBoard[clickedRow][clickedCol].getPossibleMoves(chessBoardData), gridPane, clickedRow, clickedCol, chessBoardData);
            }

            // Check if the clicked square is a legal move for the previously selected piece
            ArrayList<Position> legalMoves = chessBoard[previousClick.getRow()][previousClick.getCol()].getPossibleMoves(chessBoardData);
            for (Position move : legalMoves) {
                if (move.getRow() == clickedRow && move.getCol() == clickedCol) {
                    // Remove highlighting from previous move
                    removePreviousMovePositions(gridPane);

                    // Get the piece that's moving
                    Piece movingPiece = chessBoard[previousClick.getRow()][previousClick.getCol()];

                    // Handle special moves (en passant, castling, pawn promotion)
                    if (move.getSpecialMove() != null) {
                        if (move.getSpecialMove().equals(Move.EN_PASSANT)) {
                            moveIsEnPassant(gridPane, chessBoard, previousClick, movingPiece);
                        }
                        if (move.getSpecialMove().equals(Move.CASTLING)) {
                            moveIsCastling(gridPane, clickedCol, previousClick, movingPiece);
                        }
                        if (move.getSpecialMove().equals(Move.PROMOTE) || move.getSpecialMove().equals(Move.PROMOTE_TAKING)) {
                            // Set up UI for pawn promotion
                            StackPane stackPane = new StackPane();
                            Scene scene = gridPane.getScene();
                            stackPane.getChildren().add(gridPane);
                            try {
                                // Load promotion UI based on the current player's color
                                VBox vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/example/chessgame/fxml/PawnPromoteScreenWhite.fxml")));
                                vBox.setUserData(new PawnPromoteDTO(gridPane, clickedRow, clickedCol, chessBoardData));

                                // Position the promotion UI differently based on player color
                                if (chessBoardData.getTurn() == 'w') {
                                    // White promotion UI appears at the top
                                    StackPane.setAlignment(vBox, Pos.TOP_LEFT);
                                    StackPane.setMargin(vBox, new Insets(0, 0, 0, clickedCol * 92));
                                } else {
                                    // Black promotion UI appears at the bottom
                                    vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/example/chessgame/fxml/PawnPromoteScreenBlack.fxml")));
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

                    // Update the game state by moving the piece
                    movingPiece.move(clickedRow, clickedCol, chessBoardData, move.getSpecialMove(), gridPane);

                    // Update the UI by moving the piece's image
                    StackPane sourceSquare = (StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol());
                    for (Node children : sourceSquare.getChildren()) {
                        if (children instanceof ImageView) {
                            // Find the target square and remove any existing image (captured piece)
                            StackPane targetSquare = (StackPane) gridPane.getChildren().get(clickedRow * 8 + clickedCol);
                            for (Node targetChildren : targetSquare.getChildren()) {
                                if (targetChildren instanceof ImageView) {
                                    targetSquare.getChildren().remove(targetChildren);
                                    break;
                                }
                            }
                            // Add the moving piece's image to the target square
                            targetSquare.getChildren().add(children);
                            break;
                        }
                    }

                    // Highlight the source and destination squares of the move
                    addPreviousMovePositions(previousClick.getRow(), previousClick.getCol(), clickedRow, clickedCol, gridPane);
                    break;
                }
            }

            // Store the current click position for next time
            gridPane.setUserData(new Position(clickedRow, clickedCol));
        }
    }

    /**
     * Removes color highlights from previous moves on the board.
     * Resets all squares to their default colors.
     *
     * @param gridPane The chess board's GridPane
     */
    private void removePreviousMovePositions(GridPane gridPane) {
        // Reset all squares to their default colors
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            StackPane squares = (StackPane) gridPane.getChildren().get(i);
            for (Node square : squares.getChildren()) {
                if (square instanceof Rectangle) {
                    int row = i / 8;
                    int col = i % 8;
                    // Set colors based on the checkerboard pattern
                    if (((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1))) {
                        ((Rectangle) square).setFill(ChessBoard.getSecondaryColor());
                    } else {
                        ((Rectangle) square).setFill(ChessBoard.getPrimaryColor());
                    }
                }
            }
        }
    }

    /**
     * Highlights the squares involved in the previous move.
     *
     * @param startRow Starting row of the move
     * @param startCol Starting column of the move
     * @param endRow   Ending row of the move
     * @param endCol   Ending column of the move
     * @param gridPane The chess board's GridPane
     */
    private void addPreviousMovePositions(int startRow, int startCol, int endRow, int endCol, GridPane gridPane) {
        // Highlight the starting square
        StackPane startSquare = (StackPane) gridPane.getChildren().get(startRow * 8 + startCol);
        for (Node square : startSquare.getChildren()) {
            if (square instanceof Rectangle) {
                // Use light or dark yellow based on the square's position
                if ((startRow % 2 == 0 && startCol % 2 == 0) || (startRow % 2 == 1 && startCol % 2 == 1)) {
                    ((Rectangle) square).setFill(Color.rgb(245, 246, 130)); // Light yellow
                } else {
                    ((Rectangle) square).setFill(Color.rgb(185, 202, 67));  // Dark yellow
                }
                break;
            }
        }

        // Highlight the ending square
        StackPane endSquare = (StackPane) gridPane.getChildren().get(endRow * 8 + endCol);
        for (Node square : endSquare.getChildren()) {
            if (square instanceof Rectangle) {
                // Use light or dark yellow based on the square's position
                if ((endRow % 2 == 0 && endCol % 2 == 0) || (endRow % 2 == 1 && endCol % 2 == 1)) {
                    ((Rectangle) square).setFill(Color.rgb(245, 246, 130)); // Light yellow
                } else {
                    ((Rectangle) square).setFill(Color.rgb(185, 202, 67));  // Dark yellow
                }
                break;
            }
        }
    }

    /**
     * Handles the UI update for an en passant capture.
     * Removes the captured pawn from the board.
     *
     * @param gridPane      The chess board's GridPane
     * @param chessBoard    The logical chess board representation
     * @param previousClick The position of the capturing pawn
     * @param movingPiece   The pawn making the en passant capture
     */
    private void moveIsEnPassant(GridPane gridPane, Piece[][] chessBoard, Position previousClick, Piece movingPiece) {
        // Check if the pawn to be captured is to the right of the moving pawn
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, previousClick.getRow(), previousClick.getCol() + 1, movingPiece.getColor()) && chessBoard[previousClick.getRow()][previousClick.getCol() + 1] instanceof Pawn && ((Pawn) chessBoard[previousClick.getRow()][previousClick.getCol() + 1]).isMovedFar()) {

            // Remove the captured pawn's image from the UI
            StackPane stackPane = ((StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() + 1));
            for (Node item : stackPane.getChildren()) {
                if (item instanceof ImageView imageView) {
                    stackPane.getChildren().remove(imageView);
                    break;
                }
            }
        } else {
            // The pawn to be captured is to the left of the moving pawn
            StackPane stackPane = ((StackPane) gridPane.getChildren().get(previousClick.getRow() * 8 + previousClick.getCol() - 1));
            for (Node item : stackPane.getChildren()) {
                if (item instanceof ImageView imageView) {
                    stackPane.getChildren().remove(imageView);
                    break;
                }
            }
        }
    }

    /**
     * Handles the UI update for castling.
     * Moves the rook to its new position.
     *
     * @param gridPane      The chess board's GridPane
     * @param clickedCol    The column the king is moving to
     * @param previousClick The king's original position
     * @param movingPiece   The king that's castling
     */
    private void moveIsCastling(GridPane gridPane, int clickedCol, Position previousClick, Piece movingPiece) {
        // Check if this is kingside castling (king moves to the right)
        if (clickedCol > previousClick.getCol()) {
            StackPane rookStackPane;
            int index;

            // Different starting positions based on piece color
            if (movingPiece.getColor() == 'b') {
                // Black's kingside rook starts at h8 (index 7)
                rookStackPane = (StackPane) gridPane.getChildren().get(7);
                index = 7;
            } else {
                // White's kingside rook starts at h1 (index 63)
                rookStackPane = (StackPane) gridPane.getChildren().get(63);
                index = 63;
            }

            // Move the rook image to its new position (two squares left of its original position)
            for (Node children : rookStackPane.getChildren()) {
                if (children instanceof ImageView temporary) {
                    rookStackPane.getChildren().remove(temporary);
                    ((StackPane) gridPane.getChildren().get(index - 2)).getChildren().add(temporary);
                    break;
                }
            }
        } else {
            // This is queenside castling (king moves to the left)
            StackPane rookStackPane;
            int index;

            // Different starting positions based on piece color
            if (movingPiece.getColor() == 'b') {
                // Black's queenside rook starts at a8 (index 0)
                rookStackPane = (StackPane) gridPane.getChildren().get(0);
                index = 0;
            } else {
                // White's queenside rook starts at a1 (index 56)
                rookStackPane = (StackPane) gridPane.getChildren().get(56);
                index = 56;
            }

            // Move the rook image to its new position (three squares right of its original position)
            for (Node children : rookStackPane.getChildren()) {
                if (children instanceof ImageView temporary) {
                    rookStackPane.getChildren().remove(temporary);
                    ((StackPane) gridPane.getChildren().get(index + 3)).getChildren().add(temporary);
                    break;
                }
            }
        }
    }

    /**
     * Adds visual hints to show possible moves for a selected piece.
     *
     * @param placesToAddHints List of positions where hints should be added
     * @param gridPane         The chess board's GridPane
     * @param row              The row of the selected piece
     * @param col              The column of the selected piece
     * @param chessBoardData   The chess game data
     */
    private void addHints(ArrayList<Position> placesToAddHints, GridPane gridPane, int row, int col, ChessBoardData chessBoardData) {
        // Add visual indicators for each possible move
        for (Position place : placesToAddHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(place.getRow() * 8 + place.getCol());

            // Different visual indicators for capturing moves vs. non-capturing moves
            if (place.getSpecialMove() != null && (place.getSpecialMove().equals(Move.TAKING) || place.getSpecialMove().equals(Move.PROMOTE_TAKING))) {
                // For capturing moves, show a hollow circle
                Circle outerCircle = new Circle(45, Color.rgb(0, 0, 0, 0.14));
                Circle innerCircle = new Circle(35);
                Shape donut = Shape.subtract(outerCircle, innerCircle);
                donut.setUserData("Hint");
                donut.setFill(Color.rgb(0, 0, 0, 0.14));
                square.getChildren().add(donut);
            } else {
                // For non-capturing moves, show a small circle
                Circle circle = new Circle(11.5, Color.rgb(0, 0, 0, 0.14));
                square.getChildren().add(circle);
            }
        }

        // Highlight the selected piece if it's the current player's turn
        if (chessBoardData.getTurn() == chessBoardData.getChessBoard()[row][col].getColor()) {
            StackPane square = (StackPane) gridPane.getChildren().get(row * 8 + col);
            for (Node item : square.getChildren()) {
                if (item instanceof Rectangle) {
                    // Use light or dark yellow based on the square's position
                    if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                        ((Rectangle) item).setFill(Color.rgb(245, 246, 130)); // Light yellow
                    } else {
                        ((Rectangle) item).setFill(Color.rgb(185, 202, 67));  // Dark yellow
                    }
                    break;
                }
            }
        }
    }

    /**
     * Removes visual hints for possible moves.
     *
     * @param placesToRemoveHints List of positions where hints should be removed
     * @param gridPane            The chess board's GridPane
     * @param row                 The row of the previously selected piece
     * @param col                 The column of the previously selected piece
     * @param chessBoardData      The chess game data
     */
    private void removeHints(ArrayList<Position> placesToRemoveHints, GridPane gridPane, int row, int col, ChessBoardData chessBoardData) {
        // Remove visual indicators for each possible move
        for (Position move : placesToRemoveHints) {
            StackPane square = (StackPane) gridPane.getChildren().get(move.getRow() * 8 + move.getCol());
            for (Node item : square.getChildren()) {
                // Remove circles (non-capturing move indicators)
                if (item instanceof Circle) {
                    square.getChildren().remove(item);
                    break;
                }
                // Remove donuts (capturing move indicators)
                if (item instanceof Shape) {
                    if (item.getUserData() != null && item.getUserData().equals("Hint")) {
                        square.getChildren().remove(item);
                        break;
                    }
                }
            }
        }

        // Reset the color of the previously selected piece's square
        StackPane square = (StackPane) gridPane.getChildren().get(row * 8 + col);
        Piece piece = chessBoardData.getChessBoard()[row][col];
        if (piece.getColor() == chessBoardData.getTurn()) {
            for (Node item : square.getChildren()) {
                if (item instanceof Rectangle) {
                    // Reset to the default color based on the checkerboard pattern
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