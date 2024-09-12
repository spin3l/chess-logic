package main.java.es.agata.twochess.board.moves;


import main.java.es.agata.twochess.board.pieces.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MovesCache implements Cache<Byte, List<MoveDirection>> {

    private final HashMap<Byte, List<MoveDirection>> moves;

    private static MovesCache instance;

    private MovesCache() {
        this.moves = new HashMap<>();
    }

    @Override
    public Optional<List<MoveDirection>> get(Byte key) {
        if (!this.moves.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(this.moves.get(key));
    }

    @Override
    public void put(
            Byte key,
            List<MoveDirection> moves
    ) {
        this.moves.put(
                key,
                moves
        );
    }

    private MovesCache initialise() {
        for (Moves m : Moves.values()) {
            this.put(
                    m.piece().value(Player.WHITE),
                    m.moves()
            );
            this.put(
                    m.piece().value(Player.BLACK),
                    m.mirroredMoves()
            );
        }
        return this;
    }

    public static Cache<Byte, List<MoveDirection>> getInstance() {
        if (MovesCache.instance == null) {
            MovesCache.instance = new MovesCache().initialise();
        }

        return MovesCache.instance;
    }

    @Override
    public String toString() {
        return moves.toString();
    }
}
