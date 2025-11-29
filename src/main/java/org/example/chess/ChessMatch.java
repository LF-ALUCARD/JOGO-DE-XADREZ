package org.example.chess;

import org.example.boardgame.Board;
import org.example.boardgame.Piece;
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

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
     Position source = sourcePosition.toPosition();
     Position target = targetPosition.toPosition();

     validateSourcePosition(source);
     validateTargetPosition(source, target);
     Piece capturedPiece = makeMove(source, target);

     return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition (Position source){
        if (!board.thereIsPiece(source)){
            throw new ChessExceptions("Erro: não existe peça nessa posição");
        }
        if(!board.piece(source).isThereAnyPossibleMove()) {
            throw new ChessExceptions("Erro: não existe movimentos possiveis para essa peça");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessExceptions("Erro: Essa peça não pode mover para essa posição");
        }
    }

    private Piece makeMove(Position sourcePosition, Position targetPosition){
        Piece p = board.removePiece(sourcePosition);
        Piece capturedPiece = board.removePiece(targetPosition);
        board.placePiece(p, targetPosition);

        return capturedPiece;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
