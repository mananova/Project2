package ch.epfl.maze.physical.pacman;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */
public class Pinky extends Predator {

	/**
	 * Constructs a Pinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Pinky in the labyrinth
	 */
	public Pinky(Vector2D position) {
		super(position);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		Direction targetDir = this.getTarget(daedalus).getDirection();
		if (targetDir == null){
			targetDir = Direction.DOWN;
		}
		Vector2D offset = new Vector2D(0, 0);
		offset = offset.addDirectionTo(targetDir);
		offset = offset.mul(4);
		return standardMove(choices, getTarget(daedalus).getPosition().add(offset));
	}

	@Override
	public Animal copy() {
		return new Pinky(getMaison());
	}
}
