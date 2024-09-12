package main.java.es.agata.twochess.board.pieces;

import main.java.es.agata.twochess.board.state.Coordinate;

public class PieceInfo {

    private final Piece piece;

    private Coordinate coordinate;

    public PieceInfo(
            Piece piece,
            Coordinate coordinate
    ) {
        this.piece      = piece;
        this.coordinate = coordinate;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Piece.class)) {
            return piece.equals(obj);
        }
        if (obj.getClass().equals(Coordinate.class)) {
            return coordinate.equals(obj);
        }
        return false;
    }

    public boolean is(Piece piece) {
        return this.piece.equals(piece);
    }
}
