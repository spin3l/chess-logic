package main.java.es.agata.twochess.board.moves;

import main.java.es.agata.twochess.board.pieces.Piece;

import java.util.Collections;
import java.util.List;

public enum Moves {

    KING(
            Piece.KING,
            List.of(
                    new MoveDirection(
                            0,
                            1,
                            1
                    ),
                    new MoveDirection(
                            1,
                            1,
                            1
                    ),
                    new MoveDirection(
                            1,
                            0,
                            2
                    ),
                    new MoveDirection(
                            1,
                            -1,
                            1
                    ),
                    new MoveDirection(
                            0,
                            -1,
                            1
                    ),
                    new MoveDirection(
                            -1,
                            -1,
                            1
                    ),
                    new MoveDirection(
                            -1,
                            0,
                            2
                    ),
                    new MoveDirection(
                            -1,
                            1,
                            1
                    )
            )
    ),
    QUEEN(
            Piece.QUEEN,
            List.of(
                    new MoveDirection(
                            0,
                            1,
                            7
                    ),
                    new MoveDirection(
                            1,
                            1,
                            7
                    ),
                    new MoveDirection(
                            1,
                            0,
                            7
                    ),
                    new MoveDirection(
                            1,
                            -1,
                            7
                    ),
                    new MoveDirection(
                            0,
                            -1,
                            7
                    ),
                    new MoveDirection(
                            -1,
                            -1,
                            7
                    ),
                    new MoveDirection(
                            -1,
                            0,
                            7
                    ),
                    new MoveDirection(
                            -1,
                            1,
                            7
                    )
            )
    ),
    ROOK(
            Piece.ROOK,
            List.of(
                    new MoveDirection(
                            -1,
                            0,
                            7
                    ),
                    new MoveDirection(
                            1,
                            0,
                            7
                    ),
                    new MoveDirection(
                            0,
                            1,
                            7
                    ),
                    new MoveDirection(
                            0,
                            -1,
                            7
                    )
            )
    ),
    BISHOP(
            Piece.BISHOP,
            List.of(
                    new MoveDirection(
                            -1,
                            -1,
                            7
                    ),
                    new MoveDirection(
                            1,
                            1,
                            7
                    ),
                    new MoveDirection(
                            -1,
                            1,
                            7
                    ),
                    new MoveDirection(
                            1,
                            -1,
                            7
                    )
            )
    ),
    KNIGHT(
            Piece.KNIGHT,
            List.of(
                    new MoveDirection(
                            1,
                            2,
                            1
                    ),
                    new MoveDirection(
                            2,
                            1,
                            1
                    ),
                    new MoveDirection(
                            2,
                            -1,
                            1
                    ),
                    new MoveDirection(
                            1,
                            -2,
                            1
                    ),
                    new MoveDirection(
                            -1,
                            -2,
                            1
                    ),
                    new MoveDirection(
                            -2,
                            -1,
                            1
                    ),
                    new MoveDirection(
                            -2,
                            1,
                            1
                    ),
                    new MoveDirection(
                            -1,
                            2,
                            1
                    )
            )
    ),
    PAWN(
            Piece.PAWN,
            List.of(
                    new MoveDirection(
                            0,
                            1,
                            2
                    ),
                    new MoveDirection(
                            1,
                            1,
                            1
                    ),
                    new MoveDirection(
                            -1,
                            1,
                            1
                    )
            )
    );


    private final Piece piece;

    private final List<MoveDirection> moves;

    Moves(
            Piece piece,
            List<MoveDirection> moves
    ) {
        this.piece = piece;
        this.moves = moves;
    }

    public Piece piece() {
        return this.piece;
    }

    public List<MoveDirection> moves() {
        return Collections.unmodifiableList(moves);
    }

    public List<MoveDirection> mirroredMoves() {
        return moves.stream().map(MoveDirection::mirror).toList();
    }
}
