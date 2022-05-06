import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Control implements KeyListener {
    Boardgame game;
    View view;
    private boolean released = true;

    public Control() {
        // Model implements interface Boardgame
        int n = getUserSize();
        game = new Model(n);
        // View is separate from Control, because it uses different "buttons"
        view = new View(game, n);
        // Adds key listener to the frame
        view.window.addKeyListener(this);
    }

    // Mock: tests the case of winning 2048
    public Control(boolean mock) {
        int n = getUserSize();
        // Calls a mock constructor for the game
        game = new Model(n, mock);
        view = new View(game, n);
        view.window.addKeyListener(this);
    }

    private int getUserSize() {
        Scanner sc;
        int n;
        while (true) {
            System.out.println("\nPlease input the board size (2 to 9): ");
            sc = new Scanner(System.in);
            try {
                n = Integer.parseInt(sc.nextLine());
                if (n<2 || n>9) continue;
                break;
            }
            catch (NumberFormatException ignored) {
            }
        }
        return n;
    }

    private void move(String direction) {
        // Updates Model
        game.move(direction);
        game.getMessage();
        // Updates View
        view.updateView();
        view.getMessageV();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (released) {
            if (key == KeyEvent.VK_LEFT) {
                move("l");
            }
            else if (key == KeyEvent.VK_RIGHT) {
                move("r");
            }
            else if (key == KeyEvent.VK_UP) {
                move("u");
            }
            else if (key == KeyEvent.VK_DOWN) {
                move("d");
            }
            else if (key == KeyEvent.VK_ENTER) {
                // Starts over game
                view.window.dispose();
                new Control();
            }
            else if (key == KeyEvent.getExtendedKeyCodeForChar('q')) {
                System.exit(0);
            }
        }
        released = false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        released = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
