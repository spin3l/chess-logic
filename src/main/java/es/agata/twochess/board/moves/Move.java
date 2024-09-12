package main.java.es.agata.twochess.board.moves;

public record Move(
        int dX,
        int dY
) {
    public Move mirror() {
        return new Move(
                dX,
                -dY
        );
    }

    public Move reverse() {
        return new Move(
                -dX,
                -dY
        );
    }
}
