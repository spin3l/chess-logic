package main.java.es.agata.twochess.engine;

import main.java.es.agata.twochess.board.Board;
import main.java.es.agata.twochess.board.moves.*;
import main.java.es.agata.twochess.board.pieces.Piece;
import main.java.es.agata.twochess.board.pieces.PieceInfo;
import main.java.es.agata.twochess.board.pieces.PieceSet;
import main.java.es.agata.twochess.board.representations.GameStateRepresentation;
import main.java.es.agata.twochess.board.representations.GameStateRepresentationBuilder;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.squares.Coordinates;
import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.List;
import java.util.Optional;

public class GameEngine implements MovesEngine {

    private final Cache<Byte, List<MoveDirection>> cache;
    private final Board                            board;

    public GameEngine(Board board) {
        this.cache = MovesCache.getInstance();
        this.board = board;
    }

    @Override
    public MoveLegality legality(
            Coordinate coordinate,
            Move move,
            Player checkPlayer,
            boolean checkKingSafety
    ) {
        Coordinate landing = coordinate.applyMove(move);

        PieceSet ownPieces      = board.getPieceSet(checkPlayer);
        PieceSet opponentPieces = board.getPieceSet(checkPlayer.opponent());

        if (landing.isOut() || ownPieces.hasAt(landing)) {
            return MoveLegality.ILLEGAL;
        }

        if (opponentPieces.hasAt(landing)) {
            if (!checkKingSafety) {
                return MoveLegality.CAPTURE;
            }

            return moveEndangersKing(
                    coordinate,
                    move,
                    checkPlayer
            )
                   ? MoveLegality.ILLEGAL
                   : MoveLegality.CAPTURE;
        }

        if (!checkKingSafety) {
            return MoveLegality.LEGAL;
        }

        return moveEndangersKing(
                coordinate,
                move,
                checkPlayer
        )
               ? MoveLegality.ILLEGAL
               : MoveLegality.LEGAL;
    }

    @Override
    public Optional<Byte> move(
            Coordinate from,
            Coordinate to
    ) {
        return board.getGameState().move(
                from,
                to
        );
    }

    @Override
    public Optional<Coordinates> legalMoves(Coordinate coordinate) {
        return this.legalMoves(
                coordinate,
                true
        );
    }

    @Override
    public Optional<Coordinates> legalMoves(
            Coordinate coordinate,
            boolean checkKingSafety
    ) {
        return board.getGameState().getSquare(coordinate).map(square -> this.legalMoves(
                coordinate,
                square,
                checkKingSafety
        ));
    }

    @Override
    public GameStateRepresentation getRepresentation(GameStateRepresentationBuilder representationBuilder) {
        return representationBuilder.build(board.getGameState());
    }

    private boolean moveEndangersKing(
            Coordinate coordinate,
            Move move,
            Player checkPlayer
    ) {
        Coordinate landing = coordinate.applyMove(move);

        if (landing.isOut()) {
            return false;
        }

        Byte landingValue = this.board.getGameState().getSquare(landing).get();

        try {
            this.move(
                    coordinate,
                    landing
            );

            Coordinate kingCoordinates = board.getPieceSet(checkPlayer).getKing();

            return targetedCoordinates(checkPlayer.opponent()).contains(kingCoordinates);
        } finally {
            this.move(
                    landing,
                    coordinate
            );
            if (!Piece.from(landingValue).equals(Piece.NONE)) {
                this.board.getGameState().setSquare(
                        landing,
                        landingValue
                );
            }
        }
    }

    private Coordinates targetedCoordinates(Player player) {
        Coordinates result = new Coordinates();

        for (PieceInfo pieceInfo : board.getPieceSet(player)) {
            Optional<Coordinates> moves = legalMoves(
                    pieceInfo.getCoordinate(),
                    false
            );

            result.add(moves.get());
        }

        return result;
    }

    private Coordinates legalMoves(
            Coordinate coordinate,
            Byte squareValue,
            boolean checkKingSafety
    ) {
        Coordinates         coordinates = new Coordinates();
        List<MoveDirection> moves       = this.cache.get(squareValue).orElse(List.of());

        Player player = Player.from(squareValue);

        for (MoveDirection moveDirection : moves) {
            for (Move move : moveDirection) {
                MoveLegality legality = legality(
                        coordinate,
                        move,
                        player,
                        checkKingSafety
                );

                if (legality.isLegal() || legality.isCapture()) {
                    coordinates.add(coordinate.applyMove(move));
                }

                if (legality.isCapture() || legality.isIllegal()) {
                    break;
                }
            }
        }

        return coordinates;
    }
}
