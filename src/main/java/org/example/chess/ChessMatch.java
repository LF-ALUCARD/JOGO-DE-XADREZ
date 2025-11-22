package org.example.chess;

import org.example.boardgame.Board;
import org.example.boardgame.Position;
import org.example.chess.pieces.King;
import org.example.chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch(){
        this.board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];

        for (int i = 0; i < board.getColumn(); i++){

            for (int j = 0; j < board.getRow(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return mat;
    }

    private void praceNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        praceNewPiece('b', 6, new Rook(board, Color.WHITE));
        praceNewPiece('e', 8, new King(board, Color.WHITE));
        praceNewPiece('e', 1 ,new King(board, Color.BLACK));
    }
}
