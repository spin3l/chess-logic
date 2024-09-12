package main.java.es.agata.twochess.board.squares;

import main.java.es.agata.twochess.board.pieces.Piece;
import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Squares implements Iterable<Square> {

    public static final int SQUARES_DIMENSIONS = 128;

    private final List<Square> squares;

    public Squares() {
        this.squares = new ArrayList<>(SQUARES_DIMENSIONS);

        for (int i = 0; i < SQUARES_DIMENSIONS; i++) {
            int file = i % 16;
            int rank = i / 16;

            Coordinate coordinate = new Coordinate(
                    file,
                    rank
            );

            this.squares.add(new Square(
                    coordinate,
                    Piece.NONE.value
            ));
        }
    }

    public Optional<Square> get(Coordinate coordinate) {
        if (coordinate.isOut()) {
            return Optional.empty();
        }
        return Optional.of(this.squares.get(coordinate.toByte()));
    }

    public boolean set(
            Coordinate coordinate,
            byte newValue
    ) {
        Optional<Square> square = this.get(coordinate);
        if (square.isEmpty()) {
            return false;
        }
        square.get().setValue(newValue);
        return true;
    }

    @Override
    public Iterator<Square> iterator() {
        return new SquaresIterator(this.squares);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int           num    = 0;
        for (Square square : this) {
            result.append(square);
            if (++num % 8 == 0) {
                result.append('\n');
            }
        }
        return result.toString();
    }
}
