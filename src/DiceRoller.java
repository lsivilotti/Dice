
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DiceRoller {
	static int[] winners;
	static int NUMBER_OF_WINNERS = 7;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Dice Roller");
		frame.setVisible(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		DiceUX diceUX = new DiceUX(frame);
		diceUX.uxPanel();
	}
}