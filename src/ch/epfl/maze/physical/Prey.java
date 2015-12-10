package ch.epfl.maze.physical;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;
/**
 * Prey that is killed by a predator when they meet each other in the labyrinth.
 * 
 */
abstract public class Prey extends Animal {

	private Direction prevChoice;
	
	/**
	 * Constructs a prey with a specified position.
	 * 
	 * @param position
	 *            Position of the prey in the labyrinth
	 */
	public Prey(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not being hunted in a
	 * {@code MazeSimulation}.
	 * 
	 */
	@Override
	public final Direction move(Direction[] choices) {
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

	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to evade predators more
	 * effectively.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)})
	 * @param daedalus
	 *            The world in which the animal moves
	 * @return The next direction of the animal, chosen in {@code choices}
	 */
	abstract public Direction move(Direction[] choices, Daedalus daedalus);
	
	public final Direction getDirection(){
		return prevChoice;
	}
}