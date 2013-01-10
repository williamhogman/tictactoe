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
	runAiGame();
    }


    public void runAiGame() {
	Player p1 = new Player(new RandomAI());
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
}
