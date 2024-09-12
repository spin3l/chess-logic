package main.java.es.agata.twochess.board.state;

import main.java.es.agata.twochess.board.pieces.Piece;
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

    private final PieceSet whitePieceSet;
    private final PieceSet blackPieceSet;

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
    }

    public PieceSet getPieceSet(Player player) {
        if (player.equals(Player.NONE)) {
            return PieceSet.empty();
        }
        return player.isWhite()
               ? whitePieceSet
               : blackPieceSet;
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

    @Override
    public String toString() {
        return new StringBuilder().append(squares)
                                  .append("\nCurrent player: ")
                                  .append(this.currentPlayer)
                                  .append("\nCastling ")
                                  .append("\n\t- WHITE: ")
                                  .append(this.whiteCastling)
                                  .append("\n\t- BLACK: ")
                                  .append(this.blackCastling)
                                  .append("\nEn passant target: ")
                                  .append(this.enPassantTarget)
                                  .append("\nHalfmove clock: ")
                                  .append(this.halfMove)
                                  .append("\nFullmove counter: ")
                                  .append(this.fullMove)
                                  .toString();
    }
}
