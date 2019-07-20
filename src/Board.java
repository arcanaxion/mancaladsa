import java.util.LinkedList;
import java.util.Scanner;

public class Board {
    static int playerOne = 0;
    static int playerTwo = 0;
    static LinkedList<Integer> board = new LinkedList<Integer>();

    public static void setBoard(LinkedList<Integer> newboard) {
        board = newboard;
    }


    public static boolean validInput(String user) {
        try {
            int num = Integer.parseInt(user);
            // fail case #1: out of bounds
            if (num > 14 || num < 1) {
                System.out.println("Incorrect input! Please enter again: ");
                return false;
            } else if (board.get(num-1) == 0) {
                System.out.println("That hole is empty... you can't select that! Please enter again: ");
                return false;
            }

            // validation successful
            return true;
            // fail case #2: number format exception
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a number, not alphabets: ");
            return false;
        }
    }


    //promt user to enter which hole he want to start the mancala
    public static void userplay(int player) {
        printBoard();
        System.out.println("Please choose a hole that you want to move: ");
        Scanner input = new Scanner(System.in);
        String umoveStr;
        do {
            umoveStr = input.nextLine();
        } while (!validInput(umoveStr));
        int umoveInt = Integer.parseInt(umoveStr);
        System.out.println("You choose to move seed from hole " + umoveInt);
        singleTurn(umoveInt-1, player);
        printBoard();
        System.out.println();
    }

    // performs a full single turn
    // accepts which index to move, and whose mancala to put seeds in
    //   player == 1: points go to playerOne
    //   else: points go to playerTwo
    public static void singleTurn(int index, int player) {
        int tempIndex = index; // index for every time hand picks up new seeds
        int hand = board.get(tempIndex);
        board.set(tempIndex, 0);
        int pointer = tempIndex;

        while (true) {
            for (int x = hand; x > 0; x--) {
                pointer = ((pointer == 13) ? 0 : (pointer += 1));
                board.set(pointer, board.get(pointer)+1);
            }
            pointer = ((pointer == 13) ? 0 : (pointer += 1));
            if (board.get(pointer) != 0) {
                tempIndex = pointer;
                hand = board.get(tempIndex);
                board.set(tempIndex, 0);
            } else {
                pointer = ((pointer == 13) ? 0 : (pointer += 1));
                // take contents in board.get(pointer)
                if (player == 1) {
                    playerOne += board.get(pointer);
                } else {
                    playerTwo += board.get(pointer);
                }
                board.set(pointer, 0);
                return;
            }
        }
    }

    /*
    Print Board
    */
    public static void printBoard() {
        for (int row = 0; row < 3; row++) {
            for (int alt = 0; alt < 15; alt++) {
                if (row == 1) {
                    System.out.print("---");
                } else if (alt%2 == 0) {
                    System.out.print(" | ");
                } else if (row == 0) {
                    System.out.printf("%3d", board.get((int) (alt*-0.5 + 13.5)));
                } else {
                    System.out.printf("%3d", board.get((int) (alt*0.5 - 0.5)));
                }
            }
            System.out.println();
        }
    }
}