package main.java.es.agata.twochess.board.representations;

import main.java.es.agata.twochess.board.state.GameState;

public interface GameStateRepresentationBuilder {

    GameStateRepresentation initial();

    GameStateRepresentation build(GameState gameState);

    GameState build(String representation);

}
