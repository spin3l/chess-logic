package main.java.es.agata.twochess.board.pieces;

import main.java.es.agata.twochess.board.squares.Square;
import main.java.es.agata.twochess.board.squares.Squares;
import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PieceSet implements Iterable<PieceInfo> {

    private static final PieceSet EMPTY = new PieceSet(
            new ArrayList<>(),
            new ArrayList<>()
    );

    private final List<PieceInfo> pawns;
    private final List<PieceInfo> pieces;

    private PieceSet(
            List<PieceInfo> pawns,
            List<PieceInfo> pieces
    ) {
        this.pawns  = pawns;
        this.pieces = pieces;
    }

    public static PieceSet of(
            Squares squares,
            Player player
    ) {
        List<PieceInfo> pawns  = new ArrayList<>();
        List<PieceInfo> pieces = new ArrayList<>();

        for (Square square : squares) {
            byte value = square.getValue();

            if (!Piece.from(value).equals(Piece.NONE) && Player.from(value).equals(player)) {
                List<PieceInfo> set = Piece.from(value).equals(Piece.PAWN)
                                      ? pawns
                                      : pieces;
                set.add(new PieceInfo(
                        Piece.from(square.getValue()),
                        square.getCoordinate()
                ));
            }
        }

        return new PieceSet(
                pawns,
                pieces
        );
    }

    public static PieceSet empty() {
        return PieceSet.EMPTY;
    }

    public Coordinate getKing() {
        return this.getPieces(Piece.KING).getFirst().getCoordinate();
    }

    public List<PieceInfo> getPieces(Piece piece) {
        if (piece.equals(Piece.PAWN)) {
            return pawns;
        }
        return pieces.stream().filter((pieceInfo) -> pieceInfo.is(piece)).toList();
    }

    public boolean hasAt(Coordinate at) {
        return pawns.contains(at) || pieces.contains(at);
    }

    @Override
    public String toString() {
        return pawns.toString() + pieces.toString();
    }

    @Override
    public Iterator<PieceInfo> iterator() {
        return Stream.concat(
                pawns.stream(),
                pieces.stream()
        ).iterator();
    }

    public void update(
            Square from,
            Square to
    ) {
        for (PieceInfo pieceInfo : this) {
            if (pieceInfo.getCoordinate().equals(from.getCoordinate())) {
                pieceInfo.setCoordinate(to.getCoordinate());
            }
        }
    }

    public void remove(Square square) {
        this.pieces.remove(square.getCoordinate());
        this.pawns.remove(square.getCoordinate());
    }

    public void addPiece(PieceInfo pieceInfo) {
        List<PieceInfo> set = pieceInfo.getPiece().equals(Piece.PAWN)
                              ? pawns
                              : pieces;
        set.add(pieceInfo);
    }
}
