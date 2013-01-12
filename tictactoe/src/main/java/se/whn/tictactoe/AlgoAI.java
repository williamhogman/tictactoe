package se.whn.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class AlgoAI extends Actor {

    /*
     * All squares are part of atleast one vertical
     * and one horizontal line. (2)
     * 
     * The corners are, in addition to the aforementioned lines part
     * of one diagonal. (3)
     *
     * The middle square is part of both diagonals. (4)
     *
     * Converting them to an ordninal scale yields the following array
     */
    private static final int[] SQUARE_VALUES = {
        2, 1, 2,
        1, 3, 1,
        2, 1, 2
    };

    private Square byHeuristicScore(List<Square> squares){
        //Square[] sq = new Square[squares.size()];
        //return byHeuristicScore(squares.toArray(sq));
        /* 
         * Appearently this method is called a lot! 
         * It is better to duplicate the code than to call the other
         * function! 
         */
        Square bestSquare = null;
        int bestValue = 0;
        // Note that this is a heuristic that only works for a 3x3 grid
        for(Square s : squares) {
            int value = SQUARE_VALUES[s.getIndex()];
            if(value > bestValue) {
                bestValue = value;
                bestSquare = s;
            }
        }

        return bestSquare;
    }

    private Square byHeuristicScore(Square[] squares) {
        Square bestSquare = null;
        int bestValue = 0;
        // Note that this is a heuristic that only works for a 3x3 grid
        for(Square s : squares) {
            int value = SQUARE_VALUES[s.getIndex()];
            if(value > bestValue) {
                bestValue = value;
                bestSquare = s;
            }
        }

        return bestSquare;
    }

    private Actor fallback;

    public AlgoAI() {
        fallback = new RandomAI();
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

        if(sq != null) {
            return sq.getIndex();
        }

        return fallback.selectMove(game, plr);
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
        return byHeuristicScore(candidates);
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
