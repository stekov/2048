import java.awt.*;
import javax.swing.*;

public class View {
    JFrame window;
    private final int n;
    private final Model game;
    private final Square[][] boardV;
    private Square square;
    private final JLabel message;

    public View(Boardgame game, int n) {
        this.n = n;
        this.game = (Model) game;
        this.game.currentMessage = "Welcome! Use the arrow keys to play.";
        boardV = new Square[n][n];

        // Message section
        message = new JLabel();
        JPanel messagePanel = new JPanel();
        messagePanel.setSize(n, 100);
        messagePanel.add(getMessageV());
        messagePanel.setMaximumSize(new Dimension(100*n, 100));

        // Game section
        JPanel gamePanel = new JPanel();
        gamePanel.setSize(100*n, 100*n);
        gamePanel.setLayout(new GridLayout(n, n, 2, 2));

        // Window (frame)
        window = new JFrame("2048");
        window.add(messagePanel);
        window.add(gamePanel);
        window.setSize(100*n, 100*n);
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adds Square-objects to board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                square = new Square(i, j);
                boardV[i][j] = square;
                gamePanel.add(square);
            }
        }
        randomTileV();
    }

    private void randomTileV() {
        int i = game.randRow;
        int j = game.randCol;
        boardV[i][j].updateSquare(game.tile);
        updateView();
    }

    // Updates all squares from Model
    private void updateSquares() {
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++) {
                square = boardV[i][j];
                square.updateSquare(game.board[i][j]);
            }
        }
    }

    // Updates and colors all Squares
    void updateView(){
        updateSquares();
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                square = boardV[i][j];
                if (square.value == 0) { square.paintSquare(200, 200, 200); }
                else if (square.value == 2){ square.paintSquare(204, 153, 255); }
                else if (square.value == 4){ square.paintSquare(153, 153, 255); }
                else if (square.value == 8){ square.paintSquare(153, 204, 255); }
                else if (square.value == 16){ square.paintSquare(153, 255, 255); }
                else if (square.value == 32){ square.paintSquare(153, 255, 204); }
                else if (square.value == 64){ square.paintSquare(153, 255, 153);  }
                else if (square.value == 128){ square.paintSquare(204, 255, 153);  }
                else if (square.value == 256){ square.paintSquare(255, 255, 153);  }
                else if (square.value == 512){ square.paintSquare(255, 204, 153);  }
                else if (square.value == 1024){ square.paintSquare(255, 153, 153);  }
                else if (square.value == 2048){ square.paintSquare(255, 0, 127);  }
                else { square.paintSquare(102, 0, 51);  }
            }
        }
    }

    // Updates the label message
    JLabel getMessageV(){
        String theMessage = game.getMessage();
        message.setText(theMessage);
        return message;
    }
}
