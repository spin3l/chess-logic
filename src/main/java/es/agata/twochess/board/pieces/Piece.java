package main.java.es.agata.twochess.board.pieces;

import main.java.es.agata.twochess.board.squares.Square;

import java.util.function.Function;

public enum Piece {

    NONE((byte) 0b000),

    PAWN((byte) 0b001),

    BISHOP((byte) 0b010),

    KNIGHT((byte) 0b011),

    ROOK((byte) 0b100),

    QUEEN((byte) 0b101),

    KING((byte) 0b110);

    private static final byte PIECE_MASK = 0b111;

    public final byte value;

    Piece(byte value) {
        this.value = value;
    }

    public byte value(Player player) {
        return (byte) (value | player.value);
    }

    public static Piece from(byte value) {
        return switch (Piece.PIECE_MASK & value) {
            case 0b001 -> PAWN;
            case 0b010 -> BISHOP;
            case 0b011 -> KNIGHT;
            case 0b100 -> ROOK;
            case 0b101 -> QUEEN;
            case 0b110 -> KING;
            default -> NONE;
        };
    }

    public static Piece from(char c) {
        return switch (Character.toLowerCase(c)) {
            case 'p' -> PAWN;
            case 'b' -> BISHOP;
            case 'n' -> KNIGHT;
            case 'r' -> ROOK;
            case 'q' -> QUEEN;
            case 'k' -> KING;
            default -> NONE;
        };
    }

    public static boolean at(Square square) {
        return !Piece.from(square.getValue()).equals(Piece.NONE);
    }

    public static Piece from(Square square) {
        return Piece.from(square.getValue());
    }

    public char representation() {
        return switch (this.value) {
            case 0b001 -> 'p';
            case 0b010 -> 'b';
            case 0b011 -> 'n';
            case 0b100 -> 'r';
            case 0b101 -> 'q';
            case 0b110 -> 'k';
            default -> '-';
        };
    }

    public Character representation(Player player) {
        Function<Character, Character> func = player.isWhite()
                                              ? Character::toUpperCase
                                              : Character::toLowerCase;
        return func.apply(representation());
    }
}
