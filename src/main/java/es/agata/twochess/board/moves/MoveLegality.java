package main.java.es.agata.twochess.board.moves;

public enum MoveLegality {
    ILLEGAL,
    LEGAL,
    CAPTURE;

    public boolean isLegal() {
        return this.equals(LEGAL);
    }

    public boolean isIllegal() {
        return this.equals(ILLEGAL);
    }

    public boolean isCapture() {
        return this.equals(CAPTURE);
    }
}
