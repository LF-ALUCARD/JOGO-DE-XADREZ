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

    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.WHITE), new Position(0, 4));
        board.placePiece(new King(board, Color.BLACK), new Position(7, 4));
    }
}
