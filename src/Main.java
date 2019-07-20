import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startgame();
    }

    public static boolean isNumber(String user) {
        try {
            Integer.parseInt(user);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public static void startgame() {
        System.out.println("[ WELCOME TO MANCALA ]\n");
        String choice = null;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Select Option:\n[Start Game] - 'S'\n" +
                    "[Quit Game] - 'Q'\n[Instructions] - 'I'\n\nEnter: ");
            choice = input.nextLine().toUpperCase();
            if (choice.equals("I"))
                printInstructions();
        } while (!(choice.equals("Q") || choice.equals("S")));

        if (choice.equals("S")) {
            createBoard();
            chooseGamemode();
        } else {
            System.out.println("Exiting Mancala...");
        }

    }


    public static void createBoard() {
        Scanner input = new Scanner(System.in);
        String seedsS = null;
        do {
            System.out.println("Select number of seeds (at least 1) per hole\nDefault is 4\n-> ");
            seedsS = input.nextLine();
        } while (!isNumber(seedsS) || Integer.parseInt(seedsS) < 1);
        int seeds = Integer.parseInt(seedsS);

        // create new board
        LinkedList<Integer> newboard = new LinkedList<Integer>();
        for (int fill = 0; fill < 14; fill++) {
            newboard.add(seeds);
        }
        Board.setBoard(newboard);
    }


    public static void chooseGamemode() {
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Select your gamemode:\n[Player v Player] - 'P'\n" +
                    "[Player v Computer] - 'C'\n\nEnter:");
            choice = input.nextLine();
        } while (!(choice.toUpperCase().equals("P") || choice.toUpperCase().equals("C")));

        if (choice.toUpperCase().equals("P")) {
            pvp();
        } else if (choice.toUpperCase().equals("C")) {
            pvc();
        }
    }

    public static void pvp() {
        String playerOne = null;
        String playerTwo = null;

        Scanner input = new Scanner(System.in);
        System.out.println("Player 1, enter your name!\n-> ");
        playerOne = input.nextLine();
        System.out.println("Player 2, enter your name!\n-> ");
        playerTwo = input.nextLine();


        int remainingSeeds = 0;
        while (true) {
            // Player 1's turn
            System.out.printf("[Player 1] %s, it's your turn!\n", playerOne);
            Board.userplay(1);
            System.out.printf("[Player 1] %s, you currently have %d points!\n",
                    playerOne, Board.playerOne);
            // separator
            System.out.println("\n");
            // after each player's turn, determine if while loop should continue
            remainingSeeds = 0;
            for (int index = 0; index < Board.board.size(); index++) {
                remainingSeeds += Board.board.get(index);
            }
            if (remainingSeeds <= 1) {
                break;
            }
            // END Player 1's turn

            // Player 2's turn
            System.out.printf("[Player 2] %s, it's your turn!\n", playerTwo);
            Board.userplay(2);
            System.out.printf("[Player 2] %s, you currently have %d points!\n",
                    playerTwo, Board.playerTwo);
            // separator
            System.out.println("\n");
            // after each player's turn, determine if while loop should continue
            remainingSeeds = 0;
            for (int index = 0; index < Board.board.size(); index++) {
                remainingSeeds += Board.board.get(index);
            }
            if (remainingSeeds <= 1) {
                break;
            }
            // END Player 2's turn
        }

        Board.printBoard();
        System.out.println("No more playable moves.\nGame is over!\n");
        System.out.printf("Final scores:\n[Player 1] %s: %d\n[Player 2] %s: %d\n\n", playerOne,
                Board.playerOne, playerTwo, Board.playerTwo);
        // determine winner
        if (Board.playerOne > Board.playerTwo) {
            System.out.printf("The winner is [Player 1] %s!\n", playerOne);
        } else if (Board.playerOne < Board.playerTwo) {
            System.out.printf("The winner is [Player 2] %s!\n", playerTwo);
        } else {
            System.out.printf("Scores are tied... It's a draw!");
        }
    }

    public static void pvc() {
        String playerOne = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Player 1, enter your name!\n-> ");
        playerOne = input.nextLine();

        int remainingSeeds = 0;
        while (true) {
            // Player 1's turn
            System.out.printf("[Player 1] %s, it's your turn!\n", playerOne);
            Board.userplay(1);
            System.out.printf("[Player 1] %s, you currently have %d points!\n",
                    playerOne, Board.playerOne);
            // separator
            System.out.println("\n");
            // after each player's turn, determine if while loop should continue
            remainingSeeds = 0;
            for (int index = 0; index < Board.board.size(); index++) {
                remainingSeeds += Board.board.get(index);
            }
            if (remainingSeeds <= 1) {
                break;
            }
            // END Player 1's turn

            // AI's turn
            System.out.printf("[AI] It is the AI's turn!\n");
            Board.printBoard();
            int aiMove = AI.getBestMove();
            System.out.printf("[AI] Computer chooses hole %d\n", aiMove+1);
            Board.singleTurn(aiMove,2);
            System.out.printf("[AI] The AI currently has %d points!\n",
                    Board.playerTwo);
            // separator
            System.out.println("\n");
            // after each player's turn, determine if while loop should continue
            remainingSeeds = 0;
            for (int index = 0; index < Board.board.size(); index++) {
                remainingSeeds += Board.board.get(index);
            }
            if (remainingSeeds <= 1) {
                break;
            }
            // END Player 2's turn
        }

        System.out.println("No more playable moves.\nGame is over!\n");
        System.out.printf("Final scores:\n[Player 1] %s: %d\n[AI] Computer: %d\n\n", playerOne,
                Board.playerOne, Board.playerTwo);
        // determine winner
        if (Board.playerOne > Board.playerTwo) {
            System.out.printf("The winner is [Player 1] %s!\n", playerOne);
        } else if (Board.playerOne < Board.playerTwo) {
            System.out.printf("The winner is [AI] Computer!\n");
        } else {
            System.out.printf("Scores are tied... It's a draw!");
        }
    }

    public static void printInstructions() {
        // create new board
        LinkedList<Integer> instructionBoard = new LinkedList<Integer>();
        for (int fill = 0; fill < 14; fill++) {
            instructionBoard.add(fill+1);
        }
        Board.setBoard(instructionBoard);

        System.out.println("Mancala is a game played between 2 people.\nA board of 2 rows and 7 columns " +
                "is used.\nEach hole is occupied by 4 seeds.\n");
        Board.printBoard();
        System.out.println("\nA player can select a hole by entering a number between '1' and '14'.\n" +
                "The hole numbers are shown in the diagram above.\n");

        System.out.println("Players take turns to select a hole.\nSeeds from the hole are placed in the " +
                "player's hand and\ngoing counter-clockwise, distributes 1 into each hole.\n");
        System.out.println("Once the hand is empty, the player picks up the seeds in the next hole\n" +
                "and continues until the hole the player should collect seeds from is empty.\n" +
                "Once that happens, the player's turn is over and the next player begins their turn.\n");
    }
}