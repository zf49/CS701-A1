package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
<<<<<<< HEAD
=======
import kalah.board.Board;

>>>>>>> 87782f4bd6b74cfc3efcb41ebfbd0d33177d5dcc

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure. Remove this comment (or rather, replace it
 * with something more appropriate)
 */
public class Kalah {
<<<<<<< HEAD
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		// Replace what's below with your implementation
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
		io.println("Player 1's turn - Specify house number or 'q' to quit: ");
	}
=======

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		Board board = new Board(io);
		board.playGame();
    }
>>>>>>> 87782f4bd6b74cfc3efcb41ebfbd0d33177d5dcc
}
