package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

/**
 * Represents a King chess piece in the chess game.
 * The King can move one square in any direction and has special castling moves.
 * It is the most important piece as the game ends when a King is checkmated.
 */
public class King extends Piece {

    /**
     * Constructs a new King piece with the specified color and starting position.
     *
     * @param color The color of the king ('w' for white, 'b' for black)
     * @param row   The starting row position (0-7)
     * @param col   The starting column position (0-7)
     */
    public King(char color, int row, int col) {
        super(color, row, col);
        letter = 'K'; // Sets the character representation of the King to 'K'
    }

    /**
     * Calculates all possible valid moves for this King based on the current board state.
     * Includes regular one-square moves in all directions and special castling moves.
     *
     * @param chessBoardData The current state of the chess board
     * @return An ArrayList of all valid positions the King can move to
     */
    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {
            // Check all eight directions for empty squares
            canMoveToEmptySquare(chessBoardData, row + 1, col + 1, possibleMoves); // Down-right
            canMoveToEmptySquare(chessBoardData, row + 1, col, possibleMoves);     // Down
            canMoveToEmptySquare(chessBoardData, row + 1, col - 1, possibleMoves); // Down-left
            canMoveToEmptySquare(chessBoardData, row, col + 1, possibleMoves);     // Right
            canMoveToEmptySquare(chessBoardData, row, col - 1, possibleMoves);     // Left
            canMoveToEmptySquare(chessBoardData, row - 1, col + 1, possibleMoves); // Up-right
            canMoveToEmptySquare(chessBoardData, row - 1, col, possibleMoves);     // Up
            canMoveToEmptySquare(chessBoardData, row - 1, col - 1, possibleMoves); // Up-left

            // Check all eight directions for enemy pieces that can be captured
            canMoveOnPiece(chessBoardData, row + 1, col + 1, possibleMoves); // Down-right
            canMoveOnPiece(chessBoardData, row + 1, col, possibleMoves);     // Down
            canMoveOnPiece(chessBoardData, row + 1, col - 1, possibleMoves); // Down-left
            canMoveOnPiece(chessBoardData, row, col + 1, possibleMoves);     // Right
            canMoveOnPiece(chessBoardData, row, col - 1, possibleMoves);     // Left
            canMoveOnPiece(chessBoardData, row - 1, col + 1, possibleMoves); // Up-right
            canMoveOnPiece(chessBoardData, row - 1, col, possibleMoves);     // Up
            canMoveOnPiece(chessBoardData, row - 1, col - 1, possibleMoves); // Up-left

            // Check special castling moves
            canCastleKingSide(chessBoardData, possibleMoves);
            canCastleQueenSide(chessBoardData, possibleMoves);
        }
        return possibleMoves;
    }

    /**
     * Checks if the King can castle on the queenside (long castle).
     * Requirements: King and Rook haven't moved, spaces between are empty,
     * King isn't in check, and King doesn't pass through check.
     *
     * @param chessBoardData The current state of the chess board
     * @param possibleMoves  List to add the castling move to if valid
     */
    private void canCastleQueenSide(ChessBoardData chessBoardData, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        // Check if king can castle: hasn't moved, not in check, path is clear, and doesn't move through check
        if (!hasMoved && !isUnderCheck(chessBoard) && CheckSquares.squareEmpty(chessBoard, row, col - 1) && CheckSquares.squareEmpty(chessBoard, row, col - 2) && CheckSquares.moveNotCheck(chessBoardData, row, col + 1, color, this) && CheckSquares.moveNotCheck(chessBoardData, row, col + 2, color, this)) {

            // Check for black king castling with the rook at a8
            if (color == 'b' && !CheckSquares.squareEmpty(chessBoard, 0, 0) && chessBoard[0][0] instanceof Rook && chessBoard[0][0].color == 'b' && !chessBoard[0][0].hasMoved) {
                possibleMoves.add(new Position(row, col - 2, Move.CASTLING));
            }
            // Check for white king castling with the rook at a1
            else if (color == 'w' && !CheckSquares.squareEmpty(chessBoard, 7, 0) && chessBoard[7][0] instanceof Rook && chessBoard[7][0].color == 'w' && !chessBoard[7][0].hasMoved) {
                possibleMoves.add(new Position(row, col - 2, Move.CASTLING));
            }
        }
    }

    /**
     * Checks if the King can castle on the kingside (short castle).
     * Requirements: King and Rook haven't moved, spaces between are empty,
     * King isn't in check, and King doesn't pass through check.
     *
     * @param chessBoardData The current state of the chess board
     * @param possibleMoves  List to add the castling move to if valid
     */
    private void canCastleKingSide(ChessBoardData chessBoardData, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        // Check if king can castle: hasn't moved, not in check, path is clear, and doesn't move through check
        if (!hasMoved && !isUnderCheck(chessBoard) && CheckSquares.squareEmpty(chessBoard, row, col + 1) && CheckSquares.squareEmpty(chessBoard, row, col + 2) && CheckSquares.moveNotCheck(chessBoardData, row, col + 1, color, this) && CheckSquares.moveNotCheck(chessBoardData, row, col + 2, color, this)) {

            // Check for black king castling with the rook at h8
            if (color == 'b' && !CheckSquares.squareEmpty(chessBoard, 0, 7) && chessBoard[0][7] instanceof Rook && chessBoard[0][7].color == 'b' && !chessBoard[0][7].hasMoved) {
                possibleMoves.add(new Position(row, col + 2, Move.CASTLING));
            }
            // Check for white king castling with the rook at h1
            else if (color == 'w' && !CheckSquares.squareEmpty(chessBoard, 7, 7) && chessBoard[7][7] instanceof Rook && chessBoard[7][7].color == 'w' && !chessBoard[7][7].hasMoved) {
                possibleMoves.add(new Position(row, col + 2, Move.CASTLING));
            }
        }
    }

    /**
     * Checks if the King can move to an empty square.
     *
     * @param chessBoardData The current state of the chess board
     * @param row            The target row
     * @param col            The target column
     * @param possibleMoves  List to add the move to if valid
     */
    private void canMoveToEmptySquare(ChessBoardData chessBoardData, int row, int col, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        // Check if the square is on the board, empty, and moving there doesn't put the king in check
        if (CheckSquares.squareInBoardAndEmpty(chessBoard, row, col) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, this)) {
            possibleMoves.add(new Position(row, col));
        }
    }

    /**
     * Checks if the King can move to a square occupied by an opponent's piece (capture).
     *
     * @param chessBoardData The current state of the chess board
     * @param row            The target row
     * @param col            The target column
     * @param possibleMoves  List to add the move to if valid
     */
    private void canMoveOnPiece(ChessBoardData chessBoardData, int row, int col, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        // Check if the square is on the board, contains an opponent's piece, and moving there doesn't put the king in check
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col, color) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, this)) {
            possibleMoves.add(new Position(row, col, Move.TAKING));
        }
    }

    /**
     * Determines if the King is currently in check.
     * Checks all possible ways the king could be attacked.
     *
     * @param chessBoard The current state of the chess board
     * @return true if the king is in check, false otherwise
     */
    public boolean isUnderCheck(Piece[][] chessBoard) {
        return canKnightAttack(chessBoard) || canDiagonalAttack(chessBoard) || canStraightAttack(chessBoard) || canPawnAttack(chessBoard);
    }

    /**
     * Checks if the King is under attack by enemy pawns.
     *
     * @param chessBoard The current state of the chess board
     * @return true if the king is threatened by a pawn, false otherwise
     */
    private boolean canPawnAttack(Piece[][] chessBoard) {
        // Pawns attack in different directions based on their color
        int increment = (color == 'b') ? 1 : -1;

        // Check the two diagonals where enemy pawns could attack from
        return (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + increment, col - 1, color) && chessBoard[row + increment][col - 1] instanceof Pawn) || (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + increment, col + 1, color) && chessBoard[row + increment][col + 1] instanceof Pawn);
    }

    /**
     * Checks if the King is under attack by enemy knights.
     *
     * @param chessBoard The current state of the chess board
     * @return true if the king is threatened by a knight, false otherwise
     */
    private boolean canKnightAttack(Piece[][] chessBoard) {
        // Check all eight possible knight move positions
        return (CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col - 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 2, col + 1, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row + 1, col + 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col - 2, color) || CheckSquares.isOppositeColorKnight(chessBoard, row - 1, col + 2, color));
    }

    /**
     * Checks if the King is under attack along diagonal lines (by bishops or queens).
     *
     * @param chessBoard The current state of the chess board
     * @return true if the king is threatened diagonally, false otherwise
     */
    private boolean canDiagonalAttack(Piece[][] chessBoard) {
        // Check all four diagonal directions
        return canDirectionAttack(1, 1, chessBoard) ||    // Down-right
                canDirectionAttack(-1, -1, chessBoard) ||  // Up-left
                canDirectionAttack(-1, 1, chessBoard) ||   // Up-right
                canDirectionAttack(1, -1, chessBoard);     // Down-left
    }

    /**
     * Checks if the King is under attack along straight lines (by rooks or queens).
     *
     * @param chessBoard The current state of the chess board
     * @return true if the king is threatened horizontally or vertically, false otherwise
     */
    private boolean canStraightAttack(Piece[][] chessBoard) {
        // Check all four straight directions
        return canDirectionAttack(1, 0, chessBoard) ||   // Down
                canDirectionAttack(-1, 0, chessBoard) ||  // Up
                canDirectionAttack(0, 1, chessBoard) ||   // Right
                canDirectionAttack(0, -1, chessBoard);    // Left
    }

    /**
     * Helper method to check if the King is under attack from a specific direction.
     * Traces a line from the king outward and checks for enemy pieces that could attack.
     *
     * @param rowIncrement The row direction (-1, 0, or 1)
     * @param colIncrement The column direction (-1, 0, or 1)
     * @param chessBoard   The current state of the chess board
     * @return true if the king is threatened from this direction, false otherwise
     */
    private boolean canDirectionAttack(int rowIncrement, int colIncrement, Piece[][] chessBoard) {
        // Check up to 7 squares in the specified direction
        for (int i = 1; i < 8; i++) {
            int targetRow = row + i * rowIncrement;
            int targetCol = col + i * colIncrement;

            // Check if target square is on the board
            if (CheckSquares.isWithInBoard(targetRow, targetCol)) {
                // If square is empty, continue checking further in this direction
                if (CheckSquares.squareEmpty(chessBoard, targetRow, targetCol)) {
                    continue;
                }

                // If square has opponent's piece
                if (CheckSquares.squareOppositeColor(chessBoard, targetRow, targetCol, color)) {
                    Piece piece = chessBoard[targetRow][targetCol];

                    // Check if it's a straight line attack (horizontal or vertical)
                    if ((rowIncrement == 0 || colIncrement == 0)) {
                        // Rooks and Queens can attack straight
                        if ((piece instanceof Rook || piece instanceof Queen)) {
                            return true;
                        }
                    }
                    // Check if it's a diagonal attack
                    else {
                        // Bishops and Queens can attack diagonally
                        if (piece instanceof Bishop || piece instanceof Queen) {
                            return true;
                        }
                    }
                    return false;
                } else {
                    // If square has friendly piece, it blocks any attack from this direction
                    return false;
                }
            }
            // If we've gone off the board, no attack is possible from this direction
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * Executes a castling move by also moving the rook to its appropriate position.
     * This is called after the king's position has already been updated.
     *
     * @param chessBoard The current state of the chess board
     * @param col        The column the king moved to (indicates castling side)
     */
    public void castle(Piece[][] chessBoard, int col) {
        // Determine if it's kingside or queenside castling based on king's movement direction
        if (this.col < col) {
            // Kingside castling (king moves right)
            Piece rook;
            if (color == 'b') {
                // Black kingside rook at h8
                rook = chessBoard[0][7];
                chessBoard[0][7] = null;
            } else {
                // White kingside rook at h1
                rook = chessBoard[7][7];
                chessBoard[7][7] = null;
            }
            // Move rook to the square the king crossed over
            rook.setCol(5);
            chessBoard[rook.row][rook.col] = rook;
        } else {
            // Queenside castling (king moves left)
            Piece rook;
            if (color == 'b') {
                // Black queenside rook at a8
                rook = chessBoard[0][0];
                chessBoard[0][0] = null;
            } else {
                // White queenside rook at a1
                rook = chessBoard[7][0];
                chessBoard[7][0] = null;
            }
            // Move rook to the square the king crossed over
            rook.setCol(3);
            chessBoard[rook.row][rook.col] = rook;
        }
    }
}