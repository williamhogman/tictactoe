public class RandomAI extends Actor {

    private Random random;

    public RandomAI() {
	random = new Random();
    }

    protected int selectMove(Game game, Player plr) {
	Grid g  = game.getGrid();

	Square[] candidates = g.getUnoccupiedSquares();

	int i = random.nextInt(candidates.length);
	return candidates[i].getIndex();
    }
}
