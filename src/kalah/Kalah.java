package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.kalahBoard.Board;


/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure. Remove this comment (or rather, replace it
 * with something more appropriate)
 */
public class Kalah {

	public static void main(String[] args) {

		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Replace what's below with your implementation
		Board board = new Board(io);
		board.playGame();

    }
}
