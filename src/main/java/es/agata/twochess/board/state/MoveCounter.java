package main.java.es.agata.twochess.board.state;

public class MoveCounter {

    private int counter;

    public MoveCounter(int starting) {
        this.counter = starting;
    }

    @Override
    public String toString() {
        return "" + counter;
    }
}
