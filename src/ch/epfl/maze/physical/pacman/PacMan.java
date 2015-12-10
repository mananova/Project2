package ch.epfl.maze.physical.pacman;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */
public class PacMan extends Prey {

	private Direction prevChoice;
	
	public PacMan(Vector2D position) {
		super(position);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
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
		return new PacMan(this.getPosition());
	}
}
