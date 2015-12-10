package ch.epfl.maze.physical.pacman;
import java.util.List;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */
public class Inky extends Predator {
	
	private Blinky partner;
	
	/**
	 * Constructs a Inky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Inky in the labyrinth
	 */
	public Inky(Vector2D position) {
		super(position);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		List<Predator> predators = daedalus.getPredators();
		for (int i = 0; i < predators.size(); i++){
			if (predators.get(i).getClass().equals(Blinky.class)){
				partner = (Blinky) predators.get(i);
			}
		}
		if (partner != null){
			Vector2D partnerPos = partner.getPosition(); //OA
			Vector2D preyPos = getTarget(daedalus).getPosition(); //OB
			Vector2D targetPos = preyPos.mul(2).add(partnerPos.negate()); //OC =2OB - OA
			return standardMove(choices, targetPos);
		}
		return Direction.NONE;
	}

	@Override
	public Animal copy() {
		return new Inky(getMaison());
	}
}
