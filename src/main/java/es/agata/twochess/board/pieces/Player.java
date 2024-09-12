package main.java.es.agata.twochess.board.pieces;

import java.util.Optional;

public enum Player {

    WHITE((byte) 0b10000),
    BLACK((byte) 0b11000),
    NONE((byte) 0b00000);

    public final byte value;

    private static final byte PLAYER_MASK = 0b11000;

    Player(byte value) {
        this.value = value;
    }

    public static Optional<Player> from(char c) {
        return switch (Character.toLowerCase(c)) {
            case 'w' -> Optional.of(WHITE);
            case 'b' -> Optional.of(BLACK);
            default -> Optional.empty();
        };
    }

    public static Player from(byte value) {
        return switch(value & PLAYER_MASK) {
            case 0b10000 -> WHITE;
            case 0b11000 -> BLACK;
            default -> NONE;
        };
    }

    public boolean isWhite() {
        return this.equals(WHITE);
    }

    public Player opponent() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            default -> NONE;
        };
    }
}
