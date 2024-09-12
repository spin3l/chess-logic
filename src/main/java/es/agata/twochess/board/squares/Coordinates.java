package main.java.es.agata.twochess.board.squares;

import main.java.es.agata.twochess.board.state.Coordinate;

import java.util.*;

public class Coordinates implements Iterable<Coordinate> {

    private final Set<Coordinate> coordinates;

    public Coordinates() {
        this.coordinates = new HashSet<>();
    }

    private Coordinates(Collection<Coordinate> coordinates) {
        this.coordinates = new HashSet<>(coordinates);
    }

    public static Coordinates of(Collection<Coordinate> coordinates) {
        return new Coordinates(coordinates);
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return this.coordinates.iterator();
    }

    public Coordinates add(Coordinates coordinates) {
        this.coordinates.addAll(coordinates.coordinates);
        return this;
    }

    public void add(Coordinate coordinate) {
        this.coordinates.add(coordinate);
    }

    public boolean contains(Coordinate coordinate) {
        return this.coordinates.contains(coordinate);
    }

    @Override
    public String toString() {
        return coordinates.stream().sorted().toList().toString();
    }
}
