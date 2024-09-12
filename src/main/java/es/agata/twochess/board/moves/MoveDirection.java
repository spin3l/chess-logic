package main.java.es.agata.twochess.board.moves;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveDirection implements Iterable<Move> {

    private final List<Move> moves;

    public MoveDirection(List<Move> moves) {
        this.moves = moves;
    }

    public MoveDirection(
            int stepX,
            int stepY,
            int steps
    ) {
        List<Move> moves = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            moves.add(new Move(
                    stepX * step,
                    stepY * step
            ));
        }

        this.moves = moves;
    }

    public MoveDirection mirror() {
        return new MoveDirection(this.moves.stream().map(Move::mirror).toList());
    }

    public List<Move> moves() {
        return moves;
    }

    @Override
    public Iterator<Move> iterator() {
        return this.moves.iterator();
    }
}
