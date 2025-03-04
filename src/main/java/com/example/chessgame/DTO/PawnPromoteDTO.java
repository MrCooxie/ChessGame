package com.example.chessgame.DTO;

import com.example.chessgame.data.ChessBoardData;
import javafx.scene.layout.GridPane;

/**
 * Data Transfer Object (DTO) for handling pawn promotion.
 * <p>
 * This record encapsulates all the necessary information required
 * to process a pawn's promotion on the chess board.
 *
 * @param gridPane       The JavaFX GridPane representing the visual chess board
 * @param row            The row where the pawn is being promoted
 * @param col            The column where the pawn is being promoted
 * @param chessBoardData The current state of the chess board
 */
public record PawnPromoteDTO(GridPane gridPane,     // The visual grid of the chess board
                             int row,               // Vertical position of the promoting pawn
                             int col,               // Horizontal position of the promoting pawn
                             ChessBoardData chessBoardData  // Current game state and board information
) {
    // Java records automatically generate constructor, getters, equals(), hashCode(), and toString() methods
}