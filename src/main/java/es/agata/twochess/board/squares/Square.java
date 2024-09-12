package main.java.es.agata.twochess.board.squares;


import main.java.es.agata.twochess.board.pieces.Piece;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.state.Coordinate;

public class Square {

    private final Coordinate coordinate;
    private byte value;

    public Square(Coordinate coordinate, byte value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }

    public Coordinate getCoordinate() { return this.coordinate; }

    public String toString() {
        Player player = Player.from(this.value);
        Piece  piece;

        piece = Piece.from(this.value);

        return piece.representation(player).toString();
    }
}
