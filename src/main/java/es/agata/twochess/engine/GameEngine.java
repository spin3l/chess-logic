package main.java.es.agata.twochess.engine;

import main.java.es.agata.twochess.board.Board;
import main.java.es.agata.twochess.board.moves.*;
import main.java.es.agata.twochess.board.pieces.PieceInfo;
import main.java.es.agata.twochess.board.pieces.PieceSet;
import main.java.es.agata.twochess.board.representations.GameStateRepresentation;
import main.java.es.agata.twochess.board.representations.GameStateRepresentationBuilder;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.squares.Coordinates;
import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.List;
import java.util.Optional;

public record GameEngine(Board board) implements MovesEngine {

    private static final MovesCache cache = MovesCache.getInstance();

    static {
        for (Moves m : Moves.values()) {
            cache.put(
                    m.piece().value(Player.WHITE),
                    m.moves()
            );
            cache.put(
                    m.piece().value(Player.BLACK),
                    m.mirroredMoves()
            );
        }
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
        // Enemy targets this players king ??
        Coordinate landing = coordinate.applyMove(move);

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
        }
    }

    private Coordinates targetedCoordinates(Player player) {
        Coordinates result = new Coordinates();

        this.board.getPieceSet(player).forEach(pieceInfo -> result.add(legalMoves(
                pieceInfo.getCoordinate(),
                false
        ).get()));

        return result;
    }

    private Coordinates legalMoves(
            Coordinate coordinate,
            Byte squareValue,
            boolean checkKingSafety
    ) {
        Coordinates         coordinates = new Coordinates();
        List<MoveDirection> moves       = moves(squareValue);

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

    private static List<MoveDirection> moves(byte squareValue) {
        return GameEngine.cache.get(squareValue).orElse(List.of());
    }
}
