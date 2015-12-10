package ch.epfl.maze.physical.zoo;
import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */
public class Monkey extends Animal {

	private Direction dir;
	
	/**
	 * Constructs a monkey with a starting position.
	 * 
	 * @param position
	 *            Starting position of the monkey in the labyrinth
	 */
	public Monkey(Vector2D position) {
		super(position);
		this.dir = Direction.UP;
	}

	/**
	 * Moves according to the relative left wall that the monkey has to follow.
	 */
	@Override
	public Direction move(Direction[] choices) {
		Direction absDir = dir.unRelativeDirection(Direction.LEFT);
		for (int i = 0; i < choices.length; i++){
			if (choices[i].equals(absDir)){
				dir = absDir;
				return dir;
			}
		}
		absDir = dir.unRelativeDirection(Direction.UP);
		for (int i = 0; i < choices.length; i++){
			if (choices[i].equals(absDir)){
				dir = absDir;
				return dir;
			}
		}
		absDir = dir.unRelativeDirection(Direction.RIGHT);
		for (int i = 0; i < choices.length; i++){
			if (choices[i].equals(absDir)){
				dir = absDir;
				return dir;
			}
		}
		absDir = dir.unRelativeDirection(Direction.DOWN);
		for (int i = 0; i < choices.length; i++){
			if (choices[i].equals(absDir)){
				dir = absDir;
				return dir;
			}
		}
		return Direction.NONE;
	}

	@Override
	public Animal copy() {
		return new Monkey(this.getPosition());
	}
	
}
