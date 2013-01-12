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

    public App() {
	//runAiGame();
        testAIStrength();
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

    public void testAIStrength() {
        Player p1 = new Player(new AlgoAI());
        Player p2 = new Player(new RandomAI());

        int won = 0;
        int lost = 0;
        int drawn = 0;
        
        for(int i = 0; i < 100000; i++) {
            Game g = new Game(p1, p2);

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
}
