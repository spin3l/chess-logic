package main.java.es.agata.twochess.board;

import main.java.es.agata.twochess.board.pieces.PieceSet;
import main.java.es.agata.twochess.board.pieces.Player;
import main.java.es.agata.twochess.board.representations.FenRepresentation;
import main.java.es.agata.twochess.board.state.GameState;
import main.java.es.agata.twochess.board.state.GameStateHistory;


public class Board {

    private final GameState        gameState;
    private final GameStateHistory gameStateHistory;

    public Board() {
        this.gameState        = new FenRepresentation.Builder().initial().getGameState();
        this.gameStateHistory = new GameStateHistory();
    }

    public Board(
            GameState gameState,
            GameStateHistory gameStateHistory
    ) {
        this.gameState        = gameState;
        this.gameStateHistory = gameStateHistory;
    }


    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public String toString() {
        return gameState.toString();
    }

    public PieceSet getPieceSet(Player player) {
        return gameState.getPieceSet(player);
    }
}
