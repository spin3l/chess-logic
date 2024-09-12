package main.java.es.agata.twochess.board.representations;

import main.java.es.agata.twochess.board.pieces.Piece;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.squares.Squares;
import main.java.es.agata.twochess.board.state.CastlingRights;
import main.java.es.agata.twochess.board.state.GameState;
import main.java.es.agata.twochess.board.state.MoveCounter;
import main.java.es.agata.twochess.board.state.Coordinate;
import main.java.es.agata.twochess.utils.Tuple;

import java.util.Optional;

public class FenRepresentation implements GameStateRepresentation {


    private final GameState gameState;
    private final String    fen;

    private FenRepresentation(
            GameState gameState,
            String fen
    ) {
        this.gameState = gameState;
        this.fen       = fen;
    }

    @Override
    public String toString() {
        return this.fen;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }


    public static class Builder implements GameStateRepresentationBuilder {

        private static final String INITIAL = "rnbqkbnr/pppppppp/8/8/8/3q4/PPP1PPPP/RNBQKBNR w " +
                                              "KQkq a2 0 1";

        private static Squares getSquares(String str) {
            Squares squares = new Squares();

            int rank = 7, file = 0;

            for (char c : str.toCharArray()) {
                if (Character.isLetter(c)) {
                    Piece piece = Piece.from(c);
                    Player player = Character.isUpperCase(c)
                                    ? Player.WHITE
                                    : Player.BLACK;
                    squares.set(
                            new Coordinate(
                                    file,
                                    rank
                            ),
                            (byte) (piece.value | player.value)
                    );
                    file += 1;
                    continue;
                }

                if (c == '/') {
                    rank -= 1;
                    file = 0;
                    continue;
                }

                try {
                    file += Integer.parseInt(Character.toString(c));
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Unknown character in FEN String: " + c);
                }
            }

            return squares;
        }

        private static Optional<Tuple<CastlingRights>> getRights(String str) {
            if (str.equals("-")) {
                return Optional.empty();
            }

            CastlingRights white = new CastlingRights(
                    str.contains("K"),
                    str.contains("Q")
            );
            CastlingRights black = new CastlingRights(
                    str.contains("k"),
                    str.contains("q")
            );

            return Optional.of(new Tuple<>(
                    white,
                    black
            ));
        }

        @Override
        public GameStateRepresentation initial() {
            String[] splitFen = Builder.INITIAL.split(" ");
            assert splitFen.length == 6;

            Squares               squares       = Builder.getSquares(splitFen[0]);
            Player                currentPlayer = Player.from(splitFen[1].charAt(0)).orElseThrow();
            Tuple<CastlingRights> rights     = Builder.getRights(splitFen[2]).orElseThrow();
            Coordinate            coordinate = Coordinate.from(splitFen[3]);
            MoveCounter           halfMove   = new MoveCounter(Integer.parseInt(splitFen[4]));
            MoveCounter           fullMove      = new MoveCounter(Integer.parseInt(splitFen[5]));

            return new FenRepresentation(
                    new GameState(
                            squares,
                            currentPlayer,
                            rights.white(),
                            rights.black(),
                            coordinate,
                            halfMove,
                            fullMove
                    ),
                    INITIAL
            );
        }


        @Override
        public GameStateRepresentation build(GameState gameState) {
            return null;
        }

        @Override
        public GameState build(String representation) {
            return null;
        }
    }

}
