import java.util.Arrays;

public class Dice {
	
	private int[] dieAmnts;
	private int[] dieVals = { 4, 6, 8, 10, 12, 20 };
	private String history;
	private int round;
	private int winner;

	private int NUMBER_OF_TYPES = 6;

	public Dice(int startNum) {
		history = "";
		dieAmnts = new int[NUMBER_OF_TYPES];
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			dieAmnts[i] = startNum;
		}
	}

	public String getHistory() {
		return history;
	}

	public int getRound() {
		return round;
	}

	public int getWinner() {
		return winner;
	}

	public void roll() {
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			int count = dieAmnts[i];
			int endCount = 0;
			for (int j = 0; j < count; j++) {
				int roll = (int) (Math.random() * dieVals[i] + 1);
				if (roll == 1) {
					// rolled 1, no die added to next round

				} else if (roll == dieVals[i]) {
					endCount += 2;
				} else {
					endCount++;
				}
			}
			dieAmnts[i] = endCount;
		}
	}

	public int game() {
		round = 0;
		while (typesPresent() > 1) {
			history += Arrays.toString(dieAmnts) + ", " + round + "\n";
			roll();
			round++;
		}
		winner = winner();
		history += Arrays.toString(dieAmnts) + ", " + round + "\n";
		if (winner == 0) {
			history += "Tie";
		} else {
			history += winner + " wins";
		}
		return winnerIndex();
	}

	public int typesPresent() {
		int numTrue = 0;
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			if (dieAmnts[i] != 0) {
				numTrue++;
			}
		}
		return numTrue;
	}

	public void print() {
		System.out.print(Arrays.toString(dieAmnts));
	}

	public int winner() {
		int index = winnerIndex() - 1;
		if (index >= 0) {
			return dieVals[index];
		} else {
			return 0;
		}

	}

	public int winnerIndex() {
		int result = 0;
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			if (dieAmnts[i] > 0) {
				result = i + 1;
			}
		}
		return result;
	}

}