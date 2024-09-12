package main.java.es.agata.twochess;

import main.java.es.agata.twochess.board.Board;
import main.java.es.agata.twochess.board.state.Coordinate;
import main.java.es.agata.twochess.engine.GameEngine;
import main.java.es.agata.twochess.engine.MovesEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws
                                           IOException {

        MovesEngine engine = new GameEngine(new Board());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Input the desired position");
            String response = reader.readLine();

            Coordinate coordinate = Coordinate.from(response);

            System.out.println(engine.legalMoves(coordinate).get());
        }
    }
}
