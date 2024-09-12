package main.java.es.agata.twochess.board.representations;

import main.java.es.agata.twochess.board.state.GameState;

public interface GameStateRepresentation {
    String toString();
    GameState getGameState();
}
