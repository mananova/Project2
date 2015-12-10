package ch.epfl.maze.physical.zoo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */
public class Mouse extends Animal {
	
	private Direction prevChoice;
	
	/**
	 * Constructs a mouse with a starting position.
	 * 
	 * @param position
	 *            Starting position of the mouse in the labyrinth
	 */
	public Mouse(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to an improved version of a <i>random walk</i> : the
	 * mouse does not directly retrace its steps.
	 */
	@Override
	public Direction move(Direction[] choices) {
		Random rand = new Random();
		if(choices.length == 1){
			prevChoice = choices[0];
		}else{
			if (prevChoice == null){
				prevChoice = choices[rand.nextInt(choices.length)];
			}else{
				List<Direction> newChoices = new ArrayList<Direction>();
				for (int i = 0; i < choices.length; i++){
					if (!choices[i].equals(prevChoice.reverse())){
						newChoices.add(choices[i]);
					}
				}
				prevChoice = newChoices.get(rand.nextInt(newChoices.size()));
			}
		}
		return prevChoice;
	}

	@Override
	public Animal copy() {
		return new Mouse(this.getPosition());
	}
}
