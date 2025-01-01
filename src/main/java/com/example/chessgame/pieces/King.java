package com.example.chessgame.pieces;

import com.example.chessgame.data.ChessBoardData;
import com.example.chessgame.data.Move;
import com.example.chessgame.data.Position;
import com.example.chessgame.helper.CheckSquares;

import java.util.ArrayList;

public class King extends Piece {
    public King(char color, int row, int col) {
        super(color, row, col);
        letter = 'K';
    }

    @Override
    public ArrayList<Position> getPossibleMoves(ChessBoardData chessBoardData) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        if (chessBoardData.getTurn() == color) {

            canMoveToEmptySquare(chessBoardData, row + 1, col + 1, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row + 1, col, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row + 1, col - 1, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row, col + 1, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row, col - 1, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row - 1, col + 1, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row - 1, col, possibleMoves);
            canMoveToEmptySquare(chessBoardData, row - 1, col - 1, possibleMoves);

            canMoveOnPiece(chessBoardData, row + 1, col + 1, possibleMoves);
            canMoveOnPiece(chessBoardData, row + 1, col, possibleMoves);
            canMoveOnPiece(chessBoardData, row + 1, col - 1, possibleMoves);
            canMoveOnPiece(chessBoardData, row, col + 1, possibleMoves);
            canMoveOnPiece(chessBoardData, row, col - 1, possibleMoves);
            canMoveOnPiece(chessBoardData, row - 1, col + 1, possibleMoves);
            canMoveOnPiece(chessBoardData, row - 1, col, possibleMoves);
            canMoveOnPiece(chessBoardData, row - 1, col - 1, possibleMoves);

            canCastleKingSide(chessBoardData, possibleMoves);
            canCastleQueenSide(chessBoardData, possibleMoves);

        }
        return possibleMoves;
    }

    private void canCastleQueenSide(ChessBoardData chessBoardData, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (!hasMoved && !isUnderCheck(chessBoard) && CheckSquares.squareEmpty(chessBoard, row, col - 1) && CheckSquares.squareEmpty(chessBoard, row, col - 2) && CheckSquares.moveNotCheck(chessBoardData, row, col + 1, color, this) && CheckSquares.moveNotCheck(chessBoardData, row, col + 2, color, this)) {
            if (color == 'b' && !CheckSquares.squareEmpty(chessBoard, 0, 0) && chessBoard[0][0] instanceof Rook && chessBoard[0][0].color == 'b' && !chessBoard[0][0].hasMoved) {
                possibleMoves.add(new Position(row, col - 2, Move.CASTLING));
            } else if (color == 'w' && !CheckSquares.squareEmpty(chessBoard, 7, 0) && chessBoard[7][0] instanceof Rook && chessBoard[7][0].color == 'w' && !chessBoard[7][0].hasMoved) {
                possibleMoves.add(new Position(row, col - 2, Move.CASTLING));
            }
        }
    }

    private void canCastleKingSide(ChessBoardData chessBoardData, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (!hasMoved && !isUnderCheck(chessBoard) && CheckSquares.squareEmpty(chessBoard, row, col + 1) && CheckSquares.squareEmpty(chessBoard, row, col + 2) && CheckSquares.moveNotCheck(chessBoardData, row, col + 1, color, this) && CheckSquares.moveNotCheck(chessBoardData, row, col + 2, color, this)) {
            if (color == 'b' && !CheckSquares.squareEmpty(chessBoard, 0, 7) && chessBoard[0][7] instanceof Rook && chessBoard[0][7].color == 'b' && !chessBoard[0][7].hasMoved) {
                possibleMoves.add(new Position(row, col + 2, Move.CASTLING));
            } else if (color == 'w' && !CheckSquares.squareEmpty(chessBoard, 7, 7) && chessBoard[7][7] instanceof Rook && chessBoard[7][7].color == 'w' && !chessBoard[7][7].hasMoved) {
                possibleMoves.add(new Position(row, col + 2, Move.CASTLING));
            }
        }
    }

    private void canMoveToEmptySquare(ChessBoardData chessBoardData, int row, int col, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (CheckSquares.squareInBoardAndEmpty(chessBoard, row, col) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, this)) {
            possibleMoves.add(new Position(row, col));
        }

    }

    private void canMoveOnPiece(ChessBoardData chessBoardData, int row, int col, ArrayList<Position> possibleMoves) {
        Piece[][] chessBoard = chessBoardData.getChessBoard();
        if (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row, col, color) && CheckSquares.moveNotCheck(chessBoardData, row, col, color, this)) {
            possibleMoves.add(new Position(row, col, Move.TAKING));
        }
    }

    public boolean isUnderCheck(Piece[][] chessBoard) {
        return canKnightAttack(chessBoard) || canDiagonalAttack(chessBoard) || canStraightAttack(chessBoard) || canPawnAttack(chessBoard);
    }

    private boolean canPawnAttack(Piece[][] chessBoard) {
        int increment = (color == 'b') ? 1 : -1;
        return (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + increment, col - 1, color) && chessBoard[row + increment][col - 1] instanceof Pawn) || (CheckSquares.squareInBoardNotEmptyOppositeColor(chessBoard, row + increment, col + 1, color) && chessBoard[row + increment][col + 1] instanceof Pawn);
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
                }
                if (CheckSquares.squareOppositeColor(chessBoard, row + i * rowIncrement, col + i * colIncrement, color)) {
                    Piece piece = chessBoard[row + i * rowIncrement][col + i * colIncrement];
                    if ((rowIncrement == 0 || colIncrement == 0)) {
                        //means can attack straight
                        if ((piece instanceof Rook || piece instanceof Queen)) {
                            System.out.println(piece);
                            return true;
                        }
                    } else {
                        if (piece instanceof Bishop || piece instanceof Queen) {
                            System.out.println(piece);
                            return true;
                        }
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

    public void castle(Piece[][] chessBoard, int col) {
        //Figure if king side or queen side.
        if (this.col < col) {
            //King Side
            Piece piece;
            if (color == 'b') {
                piece = chessBoard[0][7];
                chessBoard[0][7] = null;
            } else {
                piece = chessBoard[7][7];
                chessBoard[7][7] = null;
            }
            piece.setCol(5);
            chessBoard[piece.row][piece.col] = piece;

        } else {
            //Queen side
            Piece piece;
            if (color == 'b') {
                piece = chessBoard[0][0];
                chessBoard[0][0] = null;
            } else {
                piece = chessBoard[7][0];
                chessBoard[7][0] = null;
            }
            piece.setCol(3);
            chessBoard[piece.row][piece.col] = piece;
        }

    }
}
