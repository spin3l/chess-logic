package main.java.es.agata.twochess.board.state;


import main.java.es.agata.twochess.board.pieces.PieceInfo;
import main.java.es.agata.twochess.board.squares.Coordinates;
import main.java.es.agata.twochess.board.moves.Move;
import main.java.es.agata.twochess.board.moves.MoveDirection;

public record Coordinate(
        int file,
        int rank
) implements Comparable<Coordinate> {

    public Coordinate {
        if (isOutOfBounds(file) || isOutOfBounds(rank)) {
            file = -1;
            rank = -1;
        }
    }

    public static final Coordinate OUT = new Coordinate(
            -1,
            -1
    );

    public static Coordinate from(String str) {
        if (str.length() != 2) {
            return Coordinate.OUT;
        }

        return new Coordinate(
                str.charAt(0) - 'a',
                str.charAt(1) - '1'
        );
    }

    public byte toByte() {
        return (byte) ((rank << 4) | file);
    }

    private static boolean isOutOfBounds(int value) {
        return value < 0 || 7 < value;
    }

    public boolean isWithin() {
        return !this.isOut();
    }

    public boolean isOut() {
        return this.equals(Coordinate.OUT);
    }

    public Coordinates applyMoves(MoveDirection moves) {
        return Coordinates.of(moves.moves().stream().map(this::applyMove).toList());
    }

    public Coordinate applyMove(Move move) {
        return new Coordinate(
                this.file + move.dX(),
                this.rank + move.dY()
        );
    }

    @Override
    public String toString() {
        if (this.isOut()) {
            return "-";
        }
        return String.format(
                "%c%c",
                this.file + 'a',
                this.rank + '1'
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate coordinate) {
            return coordinate.rank == rank && coordinate.file == file;
        }
        if (obj instanceof PieceInfo pieceInfo) {
            return equals(pieceInfo.getCoordinate());
        }
        return false;
    }

    @Override
    public int compareTo(Coordinate o) {
        return Integer.compare(
                toByte(),
                o.toByte()
        );
    }
}
