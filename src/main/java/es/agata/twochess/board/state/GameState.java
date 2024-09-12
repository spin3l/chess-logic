package main.java.es.agata.twochess.board.state;

import main.java.es.agata.twochess.board.pieces.Piece;
import main.java.es.agata.twochess.board.pieces.PieceInfo;
import main.java.es.agata.twochess.board.pieces.PieceSet;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.squares.Square;
import main.java.es.agata.twochess.board.squares.Squares;

import java.util.Optional;

public class GameState {

    private final Squares        squares;
    private final Player         currentPlayer;
    private final CastlingRights whiteCastling;
    private final CastlingRights blackCastling;
    private final Coordinate     enPassantTarget;
    private final MoveCounter    halfMove;
    private final MoveCounter    fullMove;

    private final PieceSet   whitePieceSet;
    private final PieceSet   blackPieceSet;
    private       BoardState boardState;

    public GameState(
            Squares squares,
            Player currentPlayer,
            CastlingRights whiteCastling,
            CastlingRights blackCastling,
            Coordinate enPassantTarget,
            MoveCounter halfMove,
            MoveCounter fullMove
    ) {
        this.squares         = squares;
        this.currentPlayer   = currentPlayer;
        this.whiteCastling   = whiteCastling;
        this.blackCastling   = blackCastling;
        this.enPassantTarget = enPassantTarget;
        this.halfMove        = halfMove;
        this.fullMove        = fullMove;

        this.whitePieceSet = PieceSet.of(
                squares,
                Player.WHITE
        );
        this.blackPieceSet = PieceSet.of(
                squares,
                Player.BLACK
        );

        this.boardState = BoardState.OPEN;
    }


    public PieceSet getPieceSet(Player player) {
        if (player.equals(Player.NONE)) {
            return PieceSet.empty();
        }
        return player.isWhite()
               ? whitePieceSet
               : blackPieceSet;
    }

    public boolean setSquare(
            Coordinate coordinate,
            Byte value
    ) {
        Optional<Square> square = this.squares.get(coordinate);
        if (square.isEmpty()) {
            return false;
        }
        square.get().setValue(value);
        getPieceSet(Player.from(value)).addPiece(new PieceInfo(
                Piece.from(value),
                coordinate
        ));
        return true;
    }

    public Optional<Byte> getSquare(Coordinate coordinate) {
        return this.squares.get(coordinate).map(Square::getValue);
    }

    public Optional<Byte> move(
            Coordinate fromCoordinate,
            Coordinate toCoordinate
    ) {
        Optional<Square> from = this.squares.get(fromCoordinate);
        Optional<Square> to   = this.squares.get(toCoordinate);

        if (from.isEmpty() || to.isEmpty()) {
            return Optional.empty();
        }

        Square fromSquare = from.get();
        Square toSquare   = to.get();

        Player fromPlayer = Player.from(fromSquare.getValue());
        Player toPlayer   = Player.from(toSquare.getValue());

        this.getPieceSet(fromPlayer).update(
                fromSquare,
                toSquare
        );
        this.getPieceSet(toPlayer).remove(toSquare);


        byte toValue = toSquare.getValue();

        toSquare.setValue(fromSquare.getValue());
        fromSquare.setValue(Piece.NONE.value);

        return Optional.of(toValue);
    }

}
