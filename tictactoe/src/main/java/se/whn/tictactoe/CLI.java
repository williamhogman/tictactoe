package se.whn.tictactoe;

import java.io.PrintStream;
import java.io.InputStream;
import java.util.Scanner;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class CLI {

    private PrintStream out;
    private InputStream in;

    private Scanner scanner;
    private boolean shouldRun;

    public CLI() {
        this(System.in, System.out);
    }

    public CLI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.scanner = new Scanner(in);
        this.shouldRun = true;
    }

    public void run() {
        commandLoop();
    }

    private void commandLoop() {
        while(shouldRun) {
            String cmd = readCmd();
            Method m = findMethodForCommand(cmd);

            if(m == null) {
                out.println("No such command");
                continue;
            }

            try {
                m.invoke(this, null);
            } catch(IllegalArgumentException ex) {
                out.println("unable to perform command...");
            } catch(IllegalAccessException ex) {
                out.println("couldn't run command, this shouldn't happend when running the jar locally");
                out.println(ex);
            } catch(InvocationTargetException ex) {
                message("The command failed unexpectedly, printing stack trace:");
                message(ex.getCause());
                ex.printStackTrace(out);
            }
        }
    }

    private static Method findMethodForCommand(String command) {
        String name = command + "Command";
        for(Method m : CLI.class.getDeclaredMethods()) {
            int parameters = m.getParameterTypes().length;
            if(m.getName().equalsIgnoreCase(name) && parameters == 0) {
                return m;
            }
        }
        return null;
    }

    private String readCmd() {
        out.print("$ ");
        return scanner.nextLine().toLowerCase();
    }

    public int readInt(String prompt, int min, int max) {
        out.print(String.format("%s (%d-%d): ", prompt, min, max));
        String s = scanner.nextLine();
        try {
            int i = Integer.parseInt(s);
            if(i < min || i > max) {
                message("Integer out of range");
                return readInt(prompt, min, max);
            } else {
                return i;
            }
        } catch (NumberFormatException ex) {
            message("Please enter a valid integer.");
            return readInt(prompt, min, max);
        }
    }

    private void message(Object o) {
        out.println(o.toString());
    }

    private void quitCommand() {
        shouldRun = false;
        message("Bye!");
    }

    private void benchmarkCommand() {
        message("Going to run benchmark of 100k games pittng the AI against itself");
        
        Player p1 = new Player(new AlgoAI());
        Player p2 = new Player(new AlgoAI());
        
        long start = System.nanoTime();
        for(int i = 0; i < 100000; i++) {
            Game g = new Game(p1, p2);
            while(!g.isGameOver()) {
                g.playTurn();
            }
        }
        long end = System.nanoTime();
        System.gc();
        
        long total = end - start;
        
        message("Took "+total+" ns");
        message((total / 1000000.0) +" ms");
    }

    private void playCommand() {
        Player p1 = new Player(new CLIActor(this));
        Player p2 = new Player(new AlgoAI());

        p1.setRenderChar('X');
        p2.setRenderChar('O');

        Game g = new Game(p1, p2);
        CLIGameRenderer renderer = new CLIGameRenderer(g);

        renderer.render();

        while(!g.isGameOver()) {
            g.playTurn();
            renderer.render();
        }
    }
}

class CLIActor extends Actor {
    private CLI cli;

    public CLIActor(CLI cli) {
        this.cli = cli;
    }

    protected int selectMove(Game g, Player plr) {
        boolean invalidMove = true;
        int move = -1;
        while(invalidMove) {
            move = cli.readInt("Select a move. 0 is top left, 8 is bottom right.", 0, 8);
            // a move is invalid if the square is occuped
            invalidMove = g.getGrid().getSquare(move).isOccupied();
        }
            
        return move;
    }
}
