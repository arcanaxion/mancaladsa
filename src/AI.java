import java.util.ArrayList;

public class AI {

    public static int getBestMove() {
        ArrayList<int[]> moves = new ArrayList<int[]>();


        for (int hole = 0; hole < Board.board.size(); hole++) {
            if (Board.board.get(hole) != 0) {
                int[] pair = {hole, aiMove(hole)};
                moves.add(pair);
            }
        }

        int[] hole = new int[moves.size()];
        int[] seeds = new int[moves.size()];
        for (int x = 0; x < moves.size(); x++) {
            hole[x] = moves.get(x)[0];
            seeds[x] = moves.get(x)[1];
        }

        int index = 0;
        int max = seeds[0];
        for (int x = 0; x < hole.length; x++) {
            if (seeds[x] > max) {
                max = seeds[x];
                index = x;
            }
        }

        if (max == 0) {
            return hole[(int) Math.round(Math.random() * (hole.length-1))];
        }
        return hole[index];
    }

    public static int aiMove(int index) {
        ArrayList<Integer> aiBoard = new ArrayList<Integer>();
        for (int x = 0; x < Board.board.size(); x++) {
            aiBoard.add(Board.board.get(x));
        }

        int tempIndex = index; // index for every time hand picks up new seeds
        int hand = aiBoard.get(tempIndex);
        aiBoard.set(tempIndex, 0);
        int pointer = tempIndex;

        while (true) {
            for (int x = hand; x > 0; x--) {
                pointer = ((pointer == 13) ? 0 : (pointer += 1));
                aiBoard.set(pointer, aiBoard.get(pointer)+1);
            }
            pointer = ((pointer == 13) ? 0 : (pointer += 1));
            if (aiBoard.get(pointer) != 0) {
                tempIndex = pointer;
                hand = aiBoard.get(tempIndex);
                aiBoard.set(tempIndex, 0);
            } else {
                pointer = ((pointer == 13) ? 0 : (pointer += 1));
                return aiBoard.get(pointer);
            }
        }
    }

}
