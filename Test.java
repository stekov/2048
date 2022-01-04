import java.util.Scanner;

public class Test {

    public static void main(String[] u) {
        int n = 4;
        Scanner scan = new Scanner(System.in);
        Boardgame thegame = new Model(n);                 // Creates Model objecs
        System.out.println("\nWelcome to 2048!\n");

        boolean a = true;
        while (a) {
            // Prints board
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++)
                    System.out.print("  " + thegame.getStatus(i,j));
                System.out.println();
            }
            // Players move
            System.out.println();
            System.out.print("Your move. Pick a direction (l, r, u, d) or q to quit: ");
            String direction = scan.next();
            a = thegame.move(direction);
            System.out.println(thegame.getMessage());
        }
    }
}
