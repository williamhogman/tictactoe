package se.whn.tictactoe;

/**
 * 
 */
public class App 
{
    public static void main( String[] args )
    {
	App app = new App();
    }

    private App() {
        CLI c = new CLI();
        c.run();
    }




    public void runAiGame() {
	Player p1 = new Player(new AlgoAI());
	Player p2 = new Player(new RandomAI());

	p1.setRenderChar('X');
	p2.setRenderChar('O');

	Game g = new Game(p1, p2);

	CLIGameRenderer renderer = new CLIGameRenderer(g);

	while(!g.isGameOver()) {
	    g.playTurn();
	    renderer.render();
	}
    }

    public void testAIStrength(Actor underTest) {
        Player p1 = new Player(underTest);
        Player p2 = new Player(new RandomAI());
        playLoadsOfGames(p1, p2);
    }

    private void playLoadsOfGames(Player p1, Player p2) {
        int won = 0;
        int lost = 0;
        int drawn = 0;
        
        for(int i = 0; i < 100000; i++) {
            Game g = new Game(p2, p1);

            while(!g.isGameOver()) {
                g.playTurn();
            }
            Player winner = g.getWinner();
            if(winner == p1) {
                won++;
            } else if(winner == p2) {
                lost++;
            } else {
                drawn++;
            }
        }

        System.out.println("Won: " + won);
        System.out.println("Lost: " + lost);
        System.out.println("Drawn: " + drawn);
    }

    public void findLosingGame() {
        Player p1 = new Player(new AlgoAI());
        Player p2 = new Player(new RandomAI());
        p1.setRenderChar('X');
        p2.setRenderChar('O');

        for(;;) {
            Game g = new Game(p2, p1);
            CLIGameRenderer renderer = new CLIGameRenderer(g);
            

            while(!g.isGameOver()) {
                g.playTurn();
                renderer.render();
            }

            Player winner = g.getWinner();
            if(winner == p2) {
                break;
            }
        }
    }

    public void findLostingGameVHeuristicAI() {

        Player p1 = new Player(new AlgoAI());
        Player p2 = new Player(new AlternatingAI(new HeuristicAI(), new RandomAI()));
        p1.setRenderChar('X');
        p2.setRenderChar('O');

        for(;;) {
            Game g = new Game(p2, p1);
            CLIGameRenderer renderer = new CLIGameRenderer(g);
            

            while(!g.isGameOver()) {
                g.playTurn();
                renderer.render();
            }

            Player winner = g.getWinner();
            if(winner == p2) {
                break;
            }            
        }
    }


    public void nnAiTest() {
    }
}
