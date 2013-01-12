package se.whn.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class AlgoAI extends HeuristicAI {

    public AlgoAI() {
    }

    protected int selectMove(Game game, Player plr) {
        Grid g = game.getGrid();
        
        Line[] allLines = g.getAllLines();
        Square sq = blockingAndWinning(allLines, plr);

        if (sq != null) {
            return sq.getIndex();
        }

        sq = expanding(allLines, plr);

        if (sq != null) {
            return sq.getIndex();
        }

        sq = initial(g, plr);

        return sq.getIndex();
    }

    private Square initial(Grid g, Player plr) {
        return byHeuristicScore(g.getUnoccupiedSquares());
    }

    private Square expanding(Line[] lines, Player plr) {
        List<Square> candidates = new ArrayList<Square>();
        for(Line l : lines) {
            if(l.isOwnable(plr) && l.hasAny(plr)) {
                for(Square s : l.getUnoccupied()) {
                    candidates.add(s);
                }
            }
        }
        return this.byHeuristicScore(candidates);
    }
    
    /**
     * Blocking and winning, tries to place pieces to win or prevent
     * the opponent from winning.
     */
    private Square blockingAndWinning(Line[] lines, Player plr) {
        // we don't have more than one move so it makes no sense to
        // store more than one square that we have to block.
        Square mustBlock = null;

        for(Line l : lines) {
            Player placeToOwn = l.canPlaceToOwn();
            if(placeToOwn == plr) {
                // we won! GG
                return l.getFirstUnoccupied();
            } else if(placeToOwn != null) {
                // prevent losing
                mustBlock = l.getFirstUnoccupied();
            }                 
        }

        return mustBlock;
    }

}
