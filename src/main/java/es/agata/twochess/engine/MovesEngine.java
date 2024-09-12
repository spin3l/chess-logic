package main.java.es.agata.twochess.engine;

import main.java.es.agata.twochess.board.squares.Coordinates;
import main.java.es.agata.twochess.board.moves.Move;
import main.java.es.agata.twochess.board.moves.MoveLegality;
import main.java.es.agata.twochess.board.representations.GameStateRepresentation;
import main.java.es.agata.twochess.board.representations.GameStateRepresentationBuilder;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.Optional;

public interface MovesEngine {


    MoveLegality legality(
            Coordinate coordinate,
            Move move,
            Player checkPlayer,
            boolean checkKingSafety
    );

    Optional<Byte> move(
            Coordinate from,
            Coordinate to
    );

    Optional<Coordinates> legalMoves(
            Coordinate coordinate
    );

    Optional<Coordinates> legalMoves(
            Coordinate coordinate,
            boolean checkKingSafety
    );

    GameStateRepresentation getRepresentation(GameStateRepresentationBuilder representationBuilder);


}
