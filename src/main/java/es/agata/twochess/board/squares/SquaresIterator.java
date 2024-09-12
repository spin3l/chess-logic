package main.java.es.agata.twochess.board.squares;

import java.util.Iterator;
import java.util.List;

public class SquaresIterator implements Iterator<Square> {

    private final List<Square> squares;
    private       int          currentIndex;

    public SquaresIterator(List<Square> squares) {
        this.squares      = squares;
        this.currentIndex = -1;
    }

    private static int nextIndex(int idx) {
        int next = idx + 1;

        boolean shouldSkip = (next % 16) > 7;

        int row = Math.floorDiv(
                next,
                16
        );

        return shouldSkip
               ? (row + 1) * 16
               : next;
    }

    @Override
    public boolean hasNext() {
        return nextIndex(this.currentIndex) < 128;
    }

    @Override
    public Square next() {
        int    nextIndex = nextIndex(currentIndex);
        Square _return   = this.squares.get(nextIndex);
        this.currentIndex = nextIndex;
        return _return;
    }
}
