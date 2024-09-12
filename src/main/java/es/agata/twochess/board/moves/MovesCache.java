package main.java.es.agata.twochess.board.moves;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MovesCache {

    private final HashMap<Byte, List<MoveDirection>> moves;

    private static MovesCache instance;

    private MovesCache() {
        this.moves = new HashMap<>();
    }

    public Optional<List<MoveDirection>> get(Byte key) {
        if (!this.moves.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(this.moves.get(key));
    }

    public void put(
            Byte key,
            List<MoveDirection> moves
    ) {
        this.moves.put(
                key,
                moves
        );
    }


    public static MovesCache getInstance() {
        if (MovesCache.instance == null) {
            MovesCache.instance = new MovesCache();
        }

        return MovesCache.instance;
    }

    @Override
    public String toString() {
        return moves.toString();
    }
}
