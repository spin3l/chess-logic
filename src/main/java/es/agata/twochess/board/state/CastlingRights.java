package main.java.es.agata.twochess.board.state;

public class CastlingRights {

    private Boolean kingSide, queenSide;

    public CastlingRights() {
        this(
                true,
                true
        );
    }

    public CastlingRights(
            Boolean kingSide,
            Boolean queenSide
    ) {
        this.kingSide  = kingSide;
        this.queenSide = queenSide;
    }

    public Boolean getKingSide() {
        return this.kingSide;
    }

    public Boolean canQueenSide() {
        return this.queenSide;
    }


    public void disable() {
        this.disableKingSide();
        this.disableQueenSide();
    }

    public void disableKingSide() {
        this.kingSide = false;
    }

    public void disableQueenSide() {
        this.queenSide = false;
    }

    @Override
    public String toString() {
        if (!this.kingSide && !this.queenSide) {
            return "";
        }

        StringBuilder res = new StringBuilder();
        if (this.kingSide) {
            res.append('k');
        }
        if (this.queenSide) {
            res.append('q');
        }
        return res.toString();
    }
}
