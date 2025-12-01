package org.example.chess;

import org.example.boardgame.Board;
import org.example.boardgame.Piece;
import org.example.boardgame.Position;
import org.example.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean xeque;
    private boolean xequeMate;
    private ChessPiece enPassantVulnerable;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch(){
        this.board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public boolean getXequeMate(){
        return this.xequeMate;
    }

    public boolean getXeque(){
        return this.xeque;
    }

    public List<Piece> getPiecesOnTheBoard(){
        return this.piecesOnTheBoard;
    }

    public List<Piece> getCapturedPieces(){
        return this.capturedPieces;
    }

    public ChessPiece getEnPassantVulnerable(){
        return enPassantVulnerable;
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

    public int getTurn(){
        return this.turn;
    }

    public Color getCurrentPlayer(){
        return this.currentPlayer;
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
     Position source = sourcePosition.toPosition();
     Position target = targetPosition.toPosition();

     validateSourcePosition(source);
     validateTargetPosition(source, target);
     Piece capturedPiece = makeMove(source, target);

     if (testCheck(currentPlayer)){
         undoMove(source, target, capturedPiece);
         throw new ChessExceptions("Você não pode se colocar em xeque");
     }

     ChessPiece movedPiece = (ChessPiece) board.piece(target);

     xeque = testCheck(opponent(currentPlayer));

     if (testCheckMate(opponent(currentPlayer))){
         xequeMate= true;
     }
     else{
         nextTurn();
     }

     // #specialmove en passant
     if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2) || (target.getRow() == source.getRow() + 2)){
         enPassantVulnerable = movedPiece;
     }
     else{
         enPassantVulnerable = null;
     }

     return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition (Position source){
        if (!board.thereIsPiece(source)){
            throw new ChessExceptions("Erro: não existe peça nessa posição");
        }

        if (currentPlayer != ((ChessPiece)board.piece(source)).getColor()){
            throw new ChessExceptions("A peça escolhida não é sua!!");
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
        ChessPiece p = (ChessPiece) board.removePiece(sourcePosition);
        p.increaseMoveCount();

        Piece capturedPiece = board.removePiece(targetPosition);
        board.placePiece(p, targetPosition);

        if (capturedPiece != null){
            piecesOnTheBoard.remove((capturedPiece));
            capturedPieces.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && targetPosition.getColumn() == sourcePosition.getColumn() + 2){
            Position sourceT = new Position(sourcePosition.getRow(), sourcePosition.getColumn() + 3);
            Position targetT = new Position(sourcePosition.getRow(), sourcePosition.getColumn() + 1);

            ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && targetPosition.getColumn() == sourcePosition.getColumn() - 2){
            Position sourceT = new Position(sourcePosition.getRow(), sourcePosition.getColumn() - 4);
            Position targetT = new Position(sourcePosition.getRow(), sourcePosition.getColumn() - 1);

            ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof Pawn){
            if (sourcePosition.getColumn() != targetPosition.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                   pawnPosition = new Position(targetPosition.getRow() + 1, targetPosition.getColumn());
                }
                else{
                   pawnPosition = new Position(targetPosition.getRow() - 1, targetPosition.getColumn());
                }

                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();

        board.placePiece(p, source);

        if (capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(sourceT.getRow(), sourceT.getColumn() + 1);

            ChessPiece rook = (ChessPiece)board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece)board.removePiece(target);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof Pawn){
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;

                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                }
                else{
                    pawnPosition = new Position(4, target.getColumn());
                }

                board.placePiece(pawn, pawnPosition);
            }
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();
        for (Piece x : list){
            if (x instanceof King){
                return (ChessPiece)x;
            }
        }
        throw new IllegalStateException("Não foi encontrado o rei da cor: " + color);
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> oppoentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).toList();

        for(Piece x : oppoentPieces){
            boolean[][] mat = x.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }

        return false;
    }

    private boolean testCheckMate(Color color){
        if (!testCheck(color)){
            return false;
        }

        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();

        for(Piece x : list) {
            boolean[][] mat = x.possibleMoves();
            for (int i = 0; i < board.getRow(); i++) {
                for (int j = 0; j < board.getColumn(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)x).getChessPosition().toPosition();
                        Position target = new Position(i, j);

                        Piece capturedPiece = makeMove(source, target);
                        boolean testXeque = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testXeque){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));

        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        /* -------------------------------------------------------------*/

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));

        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
